package com.kenny.craftix.client.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.Entity;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper.Depth;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.entity.EntityRenderer;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.ModelBase;
import com.kenny.craftix.client.renderer.normalMapping.NMRenderer;
import com.kenny.craftix.client.renderer.normalMapping.NMShader;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.shaders.EntityShader;
import com.kenny.craftix.client.shaders.TerrainShader;
import com.kenny.craftix.client.shadows.render.ShadowMapMasterRenderer;
import com.kenny.craftix.utils.math.Maths;
import com.kenny.craftix.world.World;
import com.kenny.craftix.world.skybox.SkyboxRenderer;
import com.kenny.craftix.world.terrain.Terrain;

/**
 * World Render or Master Renderer. (Rendering everything in the world.)
 * @author Kenny
 */
public class Renderer 
{
	/**Field of view-the angular space visible to the eye with a fixed view 
	 * and a fixed head*/
	public static final float FOV = 70;
	/**Near plane visible eye*/
	public static final float NEAR_PLANE = 0.1f;
	/**Far plane visible eye*/
	public static final float FAR_PLANE = 2000;
	/**This effect adjusts the number of light shadow transitions on the model.*/
	public static final float LIGHT_LEVELS = 4.0f;
	public static final float DENSITY = 0.0035f;
	public static final float GRADIENT = 5.0f;
	/**Its three (+ 1) colors. (Together RGBA) or Sky Colour*/
	public static float SKY_RED = 0.8492f;
	public static float SKY_GREEN = 0.9036f;
	public static float SKY_BLUE = 0.9855f;
	/**Its a three fog distance colour.*/
	public static float FOG_RED = 0.743288f;
	public static float FOG_GREEN = 0.827576f;
	public static float FOG_BLUE = 0.944943f;
	/**This is alpha channel color transparency.*/
	public static final float ALPHA = 1;
	/**Setup projection matrix*/
	private Matrix4f projectionMatrix;
	/**Its a instance for static shader class*/
	private EntityShader shader = new EntityShader();
	private EntityRenderer renderer;
	private SkyboxRenderer skyboxRenderer;
	//private SkyboxSunMoonRenderer skyboxSunMoonRenderer;
	private ShadowMapMasterRenderer shadowRenderer;
	/**Get the NM render for this class*/
	private NMRenderer nmRenderer;
	private TerrainRenderer terrainRenderer;
	private World world;
	private TerrainShader terrainShader = new TerrainShader();
	private NMShader nmShader = new NMShader();
	//private Map<TexturedModel, List<Entity>> entityMainCraftixBox = new HashMap<TexturedModel, List<Entity>>();
	/**List of the entities.*/
	private Map<ModelBase, List<Entity>> entities = new HashMap<ModelBase, List<Entity>>();
	/**List of the entity with normal map effect*/
	private Map<ModelBase, List<Entity>> normalEntities = new HashMap<ModelBase, List<Entity>>();
	
	public static boolean isUseFBOs;
	public static boolean isUseNormalMaps = true;
	public static boolean isUseShaders = true;
	public static boolean isUseTringles = false;
	
	public Renderer(Craftix craftixIn, EntityCamera camera)
	{
		GlHelper.enableCulling();
		this.createProjectionMatrix();
		this.world = new World();
		this.renderer = new EntityRenderer(shader, projectionMatrix);
		this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		this.skyboxRenderer = new SkyboxRenderer(craftixIn, craftixIn.cxLoader, projectionMatrix);
		//this.skyboxSunMoonRenderer = new SkyboxSunMoonRenderer(craftixIn, craftixIn.cxLoader, projectionMatrix);
		this.nmRenderer = new NMRenderer(projectionMatrix);
		this.shadowRenderer = new ShadowMapMasterRenderer(camera);
	}
	
	public Renderer(Loader loader)
	{
		this.createProjectionMatrix();
		this.renderer = new EntityRenderer(shader, projectionMatrix);
	}
	
	/**
	 * Render a world. Entities, plane, lights, camera, etc..
	 */
	public void renderWorld(WorldScene worldIn, List<Entity> entities, List<Entity> normalEntities, List<Light> lights,
			EntityCamera camera, Vector4f clipPlane)
	{
		for (Entity entity : entities) 
		{
			this.processEntity(entity);
		}
		for (Entity entity : normalEntities) 
		{
			this.processNormalEntity(entity);
		}
		
		render(worldIn, lights, camera, clipPlane);
	}
	
	public void renderMainCraftixBox(Entity entityMainCraftixBox)
	{	
		render(entityMainCraftixBox, this.shader);
	}
	
	public Matrix4f getProjectionMatrix()
	{
		return projectionMatrix;
	}
	
	
	/**
	 * Compat all rendering stuff in one render method. For easy using.
	 */
	public void render(WorldScene worldIn, List<Light> lights, EntityCamera camera, Vector4f clipPlane)
	{
		this.prepare();
		if(isUseShaders)
		{
			this.shader.start();
			this.shader.loadPlane(clipPlane);
			this.shader.loadSkyColour(FOG_RED, FOG_GREEN, FOG_BLUE);
			this.shader.loadLights(lights);
			this.shader.loadViewMatrix(camera);
			this.shader.loadLightLevels(LIGHT_LEVELS);
			this.shader.loadDensity(DENSITY);
			this.shader.loadGradient(GRADIENT);
			this.renderer.render(entities);
			this.shader.stop();
			this.nmRenderer.render(this.normalEntities, clipPlane, lights, camera);
			this.terrainShader.start();
			this.terrainShader.loadPlane(clipPlane);
			this.terrainShader.loadSkyColour(FOG_RED, FOG_GREEN, FOG_BLUE);
			this.terrainShader.loadLights(lights);
			this.terrainShader.loadViewMatrix(camera);
			this.terrainRenderer.render(this.world.terrains, 
					this.shadowRenderer.getToShadowMapSpaceMatrix());
			this.terrainShader.stop();
			this.nmShader.start();
			this.nmShader.loadViewMatrix(camera);
			this.nmShader.loadSkyColour(FOG_RED, FOG_GREEN, FOG_BLUE);
			this.nmShader.stop();
			if(InGameSettings.renderSkyBoxIn)
			{
				this.skyboxRenderer.render(camera, FOG_RED, FOG_GREEN, FOG_BLUE, worldIn.worldTime);
				//this.skyboxSunMoonRenderer.render(camera, FOG_RED, FOG_BLUE, FOG_GREEN);
			}
			this.world.terrains.clear();
			this.entities.clear();
			this.normalEntities.clear();
		}
	}
	
	public void render(Entity entityMainCraftixBox, EntityShader shader)
	{
		ModelBase model = entityMainCraftixBox.getModel();
		Model rawModel = model.getModel();
		GlHelper.glBindVertexArray(rawModel.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		Matrix4f transormationMatrix = Maths.createTransformationMatrix
				(entityMainCraftixBox.getPosition(),entityMainCraftixBox.getRotX(), entityMainCraftixBox.getRotY(), entityMainCraftixBox.getRotZ(), entityMainCraftixBox.getScale());
		shader.loadTransformationMatrix(transormationMatrix);
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(model.getTexture().getTextureID());
		GlHelper.glDrawElements(Texture.TRIANGLES, rawModel.getVertexCount(), Texture.UNSINGED_INT, 0);
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glBindVertexArray(0);
		
	}
	
	public void processTerrain(Terrain terrain)
	{
		this.world.terrains.add(terrain);
	}
	
	//public void processMainCraftixBox(Entity entity)
	//{
	//	TexturedModel entityModel = entity.getModel();
	//	List<Entity> batch = entityMainCraftixBox.get(entityModel);
	///	if(batch != null)
	//	{
	//		batch.add(entity);
	//	} else {
	//		List<Entity> newBatch = new ArrayList<Entity>();
	//		newBatch.add(entity);
	//		this.entityMainCraftixBox.put(entityModel, newBatch);
	//	}
	//}
	
	/**
	 * Procces default entities.
	 */
	public void processEntity(Entity entity)
	{
		ModelBase entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null)
		{
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			this.entities.put(entityModel, newBatch);
		}
	}
	
	/**
	 * Procces normal map effect entities.
	 */
	public void processNormalEntity(Entity entity)
	{
		ModelBase entityModel = entity.getModel();
		List<Entity> batch = normalEntities.get(entityModel);
		if(batch != null)
		{
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			this.normalEntities.put(entityModel, newBatch);
		}
	}
	
	/**
	 * Render shadow map into the game world.
	 */
	public void renderShadowMap(List<Entity> entityList, Light sun)
	{
		for (Entity entity : entityList) 
		{
			this.processEntity(entity);
		}
		
		this.shadowRenderer.render(entities, sun);
		this.entities.clear();
	}
	
	public int getShadowMapTexture()
	{
		return shadowRenderer.getShadowMap();
	}
	
	/**
	 * Here just clear previus colors for previus frame.
	 */
	public void prepare()
	{
		GlHelper.enableDepthTest();
		GlHelper.glClear(Texture.COLOR_BUFFER_BIT | Depth.DEPTH_BUFFER_BIT);
		GlHelper.glClearColor(SKY_RED, SKY_GREEN, SKY_BLUE, ALPHA);
		TextureManager.activeTexture5();
		TextureManager.bindTexture2d(this.getShadowMapTexture());
		if(Keyboard.isKeyDown(Keyboard.KEY_F1))
		{
			 InGameSettings.usePoligonModeIn = true;
			 InGameSettings.enableTriangleMode();
			  
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F2)) 
		{
			 InGameSettings.usePoligonModeIn = false;
			 InGameSettings.disableTriangleMode();
		}
	}
	
	public void prepareMain()
	{
		GlHelper.enableDepthTest();
		GlHelper.glClear(Texture.COLOR_BUFFER_BIT | Depth.DEPTH_BUFFER_BIT);
		GlHelper.glClearColor(SKY_RED, SKY_GREEN, SKY_BLUE, ALPHA);
	}
	
	/**
	 * Here a created projection matrix.
	 */
    private void createProjectionMatrix()
    {
    	projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
    }
    
    /**
	 * Clean up all shader from previus frame.
	 */
	public void cleanUp()
	{	
		this.shader.cleanUp();
		this.terrainShader.cleanUp();
		this.nmShader.cleanUp();
		this.nmRenderer.cleanUp();
		this.shadowRenderer.cleanUp();
	}
	
	public void cleanUpMenu()
	{
		this.shader.cleanUp();
	}
	
	public SkyboxRenderer getSkyboxRenderer()
	{
		return this.skyboxRenderer;
	}
}

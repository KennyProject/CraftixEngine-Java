package com.kenny.craftix.world.skybox;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.gameplay.GameTime;

public class SkyboxRenderer 
{
	public static final String SKYBOX_FOLDER = new String("skybox/");
	/**These values are needed to adjust the rendering of the skybox*/
	public static final float LOWER_LIMIT = -10.0f;
	public static final float UPPER_LIMIT = 30.0f;
	/**This is a Raw Model for skybox cube*/
	private Model cube;
	/**This is a textures for day time*/
	private int skyboxDayTextures;
	/**This is a textures for night time*/
	private int skyboxNightTextures;
	private SkyboxShader shader;
	public float time = 0;
	/**This is a size of skybox*/
	private static final float SIZE = 500f;
	/**Its a simple verties of skybox*/
	private static final float[] VERTICES = {        
		    -SIZE,  SIZE, -SIZE,
		    -SIZE, -SIZE, -SIZE,
		    SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		    -SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE
		};
	
	private static String[] SKYBOX_DAY = 
	{
		SKYBOX_FOLDER + "s_day_right",
		SKYBOX_FOLDER + "s_day_left",
		SKYBOX_FOLDER + "s_day_up",
		SKYBOX_FOLDER + "s_day_down",
		SKYBOX_FOLDER + "s_day_back",
		SKYBOX_FOLDER + "s_day_front"
	};
	
	private static String[] SKYBOX_NIGHT = 
	{
		SKYBOX_FOLDER + "s_night_right",
		SKYBOX_FOLDER + "s_night_left",
		SKYBOX_FOLDER + "s_night_up",
		SKYBOX_FOLDER + "s_night_down",
		SKYBOX_FOLDER + "s_night_back",
		SKYBOX_FOLDER + "s_night_front"
	};
		
	/**
	 * That means when player reload the scene, the cycle time sets back to 0.
	 * 
	 * @param cycleTime - its a engine day time.
	 */
	public float setDayCicleTime(float cycleTime)
	{
		this.time = cycleTime;
		return cycleTime;
	}
	
	public float getDayCycleTime()
	{
		return this.time;
	}
	
	public SkyboxRenderer(Craftix craftixIn, Loader loader, Matrix4f projectionMatrix)
	{
		this.cube = loader.loadToVAO(VERTICES, 3);
		this.skyboxDayTextures = loader.loadCubeMapNew(SKYBOX_DAY);
		this.skyboxNightTextures = loader.loadCubeMapNew(SKYBOX_NIGHT);
		this.shader = new SkyboxShader();
		this.shader.start();
		this.shader.connectTextureUnits(craftixIn);
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
	}
	
	public void render(EntityCamera camera, float r, float g, float b, GameTime timeIn)
	{
		this.shader.start();
		this.shader.loadViewMatrix(camera);
		this.shader.loadFogColour(r, g, b);
		this.shader.loadLowerLimit(LOWER_LIMIT);
		this.shader.loadUpperLimit(UPPER_LIMIT);
		GlHelper.glBindVertexArray(cube.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		this.bindTextures(timeIn);
		GlHelper.glDrawArrays(Texture.TRIANGLES, 0, cube.getVertexCount());
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}
	
	private void bindTextures(GameTime timeIn)
	{
		TextureManager.activeTexture0();
		TextureManager.bindTextureCubeMap(timeIn.bindTexture0);
		TextureManager.activeTexture1();
		TextureManager.bindTextureCubeMap(timeIn.bindTexture1);
		this.shader.loadBlendFactor(timeIn.blendFactor);
		
	}
	
	public int getDay()
	{
		return this.skyboxDayTextures;
	}
	
	public int getNight()
	{
		return this.skyboxNightTextures;
	}
}

package com.kenny.craftix.world.water;

import static com.kenny.craftix.client.renderer.Renderer.*;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.shaders.FrameBuffer;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.utils.math.Maths;

@SuppressWarnings("unused")
public class WaterRenderer 
{
	/**Its a speed for wave water.*/
	private static final float WAVE_SPEED = 0.03f;
	private static final float TILING = 41.0f;
	private static final float DENSITY = 0.0035f;
	private static final float GRADIENT = 5.0f;
	/**This is simple quad*/
	private Model quad;
	private WaterShader shader;
	private FrameBuffer framebuffer;
	private Timer timer = new Timer();
	/**Texture, allowing to calculate the refraction of the light beam in the
	 * collision with the surface, for example water.*/
	private int dudvTexture;
	/**Normal maps are commonly stored as regular RGB images where the RGB components 
	 * correspond to the X, Y, and Z coordinates, respectively, of the surface normal.*/
	private int normalMap;
	/**This is just depth / underwater texture details for not glitched edges.*/
	public int depthMap;
	/**Its a move factor dudv water texture*/
	private float moveFactor = 0;
	
	public WaterRenderer(Loader loader, WaterShader shader, Matrix4f projectionMatrix,
			FrameBuffer framebuffer) 
	{
		this.shader = shader;
		this.framebuffer = framebuffer;
		this.dudvTexture = loader.loadFile("maps/dudvMap");
		this.normalMap = loader.loadFile("maps/normalMap/waterN");
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		setUpVAO(loader);
	}

	public void render(List<WaterTile> water, EntityCamera camera, Light sun) 
	{
		if(InGameSettings.renderWaterIn)
		{
			this.prepareRender(camera, sun);	
			for (WaterTile tile : water) 
			{
				Matrix4f modelMatrix = Maths.createTransformationMatrix(
						new Vector3f(tile.getX(), tile.getHeight(), tile.getZ()), 0, 0, 0,
						WaterTile.TILE_SIZE);
				this.shader.loadModelMatrix(modelMatrix);
				///this.shader.loadSkyColour(RED, BLUE, GREEN);
				GlHelper.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());
			}
			unbind();
		}
	}
	
	private void prepareRender(EntityCamera camera, Light sun)
	{
		this.shader.start();
		this.shader.loadViewMatrix(camera);
		this.shader.loadTiling(TILING);
		this.shader.loadDensity(DENSITY);
		this.shader.loadGradient(GRADIENT);
		this.moveFactor += WAVE_SPEED * this.timer.getFrameTimeSeconds();
		this.moveFactor %= 1;
		this.shader.loadMoveFactor(moveFactor);
		this.shader.loadLight(sun);
		
		GlHelper.glBindVertexArray(quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glActiveTexture(GL13.GL_TEXTURE0);
		GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, this.framebuffer.getReflectionTexture());
		GlHelper.glActiveTexture(GL13.GL_TEXTURE1);
		GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, this.framebuffer.getRefractionTexture());
		GlHelper.glActiveTexture(GL13.GL_TEXTURE2);
		GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, this.dudvTexture);
		GlHelper.glActiveTexture(GL13.GL_TEXTURE3);
		GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, this.normalMap);
		GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, this.dudvTexture);
		GlHelper.glActiveTexture(GL13.GL_TEXTURE4);
		GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, this.framebuffer.getRefractionDepthTexture());
		
		GlHelper.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void unbind()
	{
		GlHelper.glDisable(GL11.GL_BLEND);
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}

	private void setUpVAO(Loader loader) 
	{
		/**
		 * Just x and z vectex positions here, y is set to 0 in v.shader.
		 */
		float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
		this.quad = loader.loadToVAO(vertices, 2);
	}

}

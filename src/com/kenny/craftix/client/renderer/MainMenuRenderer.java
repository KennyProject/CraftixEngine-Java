package com.kenny.craftix.client.renderer;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.entity.EntityCamaraInMenu;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper.Depth;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.world.skybox.SkyboxPanoramaRenderer;

public class MainMenuRenderer 
{
	/**Setup projection matrix*/
	private static final float PANORAMA_FOV = 70f;
	private Matrix4f projectionMatrix;
	public SkyboxPanoramaRenderer skyboxPanoramaRenderer;
	
	public MainMenuRenderer(Loader loader) 
	{
		this.createProjectionMatrix();
		this.skyboxPanoramaRenderer = new SkyboxPanoramaRenderer(loader, projectionMatrix);
	}
	
	public void renderMainMenu(EntityCamaraInMenu camera)
	{
		this.prepareMainMenu();
		this.skyboxPanoramaRenderer.renderPanorama(camera, Renderer.FOG_RED, Renderer.FOG_GREEN, Renderer.FOG_BLUE);
	}
	
	public Matrix4f getProjectionMatrix()
	{
		return projectionMatrix;
	}
	
	/**
	 * Here just clear previus colors for previus frame.
	 */
	public void prepareMainMenu()
	{
		GlHelper.enableDepthTest();
		GlHelper.glClear(Texture.COLOR_BUFFER_BIT | Depth.DEPTH_BUFFER_BIT);
		GlHelper.glClearColor(Renderer.SKY_RED, Renderer.SKY_GREEN, Renderer.SKY_BLUE, Renderer.ALPHA);
	}
	
	/**
	 * Here a created projection matrix.
	 */
	private void createProjectionMatrix() 
	{
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(PANORAMA_FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = Renderer.FAR_PLANE - Renderer.NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((Renderer.FAR_PLANE + Renderer.NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * Renderer.NEAR_PLANE * Renderer.FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}

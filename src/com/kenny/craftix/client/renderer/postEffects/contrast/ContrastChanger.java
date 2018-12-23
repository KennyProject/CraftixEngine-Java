package com.kenny.craftix.client.renderer.postEffects.contrast;

import com.kenny.craftix.client.renderer.postEffects.ImageRenderer;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.init.PostEffectsInit;

public class ContrastChanger 
{
	/**Its renderer for a post proccessing effects.*/
	private ImageRenderer renderer;
	private ContrastShader shader;
	private PostEffectsInit effects = new PostEffectsInit();

	
	public ContrastChanger()
	{
		this.shader = new ContrastShader();
		this.renderer = new ImageRenderer();
	}
	
	public void render(int texture)
	{
		this.shader.start();
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(texture);
		this.effects.loadContrast();
		this.shader.loadContrast(this.effects.CONTRAST);
		this.renderer.renderQuad();
		this.shader.stop();
	}
	
	/**
	 * Clean all stuff on close the game.
	 */
	public void cleanUp()
	{
		this.renderer.cleanUp();
		this.shader.cleanUp();
		
	}
}

package com.kenny.craftix.client.gui;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.utils.math.Maths;

public class GuiRenderer 
{
	/**Its a basic quid*/
	public final Model quad;
	/**Get gui shader for renderer class*/
	public GuiShader shader;
	
	public GuiRenderer(Loader loader)
	{
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		this.quad = loader.loadToVAO(positions, 2);
		this.shader = new GuiShader();
	}
	
	/**
	 * Render guis with triangle's strips.
	 */
	public void render(List<GuiQuad> guis)
	{
		this.shader.start();
		GlHelper.glBindVertexArray(this.quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		for (GuiQuad gui : guis) 
		{
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(gui.getTextureId());
			GlHelper.enableBlend();
			GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
			GlHelper.disableDepthTest();
			Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			this.shader.loadTransformation(matrix);
			TextureManager.glDrawTrinangleStrips(0, this.quad.getVertexCount());
		}
		GlHelper.enableDepthTest();
		GlHelper.disableBlend();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}
	
	public void renderButton(List<GuiButton> guisButtons)
	{
		this.shader.start();
		GlHelper.glBindVertexArray(this.quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		for (GuiButton guiButton : guisButtons) 
		{
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(guiButton.getTextureId());
			GlHelper.enableBlend();
			GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
			GlHelper.disableDepthTest();
			Matrix4f matrix = Maths.createTransformationMatrix(guiButton.getPosition(), guiButton.getScale());
			this.shader.loadTransformation(matrix);
			TextureManager.glDrawTrinangleStrips(0, this.quad.getVertexCount());
		}
		GlHelper.enableDepthTest();
		GlHelper.disableBlend();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}
	
	/**
	 * Render guis with triangle's strips.
	 */
	public void render(GuiQuad gui)
	{
		this.shader.start();
		GlHelper.glBindVertexArray(this.quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(gui.getTextureId());
		GlHelper.enableBlend();
		GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
		GlHelper.disableDepthTest();
		Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
		this.shader.loadTransformation(matrix);
		TextureManager.glDrawTrinangleStrips(0, this.quad.getVertexCount());
		GlHelper.enableDepthTest();
		GlHelper.disableBlend();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}
	
	
	public void cleanUp()
	{
		this.shader.cleanUp();
	}
}

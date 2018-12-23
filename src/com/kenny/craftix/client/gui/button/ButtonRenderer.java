package com.kenny.craftix.client.gui.button;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.utils.math.Maths;

public class ButtonRenderer 
{
	public Loader loader = new Loader();
	public GuiRenderer buttonRenderer = new GuiRenderer(this.loader);
	public boolean visible = true;
	
	@SuppressWarnings("null")
	public void renderButton(List<GuiButton> buttons, boolean isUpdate)
	{
		GuiButton b = null;
		
		if(visible)
		{
			this.buttonRenderer.shader.start();
			GlHelper.glBindVertexArray(this.buttonRenderer.quad.getVaoID());
			GlHelper.glEnableVertexAttribArray(0);
			for(GuiButton button : buttons)
			{
				TextureManager.activeTexture0();
				TextureManager.bindTexture2d(button.getTextureId());
				GlHelper.enableBlend();
				GlHelper.tryBlendFuncSeperate(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA, Blend.ONE, Blend.ZERO);
				GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
				GlHelper.disableDepthTest();
				Matrix4f matrix = Maths.createTransformationMatrix(button.getPosition(), button.getScale());
				this.buttonRenderer.shader.loadTransformation(matrix);
				TextureManager.glDrawTrinangleStrips(0, this.buttonRenderer.quad.getVertexCount());
			
			GlHelper.enableDepthTest();
			GlHelper.disableBlend();
			GlHelper.glDisableVertexAttribArray(0);
			GlHelper.glBindVertexArray(0);
			this.buttonRenderer.shader.stop();
			
			@SuppressWarnings("unused")
			int j = 100;
			
			if(!b.enable)
			{
				j = 105;
			}
			else if(b.hovered)
			{
				j = 160;
			}
			
			if(isUpdate && button.enable)
			{
				button.checkHover();
				button.onButtonClick();
			}
		  }
			
		}
	}
}

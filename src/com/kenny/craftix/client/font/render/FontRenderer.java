package com.kenny.craftix.client.font.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.font.FontType;
import com.kenny.craftix.client.font.GuiEngineText;
import com.kenny.craftix.client.font.GuiText;
import com.kenny.craftix.client.font.GuiTextLoadingSplash;
import com.kenny.craftix.client.font.GuiTextWorld;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class FontRenderer 
{
	/**Its simple font shader for renderer*/
	private FontShader shader;

	/**This is width of letter.*/
	private static final float WIDHT = 0.50f;
	/**This is the edge of the letter*/
	private static final float EDGE = 0.20f;
	/**This is width of letter border.*/
	private static final float BORDER_WIDHT = 0.65f;
	/**This is edge of letter border.*/
	private static final float BORDER_EDGE = 0.1f;
	/**Its a outline colours for text*/
	private static final float TEXT_R = 0.0f;
	private static final float TEXT_G = 0.0f;
	private static final float TEXT_B = 0.0f;
	/**Its offset for dropping shadows effect.*/
	private static final float OFFSET_X = 0.0028f; //34
	private static final float OFFSET_Y = 0.0024f; //34
	
	public FontRenderer() 
	{
		this.shader = new FontShader();
	}
	
	public void render(Map<FontType, List<GuiText>> texts)
	{
		this.prepare();
		
		for (FontType font : texts.keySet()) 
		{
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(font.getTextureAtlas());
			for (GuiText text : texts.get(font)) 
			{
				this.renderText(text);
			}
		}
		
		this.endRendering();
	}
	
	public void renderEngineText(Map<FontType, List<GuiEngineText>> texts)
	{
		this.prepare();
		
		for (FontType font : texts.keySet()) 
		{
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(font.getTextureAtlas());
			for (GuiEngineText text : texts.get(font)) 
			{
				this.renderEngineText(text);
			}
		}
		
		this.endRendering();
	}
	
	public void renderLoadingSplash(Map<FontType, List<GuiTextLoadingSplash>> texts)
	{
		this.prepare();
		for (FontType font : texts.keySet()) {
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(font.getTextureAtlas());
			for (GuiTextLoadingSplash text : texts.get(font)) 
			{this.renderTextLoadingSplash(text);}}
		this.endRendering();
	}
	
	public void renderPause(Map<FontType, List<GuiTextWorld>> texts)
	{
		this.prepare();
		for (FontType font : texts.keySet()) {
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(font.getTextureAtlas());
			for (GuiTextWorld text : texts.get(font)) 
			{this.renderTextPause(text);}}
		this.endRendering();
	}

	public void cleanUp()
	{
		this.shader.cleanUp();
		
	}
	
	private void prepare()
	{
		GlHelper.enableBlend();
		GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
		GlHelper.disableDepthTest();
		this.shader.start();
	}

	private void renderText(GuiText text)
	{
		GlHelper.glBindVertexArray(text.getMesh());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		this.shader.loadColour(text.getColour());
		this.shader.loadTranslation(text.getPosition());
		this.shader.loadWidth(WIDHT);
		this.shader.loadEdge(EDGE);
		this.shader.loadBorderWidth(BORDER_WIDHT);
		this.shader.loadBorderEdge(BORDER_EDGE);
		this.shader.loadOutlineColour(new Vector3f(TEXT_R, TEXT_G, TEXT_B));
		this.shader.loadOffset(new Vector2f(OFFSET_X, OFFSET_Y));
		TextureManager.glDrawTrinangles(0, text.getVertexCount());
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glBindVertexArray(0);
	}
	
	private void renderEngineText(GuiEngineText text)
	{
		GlHelper.glBindVertexArray(text.getMesh());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		this.shader.loadColour(text.getColour());
		this.shader.loadTranslation(text.getPosition());
		this.shader.loadWidth(WIDHT);
		this.shader.loadEdge(EDGE);
		this.shader.loadBorderWidth(BORDER_WIDHT);
		this.shader.loadBorderEdge(BORDER_EDGE);
		this.shader.loadOutlineColour(new Vector3f(TEXT_R, TEXT_G, TEXT_B));
		this.shader.loadOffset(new Vector2f(OFFSET_X, OFFSET_Y));
		TextureManager.glDrawTrinangles(0, text.getVertexCount());
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glBindVertexArray(0);
	}
	
	private void renderTextLoadingSplash(GuiTextLoadingSplash text)
	{
		GlHelper.glBindVertexArray(text.getMesh());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		this.shader.loadColour(text.getColour());
		this.shader.loadTranslation(text.getPosition());
		this.shader.loadWidth(WIDHT);
		this.shader.loadEdge(EDGE);
		this.shader.loadBorderWidth(BORDER_WIDHT);
		this.shader.loadBorderEdge(BORDER_EDGE);
		this.shader.loadOutlineColour(new Vector3f(TEXT_R, TEXT_G, TEXT_B));
		this.shader.loadOffset(new Vector2f(OFFSET_X, OFFSET_Y));
		TextureManager.glDrawTrinangles(0, text.getVertexCount());
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glBindVertexArray(0);
	}
	
	private void renderTextPause(GuiTextWorld text)
	{
		GlHelper.glBindVertexArray(text.getMesh());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		this.shader.loadColour(text.getColour());
		this.shader.loadTranslation(text.getPosition());
		this.shader.loadWidth(WIDHT);
		this.shader.loadEdge(EDGE);
		this.shader.loadBorderWidth(BORDER_WIDHT);
		this.shader.loadBorderEdge(BORDER_EDGE);
		this.shader.loadOutlineColour(new Vector3f(TEXT_R, TEXT_G, TEXT_B));
		this.shader.loadOffset(new Vector2f(OFFSET_X, OFFSET_Y));
		TextureManager.glDrawTrinangles(0, text.getVertexCount());
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glBindVertexArray(0);
	}
	
	private void endRendering()
	{
		this.shader.stop();
		GlHelper.disableBlend();
		GlHelper.enableDepthTest();
		
	}

}

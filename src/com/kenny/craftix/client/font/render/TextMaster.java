package com.kenny.craftix.client.font.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kenny.craftix.client.font.FontType;
import com.kenny.craftix.client.font.GuiEngineText;
import com.kenny.craftix.client.font.GuiText;
import com.kenny.craftix.client.font.GuiTextLoadingSplash;
import com.kenny.craftix.client.font.GuiTextWorld;
import com.kenny.craftix.client.font.TextMeshData;
import com.kenny.craftix.client.loader.Loader;

public class TextMaster 
{
	/**Is the core to load the rest of the game.*/
	private static Loader loader;
	/**In this map contains information about Font Type class and List GuiText.*/
	private static Map<FontType, List<GuiText>> texts = new HashMap<FontType, List<GuiText>>();
	private static Map<FontType, List<GuiEngineText>> textsMenu = new HashMap<FontType, List<GuiEngineText>>();
	private static Map<FontType, List<GuiTextLoadingSplash>> textsLoading = new HashMap<FontType, List<GuiTextLoadingSplash>>();
	private static Map<FontType, List<GuiTextWorld>> textsPause = new HashMap<FontType, List<GuiTextWorld>>();
	private static FontRenderer renderer;
	public static boolean textMenu;
	
	public static void init(Loader theLoader)
	{
		renderer = new FontRenderer();
		loader = theLoader;
	}
	
	public static void render()
	{renderer.render(texts);}
	
	public static void renderEngineText()
	{
		if(textMenu)
		renderer.renderEngineText(textsMenu);
	}
	
	public static void renderLoadingSplash()
	{renderer.renderLoadingSplash(textsLoading);}
	
	public static void renderWorldText()
	{renderer.renderPause(textsPause);}
	
	/**
	 * Load the text to the list.
	 */
	public static void loadText(GuiText text)
	{
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), 
				data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GuiText> textBatch = texts.get(font);
		if(textBatch == null)
		{
			textBatch = new ArrayList<GuiText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	public static void loadTextMenu(GuiEngineText text)
	{
		FontType font = text.getFont();
		TextMeshData data = font.loadTextMenu(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), 
				data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GuiEngineText> textBatch = textsMenu.get(font);
		if(textBatch == null)
		{
			textBatch = new ArrayList<GuiEngineText>();
			textsMenu.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	public static void loadTextOptions(GuiTextLoadingSplash text)
	{
		FontType font = text.getFont();
		TextMeshData data = font.loadTextLoadingSplash(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), 
				data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GuiTextLoadingSplash> textBatch = textsLoading.get(font);
		if(textBatch == null)
		{
			textBatch = new ArrayList<GuiTextLoadingSplash>();
			textsLoading.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	public static void loadTextPause(GuiTextWorld text)
	{
		FontType font = text.getFont();
		TextMeshData data = font.loadTextPause(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), 
				data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GuiTextWorld> textBatch = textsPause.get(font);
		if(textBatch == null)
		{
			textBatch = new ArrayList<GuiTextWorld>();
			textsPause.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	/**
	 * Remove the text from the list.
	 */
	public static void removeText(GuiText text)
	{
		List<GuiText> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty())
		{texts.remove(text.getFont());}
	}
	
	public static void removeTextMenu(GuiEngineText text)
	{
		List<GuiEngineText> textBatch = textsMenu.get(text.getFont());
		try
		{
			textBatch.remove(text);
		}
		catch(Exception e)
		{
			textBatch.remove(text);
		}
		finally 
		{
			textBatch.remove(text);
		}
		
		
		if(textBatch.isEmpty())
		{textsMenu.remove(text.getFont());}
		
	}
	
	public static void removeTextOptions(GuiTextLoadingSplash text)
	{
		List<GuiTextLoadingSplash> textBatch = textsLoading.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty())
		{textsLoading.remove(text.getFont());}
	}
	
	public static void removeTextPause(GuiTextWorld text)
	{
		List<GuiTextWorld> textBatch = textsPause.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty())
		{textsPause.remove(text.getFont());}
	}
	
	/***
	 * Clean all text from the game on close.
	 */
	public static void cleanUp()
	{
		renderer.cleanUp();
	}
}

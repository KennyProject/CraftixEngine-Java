package com.kenny.craftix.client.font.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kenny.craftix.client.font.TextMeshData;
import com.kenny.craftix.client.loader.Loader;

public class TextComponentMaster 
{
	/**Is the core to load the rest of the game.*/
	public static Loader loader;
	/**Render text font component.*/
	private static TextComponentRenderer renderer;
	/**In this map contains information about Font Type class and List GuiText.*/
	private static Map<TextComponentFontType, List<TextComponent>> textMap = new HashMap<TextComponentFontType, List<TextComponent>>();
	
	/**
	 * Init the font renderer for textMap.
	 */
	public static void init()
	{
		renderer = new TextComponentRenderer();
		loader = new Loader();
	}
	
	/**
	 * Render a created text map.
	 */
	public static void render()
	{
		renderer.render(textMap);
	}

	/**
	 * Load the text into the list.
	 */
	public static void loadText(TextComponent text)
	{
		TextComponentFontType font = text.getTextFont();
		TextMeshData data = font.loadMesh(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), 
				data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<TextComponent> textBatch = textMap.get(font);
		if(textBatch == null)
		{
			textBatch = new ArrayList<TextComponent>();
			textMap.put(font, textBatch);
		}
		textBatch.add(text);
		System.out.println(textBatch);
	}
	
	/**
	 * Remove the text from the list.
	 */
	public static void removeText(TextComponent text)
	{
		List<TextComponent> textBatch = textMap.get(text.getTextFont());
		textBatch.remove(text);
		if(textBatch.isEmpty())
		{
			textMap.remove(text.getTextFont());
		}
	}
	
	/***
	 * Clean all text from the game on close.
	 */
	public static void cleanUp()
	{
		renderer.cleanUp();
	}


	public static TextComponentRenderer getRenderer() 
	{
		return renderer;
	}

}

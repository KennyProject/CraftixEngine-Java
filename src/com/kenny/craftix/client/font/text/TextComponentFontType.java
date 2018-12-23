package com.kenny.craftix.client.font.text;

import com.kenny.craftix.client.font.TextMeshData;

/**
 * Represents a font. It holds the font's texture atlas as well as having the
 * ability to create the quad vertices for any text using this font.
 */
public class TextComponentFontType 
{
	private int textureAtlas;
	private TextComponentMesh meshLoader;
	
	public TextComponentFontType(int textureAtlas, String fontFile) 
	{
		this.textureAtlas = textureAtlas;
		this.meshLoader = new TextComponentMesh(fontFile);
	}
	
	public TextMeshData loadMesh(TextComponent text) 
	{
		return this.meshLoader.createMesh(text);
	}

	public int getTextureAtlas() 
	{
		return textureAtlas;
	}

}

package com.kenny.craftix.client.font;

import com.kenny.craftix.client.font.text.TextComponent;
import com.kenny.craftix.client.font.text.TextComponentMesh;

/**
 * Represents a font. It holds the font's texture atlas as well as having the
 * ability to create the quad vertices for any text using this font.
 */
public class FontType 
{

	private int textureAtlas;
	private TextMeshCreator loader;
	private TextComponentMesh meshLoader;

	/**
	 * Creates a new font and loads up the data about each character from the
	 * font file.
	 * 
	 * @param fontFile - the font file containing information about each character in
	 *            the texture atlas.
	 */
	public FontType(int textureAtlas, String fontFile) 
	{
		this.textureAtlas = textureAtlas;
		this.loader = new TextMeshCreator(fontFile);
		this.meshLoader = new TextComponentMesh(fontFile);
	}

	public int getTextureAtlas() 
	{
		return textureAtlas;
	}

	
	/**
	 * Takes in an unloaded text and calculate all of the vertices for the quads
	 * on which this text will be rendered. The vertex positions and texture
	 * coords and calculated based on the information from the font file.
	 * 
	 * @param text - the unloaded text.
	 * @return Information about the vertices of all the quads.
	 */
	public TextMeshData loadText(GuiText text) 
	{
		return this.loader.createTextMesh(text);
	}
	
	public TextMeshData loadTextMenu(GuiEngineText text) 
	{
		return this.loader.createTextMeshMenu(text);
	}
	
	public TextMeshData loadTextLoadingSplash(GuiTextLoadingSplash text) 
	{
		return this.loader.createTextMeshOptions(text);
	}
	
	public TextMeshData loadTextPause(GuiTextWorld text) 
	{
		return this.loader.createTextMeshPause(text);
	}

	//=========================
	
	public TextMeshData loadMesh(TextComponent text) 
	{
		return this.meshLoader.createMesh(text);
	}
	
	//=========================

}

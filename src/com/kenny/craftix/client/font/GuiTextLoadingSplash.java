package com.kenny.craftix.client.font;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.font.render.TextMaster;

public class GuiTextLoadingSplash 
{

	private String textString;
	/**This is the font size for the text.*/
	private float fontSize;

	/**Its just text Vertex Array Buffer.*/
	private int textMeshVao;
	private int vertexCount;
	/**This is colour of current character.*/
	private Vector3f colour = new Vector3f(0f, 0f, 0f);

	private Vector2f position;
	/**The maximum size of the line.*/
	private float lineMaxSize;
	/**This is the total number of lines.*/
	private int numberOfLines;

	/**Here we load font type for characters.*/
	private FontType font;

	private boolean centerText = false;
	public GuiTextLoadingSplash(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
			boolean centered) 
	{
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		TextMaster.loadTextOptions(this);
	}


	public void remove() 
	{
		TextMaster.removeTextOptions(this);
	}

	public FontType getFont() 
	{
		return font;
	}


	public void setColour(float r, float g, float b) 
	{
		colour.set(r, g, b);
	}
	public Vector3f getColour() 
	{
		return colour;
	}

	public int getNumberOfLines() 
	{
		return numberOfLines;
	}

	public Vector2f getPosition() 
	{
		return position;
	}
	public int getMesh() 
	{
		return textMeshVao;
	}

	public void setMeshInfo(int vao, int verticesCount)
	{
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}

	public int getVertexCount() 
	{
		return this.vertexCount;
	}

	protected float getFontSize() 
	{
		return fontSize;
	}

	protected void setNumberOfLines(int number) 
	{
		this.numberOfLines = number;
	}

	protected boolean isCentered() 
	{
		return centerText;
	}

	protected float getMaxLineSize() 
	{
		return lineMaxSize;
	}
	protected String getTextString() 
	{
		return textString;
	}

}

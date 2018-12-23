package com.kenny.craftix.client.font.text;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.loader.Loader;

public class TextComponent 
{
	private int textMeshVao;
	/**This is a vertex count of the text.*/
	private int vertexCount;
	/**A message in the string.*/
	private String text;
	/**This is a size of the text.*/
	private float size;
	/**The maximum size of the text line.*/
	private float lineMaxSize;
	/**This is a line count.*/
	private int lineCount;
	/**Position text in x and y axis.*/
	private Vector2f position;
	/**Text color in rgb componenets.*/
	private Vector3f color = new Vector3f(1, 1, 1);
	/**If true set text in the center of the line.*/
	private boolean inCenter;
	/**This is id of the current text.*/
	private int id;
	/**Load the font type for characters.*/
	private TextComponentFontType textFont;
	public Loader loader = new Loader();
	
	public TextComponent(int id, String text, float x, float y, boolean centered) 
	{
		this.id = id;
		this.text = text;
		this.size = 1.6f;
		this.textFont = this.setDefalutFont();
		this.position = new Vector2f(x, y);
		this.lineMaxSize = 0.6f;
		this.inCenter = centered;
		this.show();
	}
	
	/**
	 * Showig the current text.
	 */
	public void show(TextComponent text)
	{
		TextComponentMaster.loadText(text);
	}
	
	public void show()
	{
		TextComponentMaster.loadText(this);
		this.setColor(new Vector3f(1f, 1f, 1f));
		System.out.println("Text showing!");
	}
	
	/**
	 * Hiding the current text.
	 */
	public void hide(TextComponent text)
	{
		TextComponentMaster.removeText(text);
	}
	
	public void hide()
	{
		TextComponentMaster.removeText(this);
		System.out.println("Text hided!");
	}
	
	/**
	 * Simple reload a text. Hide and then show again.
	 */
	public void reload()
	{
		this.hide();
		this.show();
	}
	
	public TextComponentFontType setDefalutFont()
	{
		TextComponentFontType fontCandara = new TextComponentFontType(this.loader.loadFontAtlas("candara"), "candara");
			return fontCandara;
	}
	
	/**
	 * Set the VAO and vertex count for this text.
	 * 
	 * @param vao - the VAO containing all the vertex data for the quads on
	 *            which the text will be rendered.
	 * @param verticesCount - the total number of vertices in all of the quads.
	 */
	public void setMeshInfo(int vao, int verticesCount)
	{
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}
	
	
	public String getText() 
	{
		return this.text;
	}

	public int getId() 
	{
		return this.id;
	}
	
	public void setSize(float size) 
	{
		this.size = size;
	}
	
	public void setColor(Vector3f color) 
	{
		this.color = color;
	}

	public void setPosition(Vector2f position) 
	{
		this.position = position;
	}
	
	public Vector2f getPosition()
	{
		return this.position;
	}
	
	public Vector3f getColor()
	{
		return this.color;
	}

	public float getLineMaxSize() 
	{
		return this.lineMaxSize;
	}

	public TextComponentFontType getTextFont() 
	{
		return this.textFont;
	}

	public void setTextFont(TextComponentFontType textFont) 
	{
		this.textFont = textFont;
	}

	public int getVertexCount() 
	{
		return this.vertexCount;
	}

	public int getMesh() 
	{
		return this.textMeshVao;
	}
	
	public float getSize() 
	{
		return this.size;
	}

	public boolean isCentered() 
	{
		return inCenter;
	}

	public int getLineCount() 
	{
		return lineCount;
	}

	public void setLineCount(int lineCount) 
	{
		this.lineCount = lineCount;
	}


}

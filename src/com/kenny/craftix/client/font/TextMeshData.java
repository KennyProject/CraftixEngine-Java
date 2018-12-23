package com.kenny.craftix.client.font;

/**
 * Stores the vertex data for all the quads on which a text will be rendered.
 */
public class TextMeshData 
{
	/**This is array of verices positions*/
	private float[] vertexPositions;
	/**This is array of texture coords*/
	private float[] textureCoords;
	
	public TextMeshData(float[] vertexPositions, float[] textureCoords)
	{
		this.vertexPositions = vertexPositions;
		this.textureCoords = textureCoords;
	}

	public float[] getVertexPositions() 
	{
		return vertexPositions;
	}

	public float[] getTextureCoords() 
	{
		return textureCoords;
	}

	public int getVertexCount() 
	{
		return vertexPositions.length / 2;
	}

}

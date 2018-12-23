package com.kenny.craftix.world.terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.MainLocation;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.utils.math.Maths;
import com.kenny.craftix.world.terrain.gen.HeightsGenerator;
@SuppressWarnings("unused")
public class Terrain 
{
	private static final Logger LOGGER = LogManager.getLogger("terrain");
	/**Its simple a size of terrain*/
	private static final float SIZE = 2000;
	/**This is the number maximum height of terrain can be. OLD STUFF!!!!*/
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 1024 * 1024 * 1024;
	/**Its a X coord of the terrain*/
	private float x;
	/**Its a Z coord of the terrain*/
	private float z;
	/**We get Model class for Terrain class. And get new Model for terrain*/
	private Model model;
	private TerrainTexturePack texturePack;
	/**Its a list rgb textures.*/
	private TerrainTexture blendMap;
	
	/**Double arrays for multiheihgt collision terrain*/
	private float[][] heights;
	
	public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, 
			TerrainTexture blendMap, String heightMap)
	{
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader, heightMap);
	}
	
	/**
	 * Its a generate terrain system. Generate a base flat terrain.
	 */
	private Model generateTerrain(Loader loader, String heightMap)
	{
		HeightsGenerator generator = new HeightsGenerator();
		
		/**
		 * I not use this capuls lines of code because heights of terrain now generates
		 * randomly.
		 */
		
		/**
		BufferedImage image = null;
		try 
		{
			image = ImageIO.read(new File(ResourceLocation.TEXTURE_FOLDER + 
					heightMap + ".png"));
		} 
		catch (IOException e) 
		{
			LOGGER.info("Cannot read file: " + heightMap);
			e.printStackTrace();
		}
		*/
		int VERTEX_COUNT = 128;
		
		
		this.heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6*(VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++)
		{
			for(int j=0;j<VERTEX_COUNT;j++)
			{
				vertices[vertexPointer * 3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				float height = getHeight(j, i, generator);
				this.heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] =  height;
				vertices[vertexPointer * 3 + 2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, generator);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++)
		{
			for(int gx=0;gx<VERTEX_COUNT-1;gx++)
			{
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	private Vector3f calculateNormal(int x, int z, HeightsGenerator generator)
	{
		float heightL = getHeight(x - 1, z, generator);
		float heightR = getHeight(x + 1, z, generator);
		float heightD = getHeight(x, z - 1, generator);
		float heightU = getHeight(x, z + 1, generator);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();
			return normal;
	}
	
	private float getHeight(int x, int z, HeightsGenerator generator)
	{
		return generator.generateHeight(x, z);
	}
	
	public float getHeightOfTerrain(float worldX, float worldZ)
	{
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if(gridX >= heights.length - 1 || gridZ >= heights.length - 1 
				|| gridX < 0 || gridZ < 0)
		{
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1-zCoord)) 
		{
			answer = Maths
					.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ], 0), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} 
		else 
		{
			answer = Maths
					.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		
		return answer;
	}
	public float getX() 
	{
		return x;
	}

	public float getZ()
	{
		return z;
	}

	public Model getModel() 
	{
		return model;
	}

	public TerrainTexturePack getTexturePack() 
	{
		return texturePack;
	}

	public TerrainTexture getBlendMap() 
	{
		return blendMap;
	}


}

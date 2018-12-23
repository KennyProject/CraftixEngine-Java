package com.kenny.craftix.client.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.TextureLoader;

import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.loader.PngDecoder.Format;
import com.kenny.craftix.client.loader.data.Vao;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.Renderer;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.textures.TextureData;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class Loader 
{
	public static final Logger LOGGER = LogManager.getLogger(CraftixOld.TITLE);
	/**List of vaos and vbos*/
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	/**List Vao*/
	public List<Vao> vao = new ArrayList<Vao>();
	/**List of textures ids*/
	public List<Integer> textures = new ArrayList<Integer>();
	
	/**
	 * Create a Vertex Arrays Objects (VAO) and return Vertex Buffer Objects (VBO) ids.
	 */
	private int createVao() 
	{
		int vaoID = GlHelper.glGenVertexArrays();
		GlHelper.glBindVertexArray(vaoID);
			return vaoID;
		
	}
	
	/**
	 * Load all raw models in VAO lists.
	 */
	public Model loadToVAO(float[] positions, float[] textureCoords, float[] normals,
			int[] indices)
	{
		int vaoID = createVao();
		this.vaos.add(vaoID);
		this.bindIndicesBuffer(indices);
		this.storeDataInAttributeList(0, 3, positions);
		this.storeDataInAttributeList(1, 2, textureCoords);
		this.storeDataInAttributeList(2, 3, normals);
		this.unbindVAO();
			return new Model(vaoID, indices.length);
	}
	
	/**
	 * Load all verices of each text character.
	 */
	public int loadToVAO(float[] positions, float[] textureCoords)
	{
		Renderer.isUseFBOs = true;
		int vaoID = createVao();
		this.vaos.add(vaoID);
		this.storeDataInAttributeList(0, 2, positions);
		this.storeDataInAttributeList(1, 2, textureCoords);
		this.unbindVAO();
			return vaoID;
	}
	
	/**
	 * Load all raw models in VAO lists with tangents for normal map effect.
	 */
	public Model loadToVAO(float[] positions, float[] textureCoords, float[] normals,
		float[] tangents, int[] indices)
	{
		Renderer.isUseFBOs = true;
		int vaoID = createVao();
		this.vaos.add(vaoID);
		this.bindIndicesBuffer(indices);
		this.storeDataInAttributeList(0, 3, positions);
		this.storeDataInAttributeList(1, 2, textureCoords);
		this.storeDataInAttributeList(2, 3, normals);
		this.storeDataInAttributeList(3, 3, tangents);
		this.unbindVAO();
			return new Model(vaoID, indices.length);
	}
	
	public Model loadToVAO(float[] position, int dimensions)
	{
		Renderer.isUseFBOs = true;
		int vaoID = createVao();
		this.storeDataInAttributeList(0, dimensions, position);
		this.unbindVAO();
			return new Model(vaoID, position.length / dimensions);
	}
	
	
	public int createEmptyVBO(int floatCount)
	{
		int vbo = GlHelper.glGenBuffers();
		this.vbos.add(vbo);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, vbo);
		GlHelper.glBufferData(Texture.ARRAY_BUFFER, floatCount * 4, Texture.STREAM_DRAW);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, 0);
			return vbo;
	}
	
	public void addInstancedAttribute(int vao, int vbo, int attribute, int dataSize, 
			int instancedDataLength, int offset)
	{
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, vbo);
		GlHelper.glBindVertexArray(vao);
		GlHelper.glVertexAttribPointer(attribute, dataSize, Texture.FLOAT, false, 
				instancedDataLength * 4, offset * 4);
		GlHelper.glVertexAttribDivisor(attribute, 1);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, 0);
		GlHelper.glBindVertexArray(0);
	}
	
	public void updateVBO(int vbo, float[] data, FloatBuffer buffer)
	{
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, vbo);
		GlHelper.glBufferData(Texture.ARRAY_BUFFER, buffer.capacity(), Texture.STREAM_DRAW);
		GlHelper.glBufferSubData(Texture.ARRAY_BUFFER, 0, buffer);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, 0);
	}
	
	/**
	 * Load texture in VAO to models.
	 */
	public int loadTexture(String file)
	{	
		org.newdawn.slick.opengl.Texture texture = null;
		try 
		{
			texture = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream
					(ResourceLocation.TEXTURE_FOLDER + file + ".png"));
			GlHelper.glGenerateMipmapping();
			GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR_MIPMAP_LINEAR);
			GlHelper.glTexParametrf(Texture.TEXTURE_LOD_BIAS, -0.4f);
			if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic)
			{
				float amount = Math.min(4f, 
						GlHelper.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				GlHelper.glTexParametrf(EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
			}else
			{
				LOGGER.info("Anisotropic Filtering not supported!");
			}
		} 
		catch (FileNotFoundException e) 
		{
			LOGGER.error("File not found in " + ResourceLocation.TEXTURE_FOLDER + "folder.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
			return textureID;
	}
	
	public int loadFile(String file)
	{	
		org.newdawn.slick.opengl.Texture texture = null;
		try 
		{
			texture = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream
					(ResourceLocation.TEXTURE_FOLDER + file + ".png"));
			GlHelper.glGenerateMipmapping();
			GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR_MIPMAP_LINEAR);
			GlHelper.glTexParametrf(Texture.TEXTURE_LOD_BIAS, -2.4f);
			if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic)
			{
				float amount = Math.min(4f, 
						GlHelper.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				GlHelper.glTexParametrf(EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
			}else
			{
				LOGGER.info("Anisotropic Filtering not supported!");
			}
		} 
		catch (FileNotFoundException e) 
		{
			LOGGER.error("File not found in: " + ResourceLocation.TEXTURE_FOLDER + " folder.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
			return textureID;
	}
	
	public int loadFontAtlas(String file)
	{	
		org.newdawn.slick.opengl.Texture texture = null;
		try 
		{
			texture = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream
					(ResourceLocation.FONT_FOLDER + file + ".png"));
			GlHelper.glGenerateMipmapping();
			GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR_MIPMAP_LINEAR);
			GlHelper.glTexParametrf(Texture.TEXTURE_LOD_BIAS, 0f);
		} 
		catch (FileNotFoundException e) 
		{
			LOGGER.error("Text Atlas not found in: " + ResourceLocation.TEXTURE_FOLDER + " folder.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
			return textureID;
	}
	
	/**
	 * Load a 3d cube when be located all textures for skybox map.
	 */
	public int loadCubeMap(String[] textureFiles) 
	{
		int texID = GlHelper.glGenTextures();
		TextureManager.activeTexture0();
		TextureManager.bindTextureCubeMap(texID);
		
		for (int i = 0; i < textureFiles.length; i++) 
		{
			TextureData data = decodeTextureFile(textureFiles[i]);
			GlHelper.glTexImage2D(Texture.TEXTURE_CUBE_MAP_POSI_X + i, 0, Texture.RGBA, data.getWidth(), data.getWidth(), 
					0, Texture.RGBA, Texture.UNSIGNED_BYTE, data.getBuffer());
		}
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_MAG_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_MIN_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_WRAP_S, Texture.CLAMP_TO_EDGE);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_WRAP_T, Texture.CLAMP_TO_EDGE);
		
		this.textures.add(texID);
			return texID;
	}
	
	public int loadCubeMapNew(String[] textureFiles) 
	{
		int texID = GlHelper.glGenTextures();
		TextureManager.activeTexture0();
		TextureManager.bindTextureCubeMap(texID);
		
		for (int i = 0; i < textureFiles.length; i++) 
		{
			TextureData data = decodeTextureFileNew(ResourceLocation.RESOURCEPACKS_FOLDER + 
					textureFiles[i] + ".png");
			GlHelper.glTexImage2D(Texture.TEXTURE_CUBE_MAP_POSI_X + i, 0, Texture.RGBA, data.getWidth(), data.getWidth(), 
					0, Texture.RGBA, Texture.UNSIGNED_BYTE, data.getBuffer());
		}
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_MAG_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_MIN_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_WRAP_S, Texture.CLAMP_TO_EDGE);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_WRAP_T, Texture.CLAMP_TO_EDGE);
		
		this.textures.add(texID);
			return texID;
	}
	
	private TextureData decodeTextureFile(String file) 
	{
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try 
		{
			InputStream in = Class.class.getResourceAsStream
					(ResourceLocation.TEXTURE_FOLDER + file + ".png");
			PngDecoder decoder = new PngDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			LOGGER.info("Tried to load the texture: " + file + " but it didn't work!");
			System.exit(-1);
		}
		return new TextureData(buffer, width, height);
	}
	
	private TextureData decodeTextureFileNew(String file) 
	{
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try 
		{
			FileInputStream in = new FileInputStream(file);
			PngDecoder decoder = new PngDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			LOGGER.info("Tried to load the texture: " + file + " but it didn't work!");
			System.exit(-1);
		}
		return new TextureData(buffer, width, height);
	}
	

	
	/**
	 * Stores the position data of the vertices into attribute 0 of the VAO. To
	 * do this the positions must first be stored in a VBO. You can simply think
	 * of a VBO as an array of data that is stored in memory on the GPU for easy
	 * access during rendering.
	 */
	
	private void storeDataInAttributeList(int attributeNumber, int coordsSize, float[] data)
	{
		int vboID = GlHelper.glGenBuffers();
		this.vbos.add(vboID);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, vboID);
		FloatBuffer buffer = this.storeDataInFloatBuffer(data);
		GlHelper.glBufferData(Texture.ARRAY_BUFFER, buffer, Texture.STATIC_DRAW);
		GlHelper.glVertexAttribPointer(attributeNumber, coordsSize, Texture.FLOAT, false, 0, 0);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, 0);
		
	}
	
	/**
	 * This method unbind all vertecies in VAO.
	 */
	private void unbindVAO()
	{
		GlHelper.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int [] indices)
	{
		int vboID = GlHelper.glGenBuffers();
		vbos.add(vboID);
		GlHelper.glBindBuffer(Texture.ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = this.storeDataInIntBuffer(indices);
		GlHelper.glBufferData(Texture.ELEMENT_ARRAY_BUFFER, buffer, Texture.STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
			return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
			return buffer;
	}
	
	/**
	 * Deletes all the VAOs and VBOs when the game is closed. VAOs and VBOs are
	 * located in video memory.
	 */
	public void cleanUp()
	{
		for (int vao : vaos) 
		{
			GlHelper.glDeleteVertexArrays(vao);
		}
		for (int vbo : vaos) 
		{
			GlHelper.glDeleteBuffers(vbo);
		}
		for (int texture : textures) 
		{
			GlHelper.glDeleteTextures(texture);
		}
	}
}

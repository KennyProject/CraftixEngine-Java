package com.kenny.craftix.client.gui;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.IShader;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class GuiShader extends ShaderProgram implements IShader
{
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "guiVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "guiFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[GuiShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	private int location_transformationMatrix;

	public GuiShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}
	
	public void loadTransformation(Matrix4f matrix)
	{
		this.uniformLoader.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_transformationMatrix = super.getUniformLocation("transformationMatrix", shaderName);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
	}

}

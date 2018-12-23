package com.kenny.craftix.client.particles.render;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class ParticleShader extends ShaderProgram 
{

	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "particleVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "particleFS" + FRAGMENT;

	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[ParticleShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**This is all uniforms contains in shader (txt) file in res folder*/
	private int location_numberOfRows;
	private int location_projectionMatrix;

	public ParticleShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_numberOfRows = super.getUniformLocation("numberOfRows", shaderName);
		this.location_projectionMatrix = super.getUniformLocation("projectionMatrix", shaderName);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(2, "texOffset");
		super.bindAttribute(3, "blendFactor");	
		
	}

	protected void loadNumberOfRows(float numberOfRows)
	{
		this.uniformLoader.loadFloat(location_numberOfRows, numberOfRows);
	}
	
	protected void loadProjectionMatrix(Matrix4f projectionMatrix) 
	{
		this.uniformLoader.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

}

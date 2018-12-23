package com.kenny.craftix.client.renderer.postEffects.contrast;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class ContrastShader extends ShaderProgram 
{
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "contrastVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "contrastFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[ContrastShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	private int location_contrast;
	
	public ContrastShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{	
		this.location_contrast = super.getUniformLocation("contrast", shaderName);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
	}
	
	public void loadContrast(float contrast)
	{
		this.uniformLoader.loadFloat(location_contrast, contrast);
	}

}

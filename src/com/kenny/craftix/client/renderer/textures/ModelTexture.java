package com.kenny.craftix.client.renderer.textures;

public class ModelTexture 
{
	/**Id textures for the model*/
	private int textureID;
	/**A texture that describes the ability of a material to reflect.*/
	private int specularMap;
	/**Normal maps are commonly stored as regular RGB images where the RGB components 
	 * correspond to the X, Y, and Z coordinates, respectively, of the surface normal.*/
	private int normalMap;
	/**It's a shine damper. The greater the value of Thea the stronger
	 * the object will Shine from the light*/
	private float shineDumper = 1;
	/**It's the ability to reflect light. The larger the number the more 
	 * light will be reflected from the object*/
	private float reflectivity = 0;
	/**This is transparancy or (Прозрачность)*/
	private boolean hasTransparency = false;
	/**If we use fake light then normals going to up and light see brightness*/
	private boolean useFakeLighting = false;
	/**Simple boolean check if you use a specular map or not.*/
	private boolean hasSpecularMap = false;
	
	/**Number of row in the atlas*/
	private int numberOfRows = 1;
	
	public ModelTexture(int textureID)
	{
		this.textureID = textureID;
	}
	
	public void setSpecularMap(int specMap)
	{
		this.specularMap = specMap;
		this.hasSpecularMap = true;
	}
	
	public boolean hasSpecularMap() 
	{
		return hasSpecularMap;
	}
	
	public int getSpecularMap()
	{
		return specularMap;
	}

	public boolean isUseFakeLighting() 
	{
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) 
	{
		this.useFakeLighting = useFakeLighting;
	}

	public int getTextureID() 
	{
		return this.textureID;
	}

	public float getShineDumper() 
	{
		return shineDumper;
	}
	
	public int getNormalMap()
	{
		return normalMap;
	}
	
	public void setNormalMap(int normalMap)
	{
		this.normalMap = normalMap;
	}

	public void setShineDumper(float shineDumper) 
	{
		this.shineDumper = shineDumper;
		
	}

	public float getReflectivity() 
	{
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) 
	{
		this.reflectivity = reflectivity;
	}

	public boolean isHasTransparency() 
	{
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) 
	{
		this.hasTransparency = hasTransparency;
	}

	public int getNumberOfRows() 
	{
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) 
	{
		this.numberOfRows = numberOfRows;
	}
	
	
	
}

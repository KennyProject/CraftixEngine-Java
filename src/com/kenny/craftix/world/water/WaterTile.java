package com.kenny.craftix.world.water;

import com.kenny.craftix.client.entity.EntityLivingBase;

public class WaterTile 
{
	
	/**This is a tile size of our quad model water.*/
	public static final float TILE_SIZE = 100 + 500;
	
	/**This is height of water (Simple waves)*/
	private float height;
	/**Its a position X and Z*/;
	private float x,z;
	
	public WaterTile(float centerX, float centerZ, float height)
	{
	
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
		EntityLivingBase.waterHeight = this.getHeight();
		
	}

	public float getHeight() 
	{
		return height;
	}

	public float getX() 
	{
		return x;
	}

	public float getZ() 
	{
		return z;
	}
}

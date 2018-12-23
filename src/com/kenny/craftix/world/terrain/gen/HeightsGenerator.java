package com.kenny.craftix.world.terrain.gen;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeightsGenerator 
{
	public static final Logger LOGGER = LogManager.getLogger("generator");
	/**This is amplitude genetations*/
	private static final float AMPLITUDE = 70f;
	private static final int OCTAVES = 3;
	private static final float ROUGHNESS = 0.1f;
	private static final float SMOOTH = 8f;
	private static final float SMOOTH_NEXT = 4f;
	/***An instance of this class is used to generate a stream of pseudorandom 
	 * numbers. The class uses a 48-bit seed, which is modified using a linear 
	 * congruential formula.*/
	private Random random = new Random();
	/**This is a seed generation map.*/
	private int seed;
	private int xOffset = 0;
	private int zOffset = 0;
	
	public HeightsGenerator()
	{
		this.seed = this.random.nextInt(1000000000);
	}
	
	/**
	 * This is same smooth noicse but more interpreted.
	 * @return
	 */
	private float getInterpolatedNoise(float x, float z)
	{
		int intX = (int) x;
		int intZ = (int) z;
		float fracX = x - intX;
		float fracZ = z - intZ;
		
		float v1 = getSmoothNoise(intX, intZ);
		float v2 = getSmoothNoise(intX + 1, intZ);
		float v3 = getSmoothNoise(intX, intZ + 1);
		float v4 = getSmoothNoise(intX + 1, intZ + 1);
		float i1 = interpolate(v1, v2, fracX);
		float i2 = interpolate(v3, v4, fracX);
			return interpolate(i1, i2, fracZ);
	}
	
	
	private float interpolate(float a, float b, float blend)
	{
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
			return a * (1f - f) + b * f;
		
	}
	
	/**
	 * Its a generation map vertices height.
	 */
	public float generateHeightOld(int x, int z)
	{
		float total = getInterpolatedNoise(x / SMOOTH, z / SMOOTH) * AMPLITUDE;
		total += getInterpolatedNoise(x / SMOOTH_NEXT, z / SMOOTH_NEXT) * AMPLITUDE / 3f;
		total += getInterpolatedNoise(x , z) * AMPLITUDE / 9f;
			return total;
	}
	
	/**
	 * Only works with POSITIVE gridX and gridZ values!
	 */
    public HeightsGenerator(int gridX, int gridZ, int vertexCount, int seed) 
    {
        this.seed = seed;
        xOffset = gridX * (vertexCount - 1);
        zOffset = gridZ * (vertexCount - 1);
    }
	
	 public float generateHeight(int x, int z) 
	 {
	        float total = 0;
	        float d = (float) Math.pow(2, OCTAVES - 1);
	        for(int i = 0; i < OCTAVES; i++)
	        {
	            float freq = (float) (Math.pow(2, i) / d);
	            float amp = (float) Math.pow(ROUGHNESS, i) * AMPLITUDE;
	            total += getInterpolatedNoise((x + xOffset) * freq, (z + zOffset) * freq) * amp;
	        }
	        return total;
	    }
	
	/**
	 * This is that same noise but litte be random, smooth.
	 * @return
	 */
	private float getSmoothNoise(int x, int z)
	{
		float corners = (getNoise(x - 1, z - 1) + getNoise(x + 1, z - 1) + 
				getNoise(x - 1, z + 1) + getNoise(x + 1, z + 1)) / 16f;
		float sides = (getNoise(x - 1, z) + getNoise(x + 1, z) + 
				getNoise(x, z - 1) + getNoise(x, z + 1)) / 8f;
		float center = getNoise(x, z) / 4f;
			return corners + sides + center;
	}
	
	/**
	 * This method will give different seeds values from up to.
	 * @param x - X coord on the terrain.
	 * @param z - Z coord on the terrain.
	 */
	public float getNoise(int x, int z)
	{
		this.random.setSeed(x * 49632 + z * 325176 + this.seed); 
		return this.random.nextFloat() * 2f - 1f;
	}
	
	
	
}

package com.kenny.craftix.init;

public class PostEffectsInit 
{
	public float CONTRAST;
	/**First level need be a bigger number than level two.*/
	public static int BLUR_LEVEL_1;
	public static int BLUR_LEVEL_2;
	public static int BRIGHT_SIZE;
	
	public static boolean isContrast = true;
	public static boolean isRenderBlur = false;
	public static boolean isRenderBrightFilter = false;
	public static boolean isRenderBloom = true;
	public static boolean isRenderPanoramaBlur = true;

	public void loadContrast()
	{
		this.setContrast(isContrast, 0.1f);
	}
	
	public static void loadBlur()
	{
		setBlur(isRenderBloom, 8, 2);
	}
	
	public static void loadPanoramaBlur()
	{
		setBlurPanorama(isRenderPanoramaBlur, 8, 2);
	}
	
	public static void loadBrightFilter()
	{
		setBrightFilter(2);
	}
	
	public static void loadBloom()
	{
		setBloom(isRenderBlur, 10, 6);
	}
	
	/**
	 * Set and regulate a contrast effect.
	 * @param contrast - change a colours.
	 */
	public void setContrast(boolean contrast, float level)
	{
		isContrast = contrast;
		
		if(isContrast)
		{
			CONTRAST = level;
		}
		if(!isContrast)
		{
			CONTRAST = 0.0f;
		}
	}
	
	/**
	 * Sets the blurring effect. It is better to use in small quantities.
	 */
	public static void setBlur(boolean renderBlur, int level1, int level2)
	{
		isRenderBlur = renderBlur;
		BLUR_LEVEL_1 = level1;
		BLUR_LEVEL_2 = level2;
		
	}
	
	/**
	 * Sets the blurring effect. It is better to use in small quantities.
	 */
	public static void setBlurPanorama(boolean renderBlurPanorama, int level1, int level2)
	{
		isRenderPanoramaBlur = renderBlurPanorama;
		BLUR_LEVEL_1 = level1;
		BLUR_LEVEL_2 = level2;
		
	}
	
	/**
	 * Set the bright filter effect.
	 * @param size - the resolution of effect on the screen.
	 */
	public static void setBrightFilter(int size)
	{
		BRIGHT_SIZE = size;
	}
	
	public static void setBloom(boolean renderBlur, int level1, int size)
	{
		isRenderBlur = renderBlur;
		BLUR_LEVEL_1 = level1;
		BRIGHT_SIZE = size;
	}

}

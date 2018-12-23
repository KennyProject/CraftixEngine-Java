package com.kenny.craftix.utils;

import com.kenny.craftix.client.CraftixOld;

public class Timer 
{

    /** How many full ticks have turned over since the last call to updateTimer(), capped at 10. */
    public int elapsedTicks = 10;
	public static long lastFrameTime;
	public static float delta;
	/**Frames Per Seconds.*/
	public int fps;
	/**Last fps time.*/
    public long lastFPS;
	
	/**
	 * This have calculated how longer last frame to be render. In seconds.
	 */
	public void setTime()
	{
		long currentFrameTime = CraftixOld.getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public float getFrameTimeSeconds()
	{
		return delta;
	}
	
	public void initTime()
	{
		lastFrameTime = CraftixOld.getCurrentTime();
	}
	
}

package com.kenny.craftix.client.gui;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.shaders.FrameBuffer;

public class GuiAdder
{
	public Craftix cx;
	
	public void guiInit(Craftix craftixIn)
	{
		this.cx = craftixIn;
	}
	
	/**
	 * Simple add a gui texture on screen of the game.
	 */
	public GuiQuad addGui(String guiFile, float x, float y, float scaleX, float scaleY)
	{
		
		return new GuiQuad(this.cx.cxLoader.loadTexture(guiFile), 
				new Vector2f(x, y), new Vector2f(scaleX, scaleY));
	}
	
	public GuiQuad drawGui(String guiFile, Loader loader, float x, float y, float scaleX, float scaleY)
	{
		return new GuiQuad(loader.loadTexture(guiFile), 
				new Vector2f(x, y), new Vector2f(scaleX, scaleY));
	}
	
	public GuiQuad addGui(String guiFile, Loader loader, float x, float y, float scaleX, float scaleY
			, float rotX, float rotY)
	{
		return new GuiQuad(loader.loadTexture(guiFile), 
				new Vector2f(x, y), new Vector2f(scaleX, scaleY), rotX, rotY);
	}
	
	public void addGuiButton()
	{
		//return new AbstactButton("button", new Vector2f(0, 0), new Vector2f(0.3f, 0.1f));
	}
	
	/***
	 * Add the screen with type a gui with reflection texture effect.
	 */
	public GuiQuad addGuiReflection(FrameBuffer frameBuffer, float x, float y, float scaleX, float scaleY)
	{
		return new GuiQuad(frameBuffer.getReflectionTexture(), 
				new Vector2f(x, y), new Vector2f(scaleX, scaleY));
	}
	
	/***
	 * Events when happen on update method.
	 */
	public void onUpdate()
	{
	}
}

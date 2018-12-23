package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.loader.Loader;

public class GuiLoadingSplash extends GuiScreen implements IGui
{
	public List<GuiQuad> guisLoadingBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisLoadingState1 = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisLoadingState2 = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisLoadingState3 = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisLoadingButtons = new ArrayList<GuiQuad>();
	
	public static boolean progress25 = true, progress50 = false, progress75 = false;
	
	/**Its a core of all game engine.*/
	public Loader loader;
	
	/**Its all gui-textures.*/
	public GuiQuad gui_loading_background;
	public GuiQuad gui_loading_loading;
	public GuiQuad gui_loading_loadingBar;
	public GuiQuad gui_loading_loadingBarBorder;
	public GuiQuad gui_loading_generate_terrain;
	public GuiQuad gui_loading_processing_entity;
	public GuiQuad gui_loading_building_terrain;
	
	/**Its all gui-textures-buttons.*/

	public void loadLoadingScreen()
	{
		this.loader = new Loader();
		
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.gui_loading_background = drawGui("guis/menu/in_background", this.loader, 0.0f, -0.95f, 1.95f, 1.95f);
		this.gui_loading_loading = drawGui("guis/menu/loading", this.loader, 0.01f, 0.4f, 0.40f, 0.40f);
		this.gui_loading_loadingBarBorder = drawGui("guis/menu/loading_bar_border", this.loader, 0.01f, -0.5f, 0.50f, 0.35f);
		this.gui_loading_loadingBar = drawGui("guis/menu/loading_bar", this.loader, 0.01f, -0.5f, 0.50f, 0.35f);
		this.gui_loading_generate_terrain = drawGui("guis/menu/generate_terrain", this.loader, 0.01f, -0.4f, 0.30f, 0.30f);
		this.gui_loading_processing_entity = drawGui("guis/menu/processing_entity", this.loader, 0.01f, -0.4f, 0.30f, 0.30f);
		this.gui_loading_building_terrain = drawGui("guis/menu/building_terrain", this.loader, 0.01f, -0.4f, 0.30f, 0.30f);
		
	}
	
	public void setAnimationBar()
	{
		if(progress25)
		{
			this.gui_loading_loadingBar.setScale(new Vector2f(0.10f,0.35f));
			this.gui_loading_loadingBar.setPosition(new Vector2f(-0.34f, -0.5f));
		}
		if(progress50)
		{
			this.gui_loading_loadingBar.setScale(new Vector2f(0.25f,0.35f));
			this.gui_loading_loadingBar.setPosition(new Vector2f(-0.21f, -0.5f));
		}
		if(progress75)
		{
			this.gui_loading_loadingBar.setScale(new Vector2f(0.50f,0.35f));
			this.gui_loading_loadingBar.setPosition(new Vector2f(0.01f, -0.5f));
		}
		
	}

	@Override
	public void drawGuiButtons() 
	{
		
	}

	@Override
	public void addToList() 
	{
		this.guisLoadingBackground.add(gui_loading_background);
		this.guisLoadingBackground.add(gui_loading_loading);
		this.guisLoadingBackground.add(gui_loading_loadingBarBorder);
		this.guisLoadingBackground.add(gui_loading_loadingBar);
		this.guisLoadingState1.add(gui_loading_generate_terrain);
		this.guisLoadingState2.add(gui_loading_processing_entity);
		this.guisLoadingState3.add(gui_loading_building_terrain);
	}

	@Override
	public void updateButtons() 
	{
	
	}
	
}

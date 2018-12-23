package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.init.TextInit;

public class GuiSingleplayer extends GuiScreen implements IGui
{
	public List<GuiQuad> guisSinglepBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisSinglepButtons = new ArrayList<GuiQuad>();

	/**All init of Singleplayer Gui textures.*/
	public GuiQuad gui_singlep_background;
	public GuiQuad gui_singlep_scenePanorama;
	public GuiQuad gui_singlep_singleplayer;
	
	/**All init of Singleplayer Gui Buttons-textures*/;
	public GuiAbstractButton button_singlep_back;
	public GuiAbstractButton button_singlep_genWorld;
	public GuiAbstractButton button_singlep_genLowPolyWorld;
	public GuiAbstractButton button_singlep_genFlatWorld;
	public GuiAbstractButton button_signlep_genEmptyWorld;
	public GuiAbstractButton button_singlep_loadWorld;
	
	public void loadSingleplayerScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.gui_singlep_scenePanorama = drawGui("guis/menu/in_background_game", this.cx.cxLoader, 0.70f, -0.20f, 1.60f, 1.80f);
		this.gui_singlep_singleplayer = drawGui("guis/menu/singleplayer", this.cx.cxLoader, -0.50f, 0.70f, 0.40f, 0.50f);
	
	}

	@Override
	public void drawGuiButtons()
	{
		this.button_singlep_back = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f,-0.72f), -0.04f, 0f) 
			{
			public void onClick(IButton button) 
			{
				TextInit textInit = new TextInit();
				textInit.loadDefaultFonts();
				GuiRenderManager.renderSingleplayerMenu = false;
				GuiRenderManager.renderMainMenu = true;
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_singlep_back.show(guisSinglepButtons);
		
		this.button_singlep_genWorld = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(-0.60f, 0.29f)) 
			{
				public void onClick(IButton button)
				{
					TextInit textInit = new TextInit();
					textInit.loadDefaultFonts();
					cx.setInMenuScene(false); 
					cx.setInWorldScene(true);
				}
				public void isVisible(boolean visibleIn) {}
			};
			this.button_singlep_genWorld.show(guisSinglepButtons);
			
		this.button_singlep_genLowPolyWorld = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(-0.60f, 0.07f)) 
		{
			public void onClick(IButton button)
			{
				
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_singlep_genLowPolyWorld.show(guisSinglepButtons);
		
		this.button_singlep_genFlatWorld = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(-0.60f, -0.15f)) 
		{
			public void onClick(IButton button)
			{
				
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_singlep_genFlatWorld.show(guisSinglepButtons);
		
		this.button_signlep_genEmptyWorld = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(-0.60f, -0.37f)) 
		{
			public void onClick(IButton button)
			{
				
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_signlep_genEmptyWorld.show(guisSinglepButtons);
		
		this.button_singlep_loadWorld = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(-0.60f, -0.71f)) 
		{
			public void onClick(IButton button)
			{
				
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_singlep_loadWorld.show(guisSinglepButtons);
		
	}

	@Override
	public void addToList() 
	{
		this.guisSinglepBackground.add(gui_singlep_scenePanorama);
		this.guisSinglepBackground.add(gui_singlep_singleplayer);
	}

	@Override
	public void updateButtons() 
	{
		this.button_singlep_back.update();
		this.button_singlep_genWorld.update();
		this.button_singlep_genLowPolyWorld.update();
		this.button_singlep_genFlatWorld.update();
		this.button_signlep_genEmptyWorld.update();
		this.button_singlep_loadWorld.update();
	}

}

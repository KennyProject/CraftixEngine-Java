package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.init.TextInit;

public class GuiInGameMenu extends GuiScreen implements IGui
{
	public List<GuiQuad> guisInGameMenuBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisInGameMenuButtons = new ArrayList<GuiQuad>();
	
	/**All init of In-Game Menu Gui textures.*/
	public GuiQuad gui_ingame_background;
	public GuiQuad gui_ingame_pause;

	/**All init of In-Game Menu Gui Buttons-textures*/;
	public GuiAbstractButton button_ingame_resume;
	public GuiAbstractButton button_ingame_saveWorld;
	public GuiAbstractButton button_ingame_options;
	public GuiAbstractButton button_ingame_backToMenu;
	public GuiAbstractButton button_ingame_closeGame;
	
	public void loadInGameMenuScreen(Craftix cx)
	{
		this.cx = cx;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.gui_ingame_background = drawGui("guis/game/background", this.cx.cxLoader, 0.25f, -0.25f, 1.40f, 1.40f);
		this.gui_ingame_pause = drawGui("guis/game/pause", this.cx.cxLoader,  0.0f, 0.75f, 0.40f, 0.45f);
	}

	@Override
	public void drawGuiButtons() 
	{
		this.button_ingame_resume = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(0.006f, 0.22f), 0f, 0f) 
			{

			public void onClick(IButton button) 
			{
				TextInit.removeInGamePausePage();
				GuiRenderManager.renderGame = true;
				GuiRenderManager.renderInventoryBar = true;
				GuiRenderManager.renderInGameMenu = false;
				cx.isGamePause = false;
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_ingame_resume.show(guisInGameMenuButtons);
		
		this.button_ingame_saveWorld = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(-0.186f, 0.0f), -0.08f, 0f) 
			{
			public void onClick(IButton button) {}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_ingame_saveWorld.show(guisInGameMenuButtons);
		
		this.button_ingame_options = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(0.212f, 0.0f), -0.08f, 0f) 
			{
				public void onClick(IButton button) 
				{
					TextInit.removeInGamePausePage();
					TextInit textInit = new TextInit();
					textInit.loadDefaultFonts();
					textInit.initOptionInGamePage(textInit.loader);
					cx.isGamePause = true;
					WorldScene.inGameOptions = true;
					GuiRenderManager.renderInGameMenu = false;
					GuiRenderManager.renderOptionsInGame = true;
				}
				public void isVisible(boolean visibleIn) {}
		};
		this.button_ingame_options.show(guisInGameMenuButtons);
		
		this.button_ingame_backToMenu = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(0.006f, -0.22f), 0f, 0f)  
			{
			public void onClick(IButton button) 
			{
				TextInit.removeInGamePausePage();
				TextInit textInit = new TextInit();
				textInit.loadDefaultFonts();
				textInit.initSingleplayerPage(textInit.loader);
				cx.backToMenu = true;
				cx.quitGameScene = true;
				cx.isGamePause = false;
				
				
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_ingame_backToMenu.show(guisInGameMenuButtons);
		
		this.button_ingame_closeGame = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(0.006f, -0.44f), 0f, 0f) 
		{
			public void onClick(IButton button) 
			{
				cx.exitController(0);
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_ingame_closeGame.show(guisInGameMenuButtons);
	}

	@Override
	public void addToList()
	{
		this.guisInGameMenuBackground.add(gui_ingame_background);
		this.guisInGameMenuBackground.add(gui_ingame_pause);
	}

	@Override
	public void updateButtons() 
	{
		this.button_ingame_resume.update();
		this.button_ingame_saveWorld.update();
		this.button_ingame_options.update();
		this.button_ingame_backToMenu.update();
		this.button_ingame_closeGame.update();
	}

}

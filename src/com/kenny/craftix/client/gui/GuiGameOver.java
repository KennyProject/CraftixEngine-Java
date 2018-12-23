package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.init.TextInit;

public class GuiGameOver extends GuiScreen implements IGui
{
	public List<GuiQuad> guisGameOverBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisGameOverButtons = new ArrayList<GuiQuad>();
	public WorldScene world;

	/**Init all the gui-texture for game over gui.*/
	public GuiQuad gui_gameOver_background;
	public GuiQuad gui_gameOver_gameOver;
	
	/**Init all the gui-buttons texture for game over gui.*/
	public GuiAbstractButton button_gameOver_backToMenu;
	public GuiAbstractButton button_gameOver_respawn;
	
	public void loadGameOverScreen(Craftix cxIn, WorldScene worldIn)
	{
		this.cx = cxIn;
		this.world = worldIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.gui_gameOver_background = drawGui("guis/game/background", this.cx.cxLoader, 0.25f, -0.25f, 1.40f, 1.40f);
		this.gui_gameOver_gameOver = drawGui("guis/game/game_over", this.cx.cxLoader, 0.0f, 0.45f, 0.40f, 0.45f);
	}

	@Override
	public void drawGuiButtons() 
	{
		this.button_gameOver_backToMenu = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(-0.252f, -0.4f), -0.08f, 0f) 
			{
				public void onClick(IButton button) 
				{
					world.getPlayer().respawn();
					TextInit textInit = new TextInit();
					textInit.loadDefaultFonts();
					textInit.initSingleplayerPage(textInit.loader);
					cx.backToMenu = true;
					cx.isGamePause = false;
					cx.quitGameScene = true;
					EntityPlayer.flag2 = false;
				}
				public void isVisible(boolean visibleIn) {}
		};
		this.button_gameOver_backToMenu.show(guisGameOverButtons);
		
		this.button_gameOver_respawn = new GuiAbstractButton("guis/menu/button_base",
				new Vector2f(0.252f, -0.4f), -0.08f, 0f) 
			{
				public void onClick(IButton button) 
				{
					world.getPlayer().respawn();
					GuiRenderManager.renderGameOver = false;
				}
				public void isVisible(boolean visibleIn) {}
		};
		this.button_gameOver_respawn.show(guisGameOverButtons);
	}

	@Override
	public void addToList() 
	{
		this.guisGameOverBackground.add(gui_gameOver_background);
		this.guisGameOverBackground.add(gui_gameOver_gameOver);
	}

	@Override
	public void updateButtons() 
	{
		this.button_gameOver_backToMenu.update();
		this.button_gameOver_respawn.update();
	}
	
}

package com.kenny.craftix.client.gui;

import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.init.TextInit;

public class GuiRenderManager 
{
	public static boolean renderGuiScreenBack = false;
	public static boolean renderMainMenu = true;
	public static boolean renderQuit = false;
	public static boolean renderOptionsMenu = false;
	public static boolean renderOptionsInGame = false;
	public static boolean renderCreditsMenu = false;
	public static boolean renderEngineInfo = false;
	public static boolean renderUpdatesMenu = false;
	public static boolean renderSingleplayerMenu = false;
	public static boolean renderInGameMenu = false;
	public static boolean renderLanguageMenu = false;
	public static boolean renderGraphicMenu = false;
	public static boolean renderAudioMenu = false;
	public static boolean renderAudioMenuInGame = false;
	public static boolean renderLinkWarning = false;
	public static boolean renderGame = false;
	public static boolean renderLoadingSplash = false;
	public static boolean renderGameOver = false;
	public static boolean renderInventoryFlyMode = false;
	public static boolean renderInventoryBack = false;
	public static boolean renderInventoryBar = true;
	
	
	private static MainMenuScene mainMenuScene;
	
	public void guisRenderTriggers()
	{
		
		if(renderMainMenu)
		{
			if(GuiRenderManager.renderMainMenu)
			{
				mainMenuScene.guiMainMenu.renderScreen();
				if(GuiRenderManager.renderLinkWarning && TextInit.initLinkPage)
				{
					//this.mainMenuScene.guiWarning.updateButtons();
					//this.mainMenuScene.getCraftix().guiRenderer.render(this.mainMenuScene.guiWarning.linkButtons);
				}
				
			}
		}
	}
	
	
	
	/**
	 * Its be in future.
	 */
	public void registryGuis()
	{
		
	}
}

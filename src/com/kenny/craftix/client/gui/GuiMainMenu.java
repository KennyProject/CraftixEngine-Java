package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.init.TextInit;

@SuppressWarnings("static-access")
public class GuiMainMenu extends GuiScreen implements IGui
{
    public List<GuiButton> buttonList = Lists.<GuiButton>newArrayList();
	public List<GuiQuad> guisBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisPanoramaBackgrond = new ArrayList<GuiQuad>();
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger();
	protected static final float DISTANCE_BEETWEN_BUTTON = 0.22f;
	public static boolean update = true;
	public static boolean isOpen = false;

	/**
	 * Load actually created Main Menu Screen.
	 */
	public void loadMainMenuScreen(Craftix cxIn)
	{
		this.cx = cxIn;
		this.drawGuis();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis()
	{
		this.guisPanoramaBackgrond.add(new GuiQuad(GUI_LOCATION + "menu/panorama_overlay", 0.0f, 0.11f, 1.95f, 1.2f));
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/logo", 0f, 0.72f, 0.50f, 0.70f));
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/logo_2", 0f, 0.40f, 0.15f, 0.25f));
	}
	
	@Override
	public void drawGuiButtons()
	{
		/**
		 * This is custom X and Y values for a for multi-size buttons.
		 */
		@SuppressWarnings("unused")
		float x = 0f, y = 0f;
		float posX = 0f, posY = 0f;
		if(InGameSettings.guiScaleSmallIn)
		{
			x = -0.024f; y = 0.030f;
			posX = -0.06f; posY = 0f;
		}
		if(InGameSettings.guiScaleMediumIn)
		{
			x = -0.006f; y = 0.020f;
			posX = -0.05f; posY = 0f;
		}
		if(InGameSettings.guiScaleLargeIn)
		{
			x = 0.006f; y = 0.006f;
			posX = 0f; posY = 0f;
		}
		
		this.b.b1 = new GuiButton(1, BUTTON_RECT, 0.0f, 0.0f, 0, "mainmenu.singleplayer") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 1)
				{
					GuiRenderManager.renderLinkWarning = false;
					GuiRenderManager.renderMainMenu = false;
					GuiRenderManager.renderSingleplayerMenu = true;
				}
			}
		};
		
		this.b.b2 = new GuiButton(2, BUTTON_RECT, 0.0f, -0.2f, 0, "mainmenu.multiplayer") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 2)
				{
					GuiRenderManager.renderLinkWarning = false;
				}
			}
		};
		
		this.b.b3 = new GuiButton(3, BUTTON_RECT, 0.0f, -0.4f, 0, "mainmenu.options") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 3)
				{
					GuiRenderManager.renderLinkWarning = false;
				}
			}
		};
		
		this.b.b4 = new GuiButton(4, BUTTON_RECT, 0.0f, -0.6f, 0, "mainmenu.credits") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 4)
				{
					GuiRenderManager.renderLinkWarning = false;
				}
			}
		};
		
		this.b.b5 = new GuiButton(5, BUTTON_RECT, 0.0f, -0.8f, 0, "mainmenu.quit") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 5)
				{
					GuiRenderManager.renderLinkWarning = false;
					GuiRenderManager.renderMainMenu = false;
					GuiRenderManager.renderQuit = true;
				}
			}
		};
		
		this.b.b6 = new GuiButton(6, GUI_LOCATION + "menu/button_info", -0.82f + posX, 0.82f + posY, 1, "mainmenu.info") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 6)
				{
					GuiRenderManager.renderLinkWarning = false;
					GuiRenderManager.renderMainMenu = false;
					GuiRenderManager.renderEngineInfo = true;
				}
			}
		};
		
		this.b.b7 = new GuiButton(7, GUI_LOCATION + "menu/button_question", -0.66f + posX, 0.82f + posY, 1, "mainmenu.updates") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 7)
				{
					GuiRenderManager.renderLinkWarning = false;
					GuiRenderManager.renderMainMenu = false;
					GuiRenderManager.renderUpdatesMenu = true;
				}
			}
		};
		
		this.b.b8 = new GuiButton(8, GUI_LOCATION + "menu/button_link", 0.66f + posX, 0.82f + posY, 1, "mainmenu.link") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 8)
				{
					GuiRenderManager.renderLinkWarning = true;
					TextInit.initLinkPage = true;
				}
			}
		};
		
		this.b.b9 = new GuiButton(9, GUI_LOCATION + "menu/button_exit", 0.82f + posX, 0.82f + posY, 1, "mainmenu.exit") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 9)
				{
					GuiRenderManager.renderLinkWarning = false;
					GuiRenderManager.renderMainMenu = false;
					GuiRenderManager.renderQuit = true;
				
				}
			}
		};
	}
	
	public void renderScreen() 
	{
		this.b.b1.renderButton(true, false);
		this.b.b2.renderButton(true, false);
		this.b.b3.renderButton(true, false);
		this.b.b4.renderButton(true, false);
		this.b.b5.renderButton(true, false);
		this.b.b6.renderButton(true, false);
		this.b.b7.renderButton(true, false);
		this.b.b8.renderButton(true, true);
		this.b.b9.renderButton(true, false);
	}

	@Override
	public void addToList()
	{
	}
	
	@Override
	public void updateButtons() 
	{
	}

}

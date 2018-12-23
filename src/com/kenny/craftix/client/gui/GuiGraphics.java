package com.kenny.craftix.client.gui;

import static com.kenny.craftix.client.language.Language.OPTION_CHANGE_DESC;
import static com.kenny.craftix.client.language.Language.langEnOptionChangeDesc;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.font.GuiEngineText;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.init.TextInit;

public class GuiGraphics extends GuiScreen implements IGui
{
	public List<GuiQuad> guisGraphicsBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisGraphicsButtons = new ArrayList<GuiQuad>();
	
	/**Its boolean represents a loading text with desc "To save...." on click
	 * stage of the buttons.*/
	public static boolean isDescLoad;
	
	/**All init of Graphics Gui textures.*/
	public GuiQuad gui_graphics_option;
	public GuiQuad gui_graphics_optionPanel;
	public GuiQuad gui_graphics_border;
	
	/**All init of Graphics Gui Buttons-textures*/;
	public GuiAbstractButton button_graphics_back;
	public GuiAbstractButton button_guiScale_1;
	public GuiAbstractButton button_guiScale_2;
	public GuiAbstractButton button_guiScale_3;
	public GuiAbstractButton button_resolution_1600x900;
	public GuiAbstractButton button_resolution_1280x720;
	public GuiAbstractButton button_resolution_1200x600;
	public GuiAbstractButton button_resolution_960x540;
	public GuiAbstractButton button_multisample_yes;
	public GuiAbstractButton button_multisample_no;
	
	public void loadGraphicsScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{	
		this.gui_graphics_option = drawGui("guis/menu/options", this.cx.cxLoader, 0.0f, 0.75f, 0.40f, 0.45f);
		this.gui_graphics_optionPanel = drawGui("guis/menu/gui_background", this.cx.cxLoader, -0.21f, -0.45f, 0.80f, 1.0f);
		this.gui_graphics_border = drawGui("guis/menu/gui_border", this.cx.cxLoader, -0.73f, 0.63f, 0.20f, 0.10f);
	}

	@Override
	public void drawGuiButtons() 
	{
		/**
		 * This is custom X and Y values for a for multi-size buttons.
		 */
		float x = 0f; float y = 0f;
		@SuppressWarnings("unused")
		float posY = 0f;

		if(InGameSettings.guiScaleSmallIn)
		{
			x = 0.010f; y = 0.015f;
			posY = 0.01f;
		}
		if(InGameSettings.guiScaleMediumIn)
		{
			x = 0.005f; y = 0.010f;
		}
		if(InGameSettings.guiScaleLargeIn)
		{
			x = 0f; y = 0f;
		}
		
		this.button_graphics_back = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f, -0.72f), -0.04f, 0f) 
		{
			
			public void onClick(IButton button) 
			{
				GuiScaled.isButtonYesNo = true;
				TextInit.removeGraphicsPage();
				TextInit textInit = new TextInit();
				textInit.loadDefaultFonts();
				textInit.initOptionPage(textInit.loader);
				GuiRenderManager.renderGraphicMenu = false;
				GuiRenderManager.renderOptionsMenu = true;
				isDescLoad = false;
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_graphics_back.show(guisGraphicsButtons);
		
		this.button_guiScale_1 = new GuiAbstractButton("guis/menu/button_num1", 
				new Vector2f(-0.55f, 0.41f), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			public void onClick(IButton button) 
			{
				button.hide(guisGraphicsButtons);
				TextInit medium = new TextInit();
				TextInit.removeGraphicsPage();
				InGameSettings.guiScaleLargeIn = false;
				InGameSettings.guiScaleSmallIn = false;
				InGameSettings.guiScale = 2;
				InGameSettings.setGuiScaleMedium();
				medium.loadDefaultFonts();
				medium.initGrapthicsPage(medium.loader);
				TextInit.userLine = -0.2f;
				GuiEngineText t0041 = medium.addText(OPTION_CHANGE_DESC, 0.61f - TextInit.X, 0.275f - TextInit.Y);
				langEnOptionChangeDesc = t0041;
				langEnOptionChangeDesc.setColour(1, 1, 1);
				isDescLoad = true;
				button_guiScale_2.show(guisGraphicsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		
		this.button_guiScale_2 = new GuiAbstractButton("guis/menu/button_num2", 
				new Vector2f(-0.55f, 0.41f), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			public void onClick(IButton button) 
			{
				button.hide(guisGraphicsButtons);
				TextInit large = new TextInit();
				TextInit.removeGraphicsPage();
				InGameSettings.guiScaleMediumIn = false;
				InGameSettings.guiScaleSmallIn = false;
				InGameSettings.guiScale = 3;
				InGameSettings.setGuiScaleLarge();
				large.loadDefaultFonts();
				large.initGrapthicsPage(large.loader);
				TextInit.userLine = -0.2f;
				GuiEngineText t0041 = large.addText(OPTION_CHANGE_DESC, 0.61f - TextInit.X, 0.275f - TextInit.Y);
				langEnOptionChangeDesc = t0041;
				langEnOptionChangeDesc.setColour(1, 1, 1);
				isDescLoad = true;
				button_guiScale_3.show(guisGraphicsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};

		this.button_guiScale_3 = new GuiAbstractButton("guis/menu/button_num3", 
				new Vector2f(-0.55f,0.41f), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			public void onClick(IButton button) 
			{
				button.hide(guisGraphicsButtons);
				TextInit small = new TextInit();
				TextInit.removeGraphicsPage();
				InGameSettings.guiScaleLargeIn = false;
				InGameSettings.guiScaleMediumIn = false;
				InGameSettings.guiScale = 1;
				InGameSettings.setGuiScaleSmall();
				small.loadDefaultFonts();
				small.initGrapthicsPage(small.loader);
				TextInit.userLine = -0.3f;
				GuiEngineText t0041 = small.addText(OPTION_CHANGE_DESC, 0.61f - TextInit.X, 0.275f - TextInit.Y);
				langEnOptionChangeDesc = t0041;
				langEnOptionChangeDesc.setColour(1, 1, 1);
				isDescLoad = true;
				button_guiScale_1.show(guisGraphicsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		if(InGameSettings.guiScaleSmallIn)
		{this.button_guiScale_1.show(guisGraphicsButtons);}
		if(InGameSettings.guiScaleMediumIn)
		{this.button_guiScale_2.show(guisGraphicsButtons);}
		if(InGameSettings.guiScaleLargeIn)
		{this.button_guiScale_3.show(guisGraphicsButtons);}
		
		this.button_resolution_1600x900 = new GuiAbstractButton("guis/menu/button_base", 
				new Vector2f(-0.12f, 0.25f)) 
		{
		
			public void onClick(IButton button)
			{
				TextInit.removeGraphicsPage();
				TextInit textInit = new TextInit();
				try 
				{
					cx.initDisplayMode(InGameSettings.displayWidthIn = 1600, 
							InGameSettings.displayWidthIn = 900, InGameSettings.useFullscreenIn);
					textInit.loadDefaultFonts();
					textInit.initGrapthicsPage(textInit.loader);
				} 
				catch (LWJGLException e) 
				{
					e.printStackTrace();
				}
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_resolution_1600x900.show(guisGraphicsButtons);
		
		this.button_resolution_1280x720 = new GuiAbstractButton("guis/menu/button_base", 
				new Vector2f(-0.12f, 0.25f - 0.22f)) 
		{
		
			public void onClick(IButton button) 
			{
				TextInit.removeGraphicsPage();
				TextInit textInit = new TextInit();
				try 
				{
					cx.initDisplayMode(InGameSettings.displayWidthIn = 1280, 
							InGameSettings.displayWidthIn = 720, InGameSettings.useFullscreenIn);
					textInit.loadDefaultFonts();
					textInit.initGrapthicsPage(textInit.loader);
				} 
				catch (LWJGLException e) 
				{
					e.printStackTrace();
				}
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_resolution_1280x720.show(guisGraphicsButtons);
		
		this.button_resolution_1200x600 = new GuiAbstractButton("guis/menu/button_base", 
				new Vector2f(-0.12f, 0.25f - 0.44f)) 
		{
		
			public void onClick(IButton button) 
			{
				TextInit.removeGraphicsPage();
				TextInit textInit = new TextInit();
				try 
				{
					cx.initDisplayMode(InGameSettings.displayWidthIn = 1200, 
							InGameSettings.displayWidthIn = 600, InGameSettings.useFullscreenIn);
					textInit.loadDefaultFonts();
					textInit.initGrapthicsPage(textInit.loader);
				} 
				catch (LWJGLException e) 
				{
					e.printStackTrace();
				}
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_resolution_1200x600.show(guisGraphicsButtons);
		
		this.button_resolution_960x540 = new GuiAbstractButton("guis/menu/button_base", 
				new Vector2f(-0.12f, 0.25f - 0.66f)) 
		{
		
			public void onClick(IButton button) 
			{
				try 
				{
					cx.initDisplayMode(InGameSettings.displayWidthIn = 860, 
							InGameSettings.displayWidthIn = 540, InGameSettings.useFullscreenIn);
				} 
				catch (LWJGLException e) 
				{
					e.printStackTrace();
				}
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_resolution_960x540.show(guisGraphicsButtons);
	}

	@Override
	public void addToList() 
	{
		this.guisGraphicsBackground.add(gui_graphics_option);
		this.guisGraphicsBackground.add(gui_graphics_optionPanel);
		this.guisGraphicsBackground.add(gui_graphics_border);
	
	}

	@Override
	public void updateButtons() 
	{
		this.button_graphics_back.update();
		
		if(InGameSettings.guiScaleLargeIn && !InGameSettings.guiScaleMediumIn && !InGameSettings.guiScaleSmallIn)
		{
			this.button_guiScale_3.updateMulti();
				this.button_guiScale_1.updateMulti();
					this.button_guiScale_2.updateMulti();
		}
		if(InGameSettings.guiScaleMediumIn && !InGameSettings.guiScaleLargeIn && !InGameSettings.guiScaleSmallIn)
		{
			this.button_guiScale_2.updateMulti();
				this.button_guiScale_3.updateMulti();
					this.button_guiScale_1.updateMulti();
		}
		if(InGameSettings.guiScaleSmallIn && !InGameSettings.guiScaleMediumIn && !InGameSettings.guiScaleLargeIn)
		{
			this.button_guiScale_1.updateMulti();
				this.button_guiScale_2.updateMulti();
					this.button_guiScale_3.updateMulti();
		}
		
		this.button_resolution_1600x900.update();
		this.button_resolution_1280x720.update();
		this.button_resolution_1200x600.update();
		this.button_resolution_960x540.update();
	}

}

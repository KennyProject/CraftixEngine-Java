package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.init.TextInit;

public class GuiLanguage extends GuiScreen implements IGui
{
	public List<GuiQuad> guisLanguageBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisLanguageButtons = new ArrayList<GuiQuad>();
	
	/**All init of Language Gui textures.*/
	public GuiQuad gui_language_option;
	public GuiQuad gui_language_optionPanel;
	public GuiQuad gui_language_border;
	
	/**All init of Language Gui Buttons-textures*/;
	public GuiAbstractButton button_language_back;
	public GuiAbstractButton button_language_en;
	public GuiAbstractButton button_language_ru;
	
	public void loadLanguageScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.gui_language_option = drawGui("guis/menu/options", this.cx.cxLoader, 0.0f, 0.75f, 0.40f, 0.45f);
		this.gui_language_optionPanel = drawGui("guis/menu/gui_background_small", this.cx.cxLoader, 0.1f, -0.45f, 0.40f, 1.00f);
		this.gui_language_border = drawGui("guis/menu/gui_border", this.cx.cxLoader, -0.73f, 0.63f, 0.20f, 0.10f);
	}

	@Override
	public void drawGuiButtons() 
	{
		/**
		 * This is custom X and Y values for a for multi-size buttons.
		 */
		float x = 0f, y = 0f;
		if(InGameSettings.guiScaleSmallIn)
		{x = 0.009f; y = 0.012f;}
		
		if(InGameSettings.guiScaleMediumIn)
		{x = 0.01f; y = 0.01f;}
		
		if(InGameSettings.guiScaleLargeIn)
		{x = 0f; y = 0f;}
		
		this.button_language_back = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f,-0.72f), -0.04f, 0f) 
		{
			public void onClick(IButton button) 
			{
				GuiScaled.isButtonYesNo = true;
				TextInit.removeLanguagePage();
				TextInit textInit = new TextInit();
				textInit.loadDefaultFonts();
				textInit.initOptionPage(textInit.loader);
				if(InGameSettings.hasError)
				{
					textInit.initErrorsMessages(textInit.loader);
				}
				GuiRenderManager.renderLanguageMenu = false;
				GuiRenderManager.renderOptionsMenu = true;
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_language_back.show(guisLanguageButtons);
		
		this.button_language_en = new GuiAbstractButton("guis/menu/button_base", 
				new Vector2f(-0.0f, 0.1f), -0.10f + x, -0.03f + y)  
		
		{
			public void onClick(IButton button) 
			{
				MainMenuScene mainScene = new MainMenuScene();
				mainScene.selectEnglishLang();
			}
			public void isVisible(boolean visibleIn) {}
		};
			this.button_language_en.show(guisLanguageButtons);
			
			this.button_language_ru = new GuiAbstractButton("guis/menu/button_base", 
					new Vector2f(-0.0f, -0.1f), -0.10f + x, -0.03f + y)  
			
			{
				public void onClick(IButton button) 
				{
					MainMenuScene mainScene = new MainMenuScene();
					mainScene.selectRussianLang();
				}
				public void isVisible(boolean visibleIn) {}
			};
			this.button_language_ru.show(guisLanguageButtons);
	}

	@Override
	public void addToList() 
	{
		this.guisLanguageBackground.add(gui_language_option);
		this.guisLanguageBackground.add(gui_language_optionPanel);
		this.guisLanguageBackground.add(gui_language_border);
	}

	@Override
	public void updateButtons() 
	{
		this.button_language_back.update();
		this.button_language_en.update();
		this.button_language_ru.update();
	}

}

package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.init.TextInit;

public class GuiAudio extends GuiScreen implements IGui 
{
	public List<GuiQuad> guisBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisAudioButtons = new ArrayList<GuiQuad>();
	
	/**All init of Audio Gui Buttons-textures*/;
	public GuiAbstractButton button_audio_back;
	public GuiAbstractButton button_audio_back_game;
	public GuiAbstractButton button_soundOn;
	public GuiAbstractButton button_soundOff;
	
	public void loadAudioScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.drawGuiButtons();
	}
	
	public void loadAudioInGameScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/options", 0.0f, 0.75f, 0.40f, 0.45f));
		//this.gui_audio_background_rot = drawGui("guis/menu/gui_background_small_rot", this.cx.cxLoader, -0.21f, -0.35f, 0.70f, 0.60f);
		//this.gui_audio_border = drawGui("guis/menu/gui_border", this.cx.cxLoader, -0.73f, 0.63f, 0.20f, 0.10f);
	}

	@Override
	public void drawGuiButtons() 
	{
		/**
		 * This is custom X and Y values for a for multi-size buttons.
		 */
		float x = 0f; float y = 0f;
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
		
		
		this.button_audio_back = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f,-0.72f), -0.04f, 0f) 
			{
				public void onClick(IButton button) 
				{
					TextInit textInit = new TextInit();
					TextInit.removeAudioPage();
					textInit.loadDefaultFonts();
					textInit.initOptionPage(textInit.loader);
					GuiRenderManager.renderAudioMenu = false;
					GuiRenderManager.renderOptionsMenu = true;
				}
				public void isVisible(boolean visibleIn) {}
		};
		if(!this.cx.isInWorldScene() && this.cx.isInMenuScene())
		{
			this.button_audio_back.show(guisAudioButtons);
		}
		
		this.button_audio_back_game = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f,-0.72f), -0.04f, 0f) 
			{
				public void onClick(IButton button) 
				{
					TextInit textInit = new TextInit();
					TextInit.removeAudioInGame();
					textInit.loadDefaultFonts();
					textInit.initOptionInGamePage(textInit.loader);
					GuiRenderManager.renderAudioMenu = false;
					GuiRenderManager.renderOptionsInGame = true;
				}
				public void isVisible(boolean visibleIn) {}
		};
		if(this.cx.isInWorldScene() && !this.cx.isInMenuScene())
		{
			this.button_audio_back_game.show(guisAudioButtons);
		}
		
		this.button_soundOn = new GuiAbstractButton("guis/menu/button_yes", 
				new Vector2f(-0.49f, 0.05f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisAudioButtons);
				inGameSettings.disableAudio();
				GuiYesNo.isYesOptionAudio = InGameSettings.useAudioIn;
				button_soundOff.show(guisAudioButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		
		this.button_soundOff = new GuiAbstractButton("guis/menu/button_no", 
				new Vector2f(-0.49f, 0.05f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisAudioButtons);
				inGameSettings.enableAudio();
				GuiYesNo.isYesOptionAudio = InGameSettings.useAudioIn;
				button_soundOn.show(guisAudioButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		if(GuiYesNo.isYesOptionAudio)
		{
			this.button_soundOn.show(guisAudioButtons);
		}else{
			this.button_soundOff.show(guisAudioButtons);
		}

	}


	public void renderScreen() 
	{
		this.cx.guiRenderer.render(this.guisBackground);
	}
	
	@Override
	public void addToList() 
	{
	}

	@Override
	public void updateButtons() 
	{
		if(this.cx.isInMenuScene() && !this.cx.isInWorldScene())
		{this.button_audio_back.update();}
		
		if(this.cx.isInWorldScene() && !this.cx.isInMenuScene())
		{this.button_audio_back_game.update();}
		
		if(GuiYesNo.isYesOptionAudio == true)
		{this.button_soundOn.updateMulti(); this.button_soundOff.updateMulti();} 
		if(GuiYesNo.isYesOptionAudio == false)
		{this.button_soundOff.updateMulti(); this.button_soundOn.updateMulti();}
	}
	
}

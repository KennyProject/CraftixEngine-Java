package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.init.TextInit;

public class GuiOptionsMenu extends GuiScreen implements IGui
{
	public List<GuiQuad> guisMenuOptionsButtons = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisMenuOptionsBackground = new ArrayList<GuiQuad>();
	public boolean setFullscreen;
	/**The Main Craftix Instance.*/
	private Craftix cx;
	
	/**All init of Options Menu Gui textures.*/
	public GuiQuad gui_options_option;
	public GuiQuad gui_options_back_with_line;
	public GuiQuad gui_options_inGameBackground;
	public GuiQuad gui_options_lock;
	public GuiQuad gui_options_tip1;
	public GuiQuad gui_options_border;
	
	/**All init of Options Menu Gui Buttons-textures.*/
	public GuiAbstractButton button_options_back;
	public GuiAbstractButton button_options_back_game;
	public GuiAbstractButton button_options_audio;
	public GuiAbstractButton button_options_graphics;
	public GuiAbstractButton button_options_language;
	public GuiAbstractButton button_fullscreen_yes;
	public GuiAbstractButton button_fullscreen_no;
	public GuiAbstractButton button_fbo_yes;
	public GuiAbstractButton button_fbo_no;
	public GuiAbstractButton button_skybox_yes;
	public GuiAbstractButton button_skybox_no;
	public GuiAbstractButton button_water_yes;
	public GuiAbstractButton button_water_no;
	public GuiAbstractButton button_triangleMode_no;
	public GuiAbstractButton button_triangleMode_yes;
	public GuiAbstractButton button_soundOn;
	public GuiAbstractButton button_soundOff;
	
	public void loadOptionsMenuScreen(Craftix cxIn)
	{
		this.cx = cxIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	/**
	 * Load the option screen when user in game scene.
	 */
	public void loadOptionsInGameScreen(Craftix cxIn)
	{	
		this.cx = cxIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.gui_options_option = drawGui("guis/menu/options", this.cx.cxLoader, 0.0f, 0.75f, 0.40f, 0.45f);
		this.gui_options_back_with_line = drawGui("guis/menu/gui_background_with_line", this.cx.cxLoader, -0.21f, -0.45f, 0.80f, 1.0f);
		this.gui_options_lock = drawGui("guis/menu/lock", this.cx.cxLoader, -0.07f, -0.2f, 0.10f, 0.20f);
		this.gui_options_tip1 = drawGui("guis/menu/button_text", this.cx.cxLoader, -0.73f, 0.65f, 0.20f, 0.10f);
		this.gui_options_border = drawGui("guis/menu/gui_border", this.cx.cxLoader, -0.73f, 0.63f, 0.20f, 0.10f);
	}

	@Override
	public void drawGuiButtons() 
	{	
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
		
		this.button_options_back = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f,-0.72f), -0.04f, 0f) 
		{
			@Override
			public void onClick(IButton button) 
			{
			
				TextInit textInit = new TextInit();
				TextInit.removeOptionsPage();
				textInit.loadDefaultFonts();
				GuiRenderManager.renderOptionsMenu = false;
				cx.saveAllGameOptions();
				GuiRenderManager.renderMainMenu = true;
			}
			public void isVisible(boolean visibleIn) {}
		};
		if(this.cx.isInMenuScene() && !this.cx.isInWorldScene())
		{
			this.button_options_back.show(guisMenuOptionsButtons);
		}
		
		this.button_options_back_game = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f,-0.72f), -0.04f, 0f) 
		{
			@Override
			public void onClick(IButton button) 
			{
				TextInit.removeOptionsInGamePage();
				TextInit textInit = new TextInit();
				textInit.loadDefaultFonts();
				textInit.initPausePage(textInit.loader);
				GuiRenderManager.renderOptionsInGame = false;
				cx.saveAllGameOptions();
				WorldScene.inGameOptions = false;
				GuiRenderManager.renderInGameMenu = true;
			}
			public void isVisible(boolean visibleIn) {}
		};
		if(this.cx.isInWorldScene() && !this.cx.isInMenuScene())
		{
			this.button_options_back_game.show(guisMenuOptionsButtons);
		}
		
		this.button_options_graphics = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f, 0.22f), -0.04f, 0f)   
		{
			@Override
			public void onClick(IButton button) 
			{
				TextInit textInit = new TextInit();
				TextInit.removeOptionsPage();
				textInit.loadDefaultFonts();
				textInit.initGrapthicsPage(textInit.loader);
				GuiRenderManager.renderOptionsMenu = false;
				GuiRenderManager.renderGraphicMenu = true;
			}
			public void isVisible(boolean visibleIn) {}
		};
		if(!this.cx.isInWorldScene() && this.cx.isInMenuScene())
		{
			this.button_options_graphics.show(guisMenuOptionsButtons);
		}
		
		this.button_options_language = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f, 0.00f), -0.04f, 0f)  
		{
			@Override
			public void onClick(IButton button) 
			{
				TextInit.removeOptionsPage();
				TextInit.removeErrorsMessages();
				TextInit textInit = new TextInit();
				textInit.loadDefaultFonts();
				textInit.initLanguagePage(textInit.loader);
				GuiRenderManager.renderOptionsMenu = false;
				GuiRenderManager.renderLanguageMenu = true;
			}
			public void isVisible(boolean visibleIn) {}
		};
		if(!this.cx.isInWorldScene() && this.cx.isInMenuScene())
		{
			this.button_options_language.show(guisMenuOptionsButtons);
		}
		
		this.button_options_audio = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f, 0.44f), -0.04f, 0f)  
		{
			@Override
			public void onClick(IButton button) 
			{
				TextInit.removeOptionsPage();
				TextInit textInit = new TextInit();
				textInit.loadDefaultFonts();
				textInit.initAudioPage(textInit.loader);
				GuiRenderManager.renderOptionsMenu = false;
				GuiRenderManager.renderAudioMenu = true;
			}
			public void isVisible(boolean visibleIn) {}
		};
		if(this.cx.isInMenuScene() && !this.cx.isInWorldScene())
		{
			this.button_options_audio.show(guisMenuOptionsButtons);
		}
		
		this.button_fullscreen_yes = new GuiAbstractButton("guis/menu/button_yes", 
				new Vector2f(-0.55f, 0.41f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			@Override
			public void onClick(IButton button) 
			{
				Logger LOGGER = LogManager.getLogger();
				
		        try 
		        {
		        	button.hide(guisMenuOptionsButtons);
					cx.initDisplayMode(1280, 768, !Display.isFullscreen());
					InGameSettings.useFullscreenIn = false;
					GuiYesNo.isYesOptionFS = InGameSettings.useFullscreenIn;
					button_fullscreen_no.show(guisMenuOptionsButtons);
				} 
		        catch (LWJGLException e) 
		        {
					LOGGER.info("Can't change display mode.");
					e.printStackTrace();
				}
		      
			}
			public void isVisible(boolean visibleIn) {}
		};
		
		this.button_fullscreen_no = new GuiAbstractButton("guis/menu/button_no",
				new Vector2f(-0.55f, 0.41f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			
			@Override
			public void onClick(IButton button) 
			{
				Logger LOGGER = LogManager.getLogger();
				
		        try 
		        {
		        	button.hide(guisMenuOptionsButtons);
					cx.initDisplayMode(cx.getDisplayWidth(), 
							cx.getDisplayHeight(), !Display.isFullscreen());
					InGameSettings.useFullscreenIn = true;
					GuiYesNo.isYesOptionFS = InGameSettings.useFullscreenIn;
					button_fullscreen_yes.show(guisMenuOptionsButtons);
				} 
		        catch (LWJGLException e) 
		        {
					LOGGER.info("Can't change display mode.");
					e.printStackTrace();
				}
			}
			public void isVisible(boolean visibleIn) {}
		
		};
		if(GuiYesNo.isYesOptionFS)
		{
			this.button_fullscreen_yes.show(guisMenuOptionsButtons);
		}else{
			this.button_fullscreen_no.show(guisMenuOptionsButtons);
		}
		
		this.button_fbo_yes = new GuiAbstractButton("guis/menu/button_yes", 
				new Vector2f(-0.55f, 0.21f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			
			@Override
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisMenuOptionsButtons);
				inGameSettings.disableFbo();
				GuiYesNo.isYesOptionFBO = InGameSettings.useFboIn;
				button_fbo_no.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		
		this.button_fbo_no = new GuiAbstractButton("guis/menu/button_no", 
				new Vector2f(-0.55f, 0.21f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			
			@Override
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisMenuOptionsButtons);
				inGameSettings.enableFbo();
				GuiYesNo.isYesOptionFBO = InGameSettings.useFboIn;
				button_fbo_yes.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		if(GuiYesNo.isYesOptionFBO)
		{
			this.button_fbo_yes.show(guisMenuOptionsButtons);
		}else{
			this.button_fbo_no.show(guisMenuOptionsButtons);
		}
		
		this.button_skybox_yes = new GuiAbstractButton("guis/menu/button_yes", 
				new Vector2f(-0.55f, 0.01f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			
			@Override
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisMenuOptionsButtons);
				inGameSettings.disableRenderSkyBox();
				GuiYesNo.isYesOptionRS = InGameSettings.renderSkyBoxIn;
				button_skybox_no.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		
		this.button_skybox_no = new GuiAbstractButton("guis/menu/button_no", 
				new Vector2f(-0.55f, 0.01f + posY),  this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			
			@Override
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisMenuOptionsButtons);
				inGameSettings.enableRenderSkyBox();
				GuiYesNo.isYesOptionRS = InGameSettings.renderSkyBoxIn;
				button_skybox_yes.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		if(GuiYesNo.isYesOptionRS)
		{
			this.button_skybox_yes.show(guisMenuOptionsButtons);
		}else{
			this.button_skybox_no.show(guisMenuOptionsButtons);
		}
		
		this.button_water_yes = new GuiAbstractButton("guis/menu/button_yes", 
				new Vector2f(-0.55f, -0.19f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			@Override
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisMenuOptionsButtons);
				inGameSettings.disableRenderWater();
				GuiYesNo.isYesOptionRW = InGameSettings.renderWaterIn;
				button_water_no.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		
		this.button_water_no = new GuiAbstractButton("guis/menu/button_no", 
				new Vector2f(-0.55f, -0.19f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			@Override
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisMenuOptionsButtons);
				inGameSettings.enableRenderWater();
				GuiYesNo.isYesOptionRW = InGameSettings.renderWaterIn;
				button_water_yes.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		if(GuiYesNo.isYesOptionRW)
		{
			this.button_water_yes.show(guisMenuOptionsButtons);
		}else{
			this.button_water_no.show(guisMenuOptionsButtons);
		}
		
		this.button_triangleMode_yes = new GuiAbstractButton("guis/menu/button_yes", 
				new Vector2f(-0.55f, -0.39f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			@Override
			public void onClick(IButton button) 
			{
				button.hide(guisMenuOptionsButtons);
				
				button_triangleMode_no.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
		};
		
		this.button_triangleMode_no = new GuiAbstractButton("guis/menu/button_no", 
				new Vector2f(-0.55f, -0.39f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			@Override
			public void onClick(IButton button) 
			{

				button.hide(guisMenuOptionsButtons);
				
				button_triangleMode_yes.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
	
		this.button_triangleMode_no.show(guisMenuOptionsButtons);
		
		this.button_soundOn = new GuiAbstractButton("guis/menu/button_yes", 
				new Vector2f(-0.00f, 0.21f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisMenuOptionsButtons);
				inGameSettings.disableAudio();
				GuiYesNo.isYesOptionAudio = InGameSettings.useAudioIn;
				button_soundOff.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		
		this.button_soundOff = new GuiAbstractButton("guis/menu/button_no", 
				new Vector2f(-0.00f, 0.21f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisMenuOptionsButtons);
				inGameSettings.enableAudio();
				GuiYesNo.isYesOptionAudio = InGameSettings.useAudioIn;
				button_soundOn.show(guisMenuOptionsButtons);
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		if(this.cx.isInWorldScene() && !this.cx.isInMenuScene())
		{
			if(GuiYesNo.isYesOptionAudio)
			{
				this.button_soundOn.show(guisMenuOptionsButtons);
			}else{
				this.button_soundOff.show(guisMenuOptionsButtons);
			}
		}

	}
	


	@Override
	public void addToList() 
	{
		this.guisMenuOptionsButtons.add(gui_options_back_with_line);
		this.guisMenuOptionsButtons.add(gui_options_option);
		this.guisMenuOptionsButtons.add(gui_options_lock);
		this.guisMenuOptionsButtons.add(gui_options_border);
	}

	@Override
	public void updateButtons() 
	{
		if(this.cx.isInMenuScene() && !this.cx.isInWorldScene())
		{
			this.button_options_back.update();
			this.button_options_audio.update();
			this.button_options_graphics.update();
			this.button_options_language.update();
		}
		
		if(!this.cx.isInMenuScene() && this.cx.isInWorldScene())
		{
			this.button_options_back_game.update();
			if(GuiYesNo.isYesOptionAudio == true)
			{this.button_soundOn.updateMulti(); this.button_soundOff.updateMulti();} 
			if(GuiYesNo.isYesOptionAudio == false)
			{this.button_soundOff.updateMulti(); this.button_soundOn.updateMulti();}
		}
		
		if(GuiYesNo.isYesOptionFS == true)
		{this.button_fullscreen_yes.updateMulti(); this.button_fullscreen_no.updateMulti();} 
		if(GuiYesNo.isYesOptionFS == false)
		{this.button_fullscreen_no.updateMulti(); this.button_fullscreen_yes.updateMulti();}
		
		if(GuiYesNo.isYesOptionFBO == true)
		{this.button_fbo_yes.updateMulti(); this.button_fbo_no.updateMulti();} 
		if(GuiYesNo.isYesOptionFBO == false)
		{this.button_fbo_no.updateMulti(); this.button_fbo_yes.updateMulti();}
		
		if(GuiYesNo.isYesOptionRS == true)
		{this.button_skybox_yes.updateMulti(); this.button_skybox_no.updateMulti();} 
		if(GuiYesNo.isYesOptionRS == false)
		{this.button_skybox_no.updateMulti(); this.button_skybox_yes.updateMulti();}
		
		if(GuiYesNo.isYesOptionRW == true)
		{this.button_water_yes.updateMulti(); this.button_water_no.updateMulti();} 
		if(GuiYesNo.isYesOptionRW == false)
		{this.button_water_no.updateMulti(); this.button_water_yes.updateMulti();}
		
		this.button_triangleMode_no.updateMulti();
		this.button_triangleMode_yes.updateMulti();
	}
}

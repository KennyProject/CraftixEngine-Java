package com.kenny.craftix.client.language;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Splitter;
import com.kenny.craftix.client.font.GuiEngineText;
import com.kenny.craftix.client.font.GuiTextLoadingSplash;
import com.kenny.craftix.client.font.GuiTextWorld;
import com.kenny.craftix.client.resources.StillWorking;

public class Language 
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Splitter COLON_SPLITTER = Splitter.on('=');
	private File enLangFile;
	public File ruLangFile;
	/**
	 * This is all words useds in the engine. In each of the localization 
	 * files, these constants are equal to words from that language. Soon these 
	 * constants will not be because I will make the translation in 
	 * the text file.
	 */
	public static String INFO_CRAFTIX;
	public static String INFO_ENGINE_VER;
	public static String INFO_VERSION_TYPE;
	public static String INFO_USERNAME;
	public static String INFO_USER_ID;
	public static String INFO_SESSION_ID;
	public static String URI_TITLE;
	public static String CONTINUE;
	public static String COMPANY;
	public static String PLAY;
	public static String SINGLEPLAYER;
	public static String MULTIPLAYER;
	public static String OPTIONS;
	public static String CREDITS;
	public static String BACK;
	public static String FILE_CONFIG_ERROR;
	public static String FULLSCREEN;
	public static String FBO;
	public static String R_SKYBOX;
	public static String R_WATER;
	public static String TRIANGLE_MODE;
	public static String SOON_AUDIO;
	public static String CREDITS_AUTOR; 
	public static String GEN_WORLD;
	public static String LOAD_WORLD;
	public static String GEN_LP_WORLD;
	public static String GEN_F_WORLD;
	public static String GEN_E_WORLD;
	public static String LOADING_WORLD;
	public static String LOADING_WORLD_GEN_TERRIAN;
	public static String LOADING_WORLD_PROC_ENTITY;
	public static String RESUME;
	public static String SAVE_WORLD;
	public static String BACK_MENU;  
	public static String EXIT;
	public static String EXIT_GAME;
	public static String QUIT_GAME;
	public static String LATEST_UPDATES_TITLE;
	public static String UPDATES_VERSION;
	public static String UPDATES_LOG_DESC;
	public static String ENGINE_INFO_TITLE;
	public static String GRAPHICS;
	public static String GUI_SCALE;
	public static String AUDIO;
	public static String LANGUAGE;
	public static String LANG_DESC;
	public static String LANG_EN;
	public static String LANG_RU;
	public static String OPTION_CHANGE_DESC;
	public static String RESOLUTION;
	public static String SCREEN_1600x900;
	public static String SCREEN_1280x720;
	public static String SCREEN_1200x600;
	public static String SCREEN_640x540;
	public static String AUDIO_ON_OFF;
	public static String RETURN;
	public static String QUIT_GAME_DESC;
	public static String RESPAWN;
	public static String GL_ERROR_1;
	public static String GL_ERROR_2;
	public static String GL_ERROR_3;
	public static String GL_ERROR_4;
	public static String GL_ERROR_5;
	
	/***
	 * This java name of our current texts.
	 */
	public static GuiEngineText langEnEngineVersion;
	public static GuiEngineText langEnEngineVersion2;
	public static GuiEngineText langEnEngineVersionInfo;
	public static GuiEngineText langEnTypeOfVersion;
	public static GuiEngineText langEnEngineName;
	public static GuiEngineText langEnCompany;
	public static GuiEngineText langEnCompany2;
	public static GuiEngineText langEnUsername;
	public static GuiEngineText langEnUserId;
	public static GuiEngineText langEnSessionId;
	public static GuiEngineText langEnPlay;
	public static GuiEngineText langEnSingleplayer;
	public static GuiEngineText langEnMultiplayer;
	public static GuiEngineText langEnOptions;
	public static GuiEngineText langEnCredits;
	public static GuiEngineText langHasError;
	public static GuiEngineText langEnBackEI;
	public static GuiEngineText langEnBackLGU;
	public static GuiEngineText langEnBackOp1;
	public static GuiEngineText langEnBackOp2;
	public static GuiEngineText langEnBackCred1;
	public static GuiEngineText langEnBackCred2;
	public static GuiEngineText langEnBackSing1;
	public static GuiEngineText langEnBackSing2;
	public static GuiEngineText langEnBackMult1;
	public static GuiEngineText langEnBackMult2;
	public static GuiEngineText langEnBackLang;
	public static GuiEngineText langEnBackGrap;
	public static GuiEngineText langEnBackAudio;
	public static GuiEngineText langEnFullscreen;
	public static GuiEngineText langEnFramebuffer;
	public static GuiEngineText langEnRSkybox;
	public static GuiEngineText langEnRWater;
	public static GuiEngineText langEnTriangleMode;
	public static GuiEngineText langEnSoonAudio;
	public static GuiEngineText langEnAutorCred;
	public static GuiEngineText langEnTestWords;
	public static GuiEngineText langEnGenWorld;
	public static GuiEngineText langEnLoadWorld;
	public static GuiEngineText langEnGenLPWorld;
	public static GuiEngineText langEnGenFWorld;
	public static GuiEngineText langEnGenEWorld;
	public static GuiEngineText langEnExit;
	public static GuiEngineText langEnGraphics;
	public static GuiEngineText langEnLanguage;
	public static GuiEngineText langEnEngineInfo;
	public static GuiEngineText langEnAudio;
	public static GuiEngineText langEnLangDesc;
	public static GuiEngineText langEnLangEn;
	public static GuiEngineText langEnLangRu;
	public static GuiEngineText langEnQuitGame;
	public static GuiEngineText langEnOptionChangeDesc;
	public static GuiEngineText langEnGuiScale;
	public static GuiEngineText langEnResolution;
	public static GuiEngineText langEn1600x900;
	public static GuiEngineText langEn1280x720;
	public static GuiEngineText langEn1200x600;
	public static GuiEngineText langEn640x560;
	public static GuiEngineText langEnOptionPage;
	public static GuiEngineText langEnLangPage;
	public static GuiEngineText langEnCreditsPage;
	public static GuiEngineText langEnSinpleplayerPage;
	public static GuiEngineText langEnMultiplayerPage;
	public static GuiEngineText langEnAuidoPage;
	public static GuiEngineText langEnGraphicsPage;
	public static GuiEngineText langEnAudioEnDis;
	public static GuiEngineText langReturn;
	public static GuiEngineText langQuitTheGame;
	public static GuiEngineText langQuitTheGameDesc;
	public static GuiEngineText langQuit;
	public static GuiEngineText langLatestUpdatesPage;
	public static GuiEngineText langUpdatesVersions;
	public static GuiEngineText langUpdatesLogDesc;
	public static GuiEngineText langURI;
	public static GuiEngineText langURIContinue;
	public static GuiEngineText langURIBack;
	public static GuiTextLoadingSplash langLoadingWorld;
	public static GuiTextLoadingSplash langLoadingWorldGenTerrian;
	public static GuiTextLoadingSplash langLoadingWorldProcEntity;
	public static GuiTextWorld langEnResume;
	public static GuiTextWorld langEnOptiInGame;
	public static GuiTextWorld langEnSaveWorld;
	public static GuiTextWorld langEnBackMenu;
	public static GuiTextWorld langEnExitGame;
	public static GuiTextWorld langOptionPageInGame;
	public static GuiTextWorld langAuidoPageInGame;
	public static GuiTextWorld langBackOpInGame;
	public static GuiTextWorld langFullscreenInGame;
	public static GuiTextWorld langFramebufferInGame;
	public static GuiTextWorld langRSkyboxInGame;
	public static GuiTextWorld langRWaterInGame;
	public static GuiTextWorld langTriangleModeInGame;
	public static GuiTextWorld langSoonAudioInGame;
	public static GuiTextWorld langGraphicsInGame;
	public static GuiTextWorld langLanguageInGame;
	public static GuiTextWorld langAudioInGame;
	public static GuiTextWorld langBackAudioInGame;
	public static GuiTextWorld langAudioEnDisInGame;
	public static GuiTextWorld langOverBackToMenuInGame;
	public static GuiTextWorld langOverRespawnInGame;
	
	public Language(File enLangFile) 
	{
		this.enLangFile = new File(enLangFile, "lang/usEn.txt");
		this.saveLanguageFile();
	}
	
	@StillWorking
	@SuppressWarnings("deprecation")
	public void saveLanguageFile()
	{
		PrintWriter langwriter = null;
		
		try
		{
			langwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.enLangFile), StandardCharsets.UTF_8));
			langwriter.println("language.name=" + "English");
			langwriter.println("language.region=" + "United States");
			langwriter.println("language.fileName=" + "enUs.lang");
			langwriter.println("");
			langwriter.println("mainmenu.craftixEngine=" + INFO_CRAFTIX);
			langwriter.println("mainmenu.craftixVerison=" + INFO_ENGINE_VER);
			langwriter.println("mainmenu.play=" + PLAY);
			langwriter.println("mainmenu.singleplayer=" + SINGLEPLAYER);
			langwriter.println("mainmenu.multiplayer=" + MULTIPLAYER);
			langwriter.println("mainmenu.options=" + OPTIONS);
			langwriter.println("mainmenu.credits=" + CREDITS);
			langwriter.println("mainmenu.creditsDesc=" + CREDITS_AUTOR);
			langwriter.println("mainmenu.quitGame=" + QUIT_GAME);
			langwriter.println("mainmenu.quitGameDesc=" + QUIT_GAME_DESC);
			langwriter.println("mainmenu.quitGame.return=" + RESUME);
			langwriter.println("mainmenu.quitGame.exit=" + EXIT);
			langwriter.println("mainmenu.back=" + BACK);
			langwriter.println("mainmenu.error.hasError=" + "null");
			langwriter.println("mainmenu.options.optionChangeDesc=" + OPTION_CHANGE_DESC);
			langwriter.println("mainmenu.options.fullscreen=" + FULLSCREEN);
			langwriter.println("mainmenu.options.framebuffer=" + FBO);
			langwriter.println("mainmenu.options.renderSkybox=" + R_SKYBOX);
			langwriter.println("mainmenu.options.renderWater=" + R_WATER);
			langwriter.println("mainmenu.options.triangleMode=" + TRIANGLE_MODE);
			langwriter.println("mainmenu.options.soonAudioDesc=" + SOON_AUDIO);
			langwriter.println("mainmenu.options.audio=" + AUDIO);
			langwriter.println("mainmenu.options.audio.onOffSound=" + AUDIO_ON_OFF);
			langwriter.println("mainmenu.options.graphics=" + GRAPHICS);
			langwriter.println("mainmenu.options.graphics.guiScale=" + GUI_SCALE);
			langwriter.println("mainmenu.options.graphics.multisample= " + "null");
			langwriter.println("mainmenu.options.graphics.screenResolutionDesc=" + RESOLUTION);
			langwriter.println("mainmenu.options.graphics.screenResolution1600x900=" + SCREEN_1600x900);
			langwriter.println("mainmenu.options.graphics.screenResolution1280x720=" + SCREEN_1280x720);
			langwriter.println("mainmenu.options.graphics.screenResolution1200x600=" + SCREEN_1200x600);
			langwriter.println("mainmenu.options.graphics.screenResolution960x540=" + SCREEN_640x540);
			langwriter.println("mainmenu.options.language=" + LANGUAGE);
			langwriter.println("mainmenu.options.language.usEn=" + LANG_EN);
			langwriter.println("mainmenu.options.language.ruRu=" + LANG_RU);
			langwriter.println("mainmenu.singleplayer.generateWorld=" + GEN_WORLD);
			langwriter.println("mainmenu.singleplayer.generateLowPolyWorld=" + GEN_LP_WORLD);
			langwriter.println("mainmenu.singleplayer.generateFlatWorld=" + GEN_F_WORLD);
			langwriter.println("mainmenu.singleplayer.generateEmptyWorld=" + GEN_E_WORLD);
			langwriter.println("mainmenu.singleplayer.loadWorld=" + LOAD_WORLD);
			langwriter.println("game.pause=" + "null");
			langwriter.println("game.pause.resume=" + RESUME);
			langwriter.println("game.pause.saveWorld=" + SAVE_WORLD);
			langwriter.println("game.pause.options=" + OPTIONS);
			langwriter.println("game.pause.returnToMenu=" + BACK_MENU);
			langwriter.println("game.pause.exitFromGame=" + EXIT_GAME);
		}
		catch (Exception e)
		{
			LOGGER.warn("Localization file not found!");
			e.printStackTrace();
		}
		finally 
		{
			IOUtils.closeQuietly((Writer)langwriter);
		}
	}

}

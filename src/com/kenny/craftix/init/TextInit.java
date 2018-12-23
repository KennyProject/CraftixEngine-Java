package com.kenny.craftix.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.font.FontType;
import com.kenny.craftix.client.font.GuiEngineText;
import com.kenny.craftix.client.font.GuiText;
import com.kenny.craftix.client.font.GuiTextLoadingSplash;
import com.kenny.craftix.client.font.GuiTextWorld;
import com.kenny.craftix.client.font.Texts;
import com.kenny.craftix.client.font.render.TextMaster;
import com.kenny.craftix.client.font.text.manager.TextDraw;
import com.kenny.craftix.client.gui.GuiGraphics;
import com.kenny.craftix.client.gui.GuiMainMenu;
import com.kenny.craftix.client.gui.GuiScaled;
import com.kenny.craftix.client.language.localization.LocNull;
import com.kenny.craftix.client.language.localization.LocRu;
import com.kenny.craftix.client.language.localization.LocUsEn;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.Renderer;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.utils.Timer;

import static com.kenny.craftix.client.language.Language.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextInit 
{
	private static final Logger LOGGER = LogManager.getLogger();
	protected static final float DISTANCE_BETWEEN_TEXTS = 0.11f;
	public static boolean langError;
	/**Core in the game*/
	public Loader loader = new Loader();
	public static boolean loadTextOptions;
	public Timer timer = new Timer();
	public List<GuiEngineText> guisText0 = new ArrayList<GuiEngineText>();
	public Map<FontType, List<GuiEngineText>> text1 = new HashMap<FontType, List<GuiEngineText>>();
	private GuiScaled guiScale = new GuiScaled();
	public FontType selectFont;
	public boolean isRu;
	public boolean isEn;
	public Texts t = new Texts();
	public static float cX = 0f; public static float cY = 0f;
	/**This is X position to align text with different Gui sizes.*/
	public static float X = 0f;
	/**This is Y position to align text with different Gui sizes.*/
	public static float Y = 0f;
	public static float userLine = 0f;
	public static boolean initLinkPage = false;
	/**
	 * Is a list of localizations.
	 */
	public LocNull nullLang;
	public LocUsEn usEn = new LocUsEn();
	public LocRu ruRu = new LocRu();
	
	/**Font type list*/
	public FontType fontSans;
	public FontType fontArial;
	public FontType fontVerdana;
	public FontType fontCalibri;
	public FontType fontHarrington;
	public FontType fontSegoe;
	public FontType fontSegoeUI;
	public FontType fontTahoma;
	/***
	 * This is default font for this engine.
	 */
	public FontType fontCandara;
	public FontType fontCandaraEn;
	public FontType fontCandaraRus;
	
	public TextDraw drawText;

	
	public GuiText addText(String text, float size, FontType font, float posX, float posY,
			float maxLineLength, boolean isCentered)
	{	
		return new GuiText(text, size, font, 
				new Vector2f(posX, posY), maxLineLength, isCentered);
	}
	
	public GuiEngineText addText(String text, float posX, float posY)
	{	
		return new GuiEngineText(text, this.guiScale.getCurrentTextSize(), setDefaultFont(), 
				new Vector2f(posX, posY), 0.6f + userLine, false);
	}
	
	public GuiEngineText addTextCenter(String text, float posX, float posY)
	{	
		return new GuiEngineText(text, this.guiScale.getCurrentTextSize(), setDefaultFont(), 
				new Vector2f(posX, posY), 0.6f, true);
	}
	
	//==================//
	
	
	
	//==================//
	
	public GuiTextLoadingSplash addTextLoading(String text, float posX, float posY)
	{	
		return new GuiTextLoadingSplash(text, this.guiScale.getCurrentTextSize(), setDefaultFont(), 
				new Vector2f(posX, posY), 0.6f + userLine, false);
	}
	
	public GuiTextLoadingSplash addTextCenterLoading(String text, float posX, float posY)
	{	
		return new GuiTextLoadingSplash(text, this.guiScale.getCurrentTextSize(), setDefaultFont(), 
				new Vector2f(posX, posY), 0.6f, true);
	}
	
	public GuiTextWorld addTextWorld(String text, float posX, float posY)
	{	
		return new GuiTextWorld(text, this.guiScale.getCurrentTextSize(), setDefaultFont(), 
				new Vector2f(posX, posY), 0.6f, false);
	}
	
	public GuiTextWorld addTextWorldCenter(String text, float posX, float posY)
	{	
		return new GuiTextWorld(text, this.guiScale.getCurrentTextSize(), setDefaultFont(), 
				new Vector2f(posX, posY), 0.6f, true);
	}
	
	/**
	 * Set the for font localization.
	 * @return - the select user font.
	 */
	public FontType setDefaultFont()
	{
		this.selectFont = fontCandara;
			return this.selectFont;
	}
	
	/**
	 * Load all localization text into the game engine.
	 */
	public void loadEngineText(Craftix cx)
	{
		try
		{
			this.isEn = false;
			this.isRu = true;
			InGameSettings.setLanguage();
			this.loadDefaultFonts();
			if(isEn && !isRu)
			{
				this.usEn.loadUsEnLocalization();
			}
			if(isRu && !isEn)
			{
				this.ruRu.loadRuLocalization();
			}
		}
		catch(Exception e)
		{
			langError = true;
			LOGGER.error("Could not load the language used. Localization requires at least one language.");
		}
		
		this.drawText = new TextDraw();
		this.drawText.textDraw(cx);
		
		if(InGameSettings.hasError == true)
		{
			this.initErrorsMessages(loader);
		}

	}
	
	public void loadGuiScale()
	{
		this.guiScale.loadGuiScale();
		this.loadTextScaleFactor();
	}
	
	/***
	 * Initiale scale text for each scale factor size.
	 */
	public void loadTextScaleFactor()
	{
		if(InGameSettings.guiScaleLargeIn)
		{
			cX = Display.getHeight() / Display.getWidth() + 0.82f; cY = 0.94f;
			X = 0f; Y = 0f;
		}
		
		if(InGameSettings.guiScaleMediumIn)
		{
			cX = Display.getHeight() / Display.getWidth() + 0.83f; cY = 0.94f;
			X = -0.005f; Y = -0.002f;
		}
		
		if(InGameSettings.guiScaleSmallIn)
		{
			cX = Display.getHeight() / Display.getWidth() + 0.85f; cY = 0.94f;
			X = -0.01f; Y = -0.005f;
		}
	}
	
	
	public void initErrorsMessages(Loader loader)
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		
		GuiEngineText tHasError = addTextCenter(FILE_CONFIG_ERROR, 0.2f, 0.0f - Y); langHasError = tHasError; langHasError.setColour(1, 0, 0);
	}
	
	
	public void initSingleplayerPage(Loader loader)
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiEngineText t0007 = addText(BACK, 0.81f - X, 0.835f - Y); langEnBackSing1 = t0007; langEnBackSing1.setColour(1, 1, 1);
		GuiEngineText t0008 = addTextCenter(GEN_WORLD, -0.100f, 0.33f - Y); langEnGenWorld = t0008; langEnGenWorld.setColour(1, 1, 1);
		GuiEngineText t0009 = addTextCenter(GEN_LP_WORLD, -0.100f, 0.44f - Y); langEnGenLPWorld = t0009; langEnGenLPWorld.setColour(1, 1, 1);
		GuiEngineText t0010 = addTextCenter(GEN_F_WORLD, -0.100f, 0.55f - Y); langEnGenFWorld = t0010; langEnGenFWorld.setColour(1, 1, 1);
		GuiEngineText t0011 = addTextCenter(GEN_E_WORLD, -0.100f, 0.66f - Y); langEnGenEWorld = t0011; langEnGenEWorld.setColour(1, 1, 1);
		GuiEngineText t0012 = addTextCenter(LOAD_WORLD, -0.100f, 0.83f - Y); langEnLoadWorld = t0012; langEnLoadWorld.setColour(1, 1, 1);
	}
	
	public void initOptionPage(Loader loader)
	{	
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiEngineText tOptionPage = addText(OPTIONS + ":", 0.05f, 0.15f); langEnOptionPage = tOptionPage; langEnOptionPage.setColour(1, 1, 1);
		GuiEngineText t0030 = addText(BACK, 0.81f - X, 0.835f - Y); langEnBackOp1 = t0030; langEnBackOp1.setColour(1, 1, 1);
		GuiEngineText t0031 = addText(FULLSCREEN, 0.05f, 0.27f); langEnFullscreen = t0031; langEnFullscreen.setColour(1, 1, 1);
		GuiEngineText t0032 = addText(FBO, 0.05f, 0.37f) ;langEnFramebuffer = t0032; langEnFramebuffer.setColour(1, 1, 1);
		GuiEngineText t0033 = addText(R_SKYBOX, 0.05f, 0.47f); langEnRSkybox = t0033; langEnRSkybox.setColour(1, 1, 1);
		GuiEngineText t0034 = addText(R_WATER, 0.05f, 0.57f); langEnRWater = t0034; langEnRWater.setColour(1, 1, 1);
		GuiEngineText t0035 = addText(TRIANGLE_MODE, 0.05f, 0.67f); langEnTriangleMode = t0035; langEnTriangleMode.setColour(1, 1, 1);
		GuiEngineText t0036 = addTextCenter(GRAPHICS, 0.534f - X, 0.37f); langEnGraphics = t0036; langEnGraphics.setColour(1, 1, 1);
		GuiEngineText t0037 = addTextCenter(LANGUAGE, 0.534f - X, 0.48f); langEnLanguage = t0037; langEnLanguage.setColour(1, 1, 1);
		GuiEngineText t0038 = addTextCenter(AUDIO, 0.534f - X, 0.26f); langEnAudio = t0038; langEnAudio.setColour(1, 1, 1);
		GuiEngineText tSoon = addText(SOON_AUDIO, 0.35f, 0.63f); langEnSoonAudio = tSoon; langEnSoonAudio.setColour(1, 1, 1);
	}
	
	public void initOptionInGamePage(Loader loader)
	{	
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiTextWorld tOptionPage = addTextWorld(OPTIONS + ":", 0.05f, 0.15f); langOptionPageInGame = tOptionPage;langOptionPageInGame.setColour(1, 1, 1);
		GuiTextWorld t0030 = addTextWorld(BACK, 0.81f - X, 0.835f - Y); langBackOpInGame = t0030; langBackOpInGame.setColour(1, 1, 1);
		GuiTextWorld t0031 = addTextWorld(FULLSCREEN, 0.05f, 0.27f); langFullscreenInGame = t0031; langFullscreenInGame.setColour(1, 1, 1);
		GuiTextWorld t0032 = addTextWorld(FBO, 0.05f, 0.37f); langFramebufferInGame = t0032; langFramebufferInGame.setColour(1, 1, 1);
		GuiTextWorld t0033 = addTextWorld(R_SKYBOX, 0.05f, 0.47f); langRSkyboxInGame = t0033; langRSkyboxInGame.setColour(1, 1, 1);
		GuiTextWorld t0034 = addTextWorld(R_WATER, 0.05f, 0.57f); langRWaterInGame = t0034; langRWaterInGame.setColour(1, 1, 1);
		GuiTextWorld t0035 = addTextWorld(TRIANGLE_MODE, 0.05f, 0.67f); langTriangleModeInGame = t0035; langTriangleModeInGame.setColour(1, 1, 1);
		GuiTextWorld t0038 = addTextWorldCenter(AUDIO, 0.134f - X, 0.26f); langAudioInGame = t0038; langAudioInGame.setColour(1, 1, 1);
		GuiTextWorld t0039 = addTextWorld(AUDIO_ON_OFF, 0.320f - X, 0.37f); langAudioEnDisInGame = t0039; langAudioEnDisInGame.setColour(1, 1, 1);
	}
	
	public void initGrapthicsPage(Loader loader)
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiEngineText tGraphicsPage = addText(GRAPHICS + ":", 0.05f, 0.15f); langEnGraphicsPage = tGraphicsPage; langEnGraphicsPage.setColour(1, 1, 1);
		GuiEngineText t0040 = addText(BACK, 0.81f - X , 0.835f - Y); langEnBackGrap = t0040; langEnBackGrap.setColour(1, 1, 1);
		/**t0041 - now in GuiGraphics.*/
		GuiEngineText t0042 = addText(GUI_SCALE, 0.05f, 0.27f); langEnGuiScale = t0042; langEnGuiScale.setColour(1, 1, 1);
		GuiEngineText t0043 = addText(RESOLUTION, 0.33f, 0.27f); langEnResolution = t0043; langEnResolution.setColour(1, 1, 1);
		GuiEngineText t0044 = addTextCenter(SCREEN_1600x900, 0.14f, 0.35f - Y); langEn1600x900 = t0044; langEn1600x900.setColour(1, 1, 1);
		GuiEngineText t0045 = addTextCenter(SCREEN_1280x720, 0.14f, 0.46f - Y); langEn1280x720 = t0045; langEn1280x720.setColour(1, 1, 1);
		GuiEngineText t0046 = addTextCenter(SCREEN_1200x600, 0.14f, 0.57f - Y); langEn1200x600 = t0046; langEn1200x600.setColour(1, 1, 1);
		GuiEngineText t0047 = addTextCenter(SCREEN_640x540, 0.14f, 0.68f - Y); langEn640x560 = t0047; langEn640x560.setColour(1, 1, 1);
	}
	
	
	public void initLanguagePage(Loader loader)
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiEngineText tLangPage = addText(LANGUAGE + ":", 0.05f, 0.15f); langEnLangPage = tLangPage; langEnLangPage.setColour(1, 1, 1);
		GuiEngineText t0050 = addText(BACK, 0.81f - X , 0.835f - Y); langEnBackLang = t0050; langEnBackLang.setColour(1, 1, 1);
		GuiEngineText t0051 = addTextCenter(LANG_DESC, 0.2f, 0.25f); langEnLangDesc = t0051; langEnLangDesc.setColour(1, 1, 1);
		GuiEngineText t0052 = addTextCenter(LANG_EN, 0.20f, 0.43f); langEnLangEn = t0052; langEnLangEn.setColour(1, 1, 1);
		GuiEngineText t0053 = addTextCenter(LANG_RU, 0.20f, 0.53f); langEnLangRu = t0053; langEnLangRu.setColour(1, 1, 1);
	}
	
	public void initAudioPage(Loader loader)
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiEngineText tAudioPage = addText(AUDIO + ":", 0.05f, 0.15f); langEnAuidoPage = tAudioPage; langEnAuidoPage.setColour(1, 1, 1);
		GuiEngineText t0065 = addText(BACK, 0.81f - X , 0.835f - Y); langEnBackAudio = t0065; langEnBackAudio.setColour(1, 1, 1);	
		GuiEngineText t0066 = addText(AUDIO_ON_OFF, 0.08f, 0.45f); langEnAudioEnDis = t0066; langEnAudioEnDis.setColour(1, 1, 1);
	}
	
	public void initAudioInGamePage(Loader loader)
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiTextWorld tAudioPage = addTextWorld(AUDIO + ":", 0.05f, 0.15f); langAuidoPageInGame = tAudioPage; langAuidoPageInGame.setColour(1, 1, 1);
		GuiTextWorld t0065 = addTextWorld(BACK, 0.81f - X , 0.835f - Y); langBackAudioInGame = t0065; langBackAudioInGame.setColour(1, 1, 1);	
		GuiTextWorld t0066 = addTextWorld(AUDIO_ON_OFF, 0.08f, 0.45f); langAudioEnDisInGame = t0066; langAudioEnDisInGame.setColour(1, 1, 1);
	}
	
	public void initCreditsPage(Loader loader)
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiEngineText t0100 = addText(BACK, 0.81f - X, 0.835f - Y); langEnBackCred1 = t0100;langEnBackCred1.setColour(1, 1, 1);
		GuiEngineText t0101 = addText(CREDITS_AUTOR, 0.2f, 0.3f); langEnAutorCred = t0101;langEnAutorCred.setColour(1, 1, 1);
	}
	
	public void initLoadngWorldPage()
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiTextLoadingSplash tLoadingWorld = addTextLoading(LOADING_WORLD, 0.0f, 0.0f); langLoadingWorld = tLoadingWorld; langLoadingWorld.setColour(1, 1, 1);
	}
	
	public void initPausePage(Loader loader)
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiTextWorld t0050 = addTextWorldCenter(RESUME, 0.205f, 0.37f); langEnResume = t0050; langEnResume.setColour(1, 1, 1);
		GuiTextWorld t0051 = addTextWorldCenter(SAVE_WORLD, 0.103f, 0.48f); langEnSaveWorld = t0051; langEnSaveWorld.setColour(1, 1, 1);
		GuiTextWorld t0052 = addTextWorldCenter(OPTIONS, 0.303f, 0.48f); langEnOptiInGame = t0052; langEnOptiInGame.setColour(1, 1, 1);
		GuiTextWorld t0053 = addTextWorldCenter(BACK_MENU, 0.205f, 0.59f); langEnBackMenu = t0053; langEnBackMenu.setColour(1, 1, 1);
		GuiTextWorld t0054 = addTextWorldCenter(EXIT_GAME, 0.205f, 0.70f); langEnExitGame = t0054; langEnExitGame.setColour(1, 1, 1);
	}
	
	public void initGameOverPage(Loader loader)
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiTextWorld tReturnToMenu = addTextWorldCenter(BACK_MENU, 0.075f, 0.68f); langOverBackToMenuInGame = tReturnToMenu; langOverBackToMenuInGame.setColour(1, 1, 1);
		GuiTextWorld tRespawn = addTextWorldCenter(RESPAWN, 0.325f, 0.68f); langOverRespawnInGame = tRespawn; langOverRespawnInGame.setColour(1, 1, 1);
	}
	
	/***
	 * Just load the text to the game display.
	 */
	public void loadTextGame()
	{
		this.loadGuiScale();
		TextMaster.init(loader);
		this.loadDefaultFonts();
		
		GuiText t0500 = addText("Craftix: " + CraftixOld.VERSION + " - Kenny AB", 1.5f, fontCandara, -0.12f, 0.0f, 0.5f, true);
		t0500.setColour(1, 1, 1);
		
		GuiText t0501 = addText("Fps: 60", 1.5f, fontCandara, -0.20f, 0.05f, 0.5f, true);
		t0501.setColour(1, 1, 1);
			
		GuiText t0502 = addText("Use FBOs: " , 1.5f, fontCandara, -0.181f, 0.10f, 0.5f, true);
		t0502.setColour(1, 1, 1);
				
		GuiText t0503 = addText("" + InGameSettings.useFboIn, 1.5f, fontCandara, -0.104f, 0.10f, 0.5f, true);
		t0503.setColour(1, 1, 1);
				
		GuiText t0504 = addText("Use Shaders: " , 1.5f, fontCandara, -0.169f, 0.15f, 0.5f, true);
		t0504.setColour(1, 1, 1);		
	
		GuiText t0505 = addText("" + Renderer.isUseShaders, 1.5f, fontCandara, -0.08f, 0.15f, 0.5f, true);
		t0505.setColour(1, 1, 1);		
				
		GuiText t0506 = addText("Use Normal Maps: " , 1.5f, fontCandara, -0.146f, 0.20f, 0.5f, true);
		t0506.setColour(1, 1, 1);			
		
		GuiText t0507 = addText("" + Renderer.isUseNormalMaps, 1.5f, fontCandara, -0.033f, 0.20f, 0.5f, true);
		t0507.setColour(1, 1, 1);
				
		GuiText t0508 = addText("Use Triangles: " , 1.5f, fontCandara, -0.165f, 0.25f, 0.5f, true);
		t0508.setColour(1, 1, 1);
		
		GuiText t0509 = addText("" + Renderer.isUseTringles, 1.5f, fontCandara, -0.073f, 0.25f, 0.5f, true);
		t0509.setColour(1, 1, 1);
				
		GuiText t0510 = addText("Use Contrast: " , 1.5f, fontCandara, -0.1665f, 0.30f, 0.5f, true);
		t0510.setColour(1, 1, 1);
				
		GuiText t0511 = addText("" + PostEffectsInit.isContrast, 1.5f, fontCandara, -0.075f, 0.30f, 0.5f, true);
		t0511.setColour(1, 1, 1);
				
		GuiText t0512 = addText("Use Blur: " , 1.5f, fontCandara, -0.1865f, 0.35f, 0.5f, true);
		t0512.setColour(1, 1, 1);
				
		GuiText t0513 = addText("" + PostEffectsInit.isRenderBlur, 1.5f, fontCandara, -0.117f, 0.35f, 0.5f, true);
		t0513.setColour(1, 1, 1);
				
		GuiText t0514 = addText("Use Bloom: " , 1.5f, fontCandara, -0.1753f, 0.40f, 0.5f, true);
		t0514.setColour(1, 1, 1);
				
		GuiText t0515 = addText("" + PostEffectsInit.isRenderBloom, 1.5f, fontCandara, -0.094f, 0.40f, 0.5f, true);
		t0515.setColour(1, 1, 1);				
	}
	
	/***
	 * Prepare load font to text in cache.
	 */
	public void loadDefaultFonts()
	{
		this.fontCandara = new FontType(this.loader.loadFontAtlas("candara"), "candara");
		this.fontCandaraEn = new FontType(this.loader.loadFontAtlas("candaraEn"), "candaraEn");
		this.fontCandaraRus = new FontType(this.loader.loadFontAtlas("candaraRus"), "candaraRus");
	}
	
	public void loadFonts()
	{
		this.fontSans = new FontType(loader.loadFontAtlas("sans"), "sans");
		this.fontArial = new FontType(loader.loadFontAtlas("arial"), "arial");
		this.fontCalibri = new FontType(loader.loadFontAtlas("calibri"),"calibri");
		this.fontHarrington = new FontType(loader.loadFontAtlas("harrington"), "harrington");
		this.fontSegoe = new FontType(loader.loadFontAtlas("segoe"), "segoe");
		this.fontSegoeUI = new FontType(loader.loadFontAtlas("segoeUI"), "segoeUI");
		this.fontTahoma = new FontType(loader.loadFontAtlas("tahoma"),"tahoma");
	}
	
	public void addToList()
	{
		//this.text1.put(fontCandara, langEnTestWords);
	}
	
	
	public static void reoveURLPage()
	{
		if(initLinkPage)
		{
			langURI.remove();
			langURIContinue.remove();
			langURIBack.remove();
			GuiMainMenu.isOpen = false;
			initLinkPage = false;
		}
	}
	public static void removeErrorsMessages()
	{	
		if(InGameSettings.hasError)
		{
			langHasError.remove();
		}
	}
	public static void removeOptionsPage()
	{
		langEnOptionPage.remove();
		langEnBackOp1.remove();
		langEnFullscreen.remove();
		langEnFramebuffer.remove();
		langEnRSkybox.remove();
		langEnRWater.remove();
		langEnTriangleMode.remove();
		langEnAudio.remove();
		langEnGraphics.remove();
		langEnLanguage.remove();
		langEnSoonAudio.remove();
	}
	public static void removeGraphicsPage()
	{
		langEnGraphicsPage.remove();
		langEnBackGrap.remove();
		if(GuiGraphics.isDescLoad)
		{
			langEnOptionChangeDesc.remove();
		}
		langEnGuiScale.remove();
		langEnResolution.remove();
		langEn1600x900.remove();
		langEn1280x720.remove();
		langEn1200x600.remove();
		langEn640x560.remove();
	}
	public static void removeAudioPage()
	{
		langEnAuidoPage.remove();
		langEnBackAudio.remove();
		langEnAudioEnDis.remove();
	}
	public static void removeLanguagePage()
	{
		langEnLangPage.remove();
		langEnBackLang.remove();
		langEnLangDesc.remove();
		langEnLangEn.remove();
		langEnLangRu.remove();
	}
	public static void removeCreditsPage()
	{
		langEnBackCred1.remove();
		langEnAutorCred.remove();
	}
	public static void removeInGamePausePage()
	{
		langEnResume.remove();
		langEnSaveWorld.remove();
		langEnOptiInGame.remove();
		langEnBackMenu.remove();
		langEnExitGame.remove();
	}
	public static void removeOptionsInGamePage()
	{
		langOptionPageInGame.remove();
		langBackOpInGame.remove();
		langFullscreenInGame.remove();
		langFramebufferInGame.remove();
		langRSkyboxInGame.remove();
		langRWaterInGame.remove();
		langTriangleModeInGame.remove();
		langAudioInGame.remove();
		langAudioEnDisInGame.remove();
	}
	public static void removeAudioInGame()
	{
		langAuidoPageInGame.remove();
		langAudioEnDisInGame.remove();
		langBackAudioInGame.remove();
	}
	public static void removeLoadingWorldPage()
	{
		langLoadingWorld.remove();
	}
	public static void removeGameOverPage()
	{
		langOverBackToMenuInGame.remove();
		langOverRespawnInGame.remove();
	}
	
	public boolean getLangRu()
	{
		return this.isRu;
	}
	
	public boolean getLangEn()
	{
		return this.isEn;
	}
	
}

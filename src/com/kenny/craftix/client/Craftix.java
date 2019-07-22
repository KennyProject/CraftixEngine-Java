package com.kenny.craftix.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.util.Log;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kenny.craftix.client.audio.AudioMaster;
import com.kenny.craftix.client.font.render.FontRenderer;
import com.kenny.craftix.client.font.render.TextMaster;
import com.kenny.craftix.client.gui.GuiAdder;
import com.kenny.craftix.client.gui.GuiLoadingSplash;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.language.Language;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.PngDecoder;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.postEffects.PostProcessing;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.resources.StillWorking;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.settings.KeyBinding;
import com.kenny.craftix.client.settings.Session;
import com.kenny.craftix.init.TextInit;
import com.kenny.craftix.main.GameConfiguration;
import com.kenny.craftix.utils.KennyCopyright;
import com.kenny.craftix.utils.MouseHelper;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.utils.Util;
import com.kenny.craftix.utils.crash.Crash;
import com.kenny.craftix.utils.data.DataFixManager;
import com.kenny.craftix.utils.data.DataFixer;
import com.kenny.launcher.UsernameManager;

@KennyCopyright
public class Craftix 
{
	/**Main Game Engine Configuration. Title, Version etc...*/
	public static final String TITLE = "Craftix";
	public static final String TITLE_DEMO = "Craftix Demo";
	public static final String VERSION = " 0.0.16";
	public static final String VERSION_TYPE = "Alpha";
	/**Called in other classes where logging is required.*/
	public static final String LOGGER_INSTANCE = "Craftix";
	public static final Logger LOGGER = LogManager.getLogger(LOGGER_INSTANCE);
	public static final boolean IS_RUNNING_ON_MAC = Util.getOSType() == Util.EnumOS.OSX;
	/**Available screen resolutions on the Mac OS.*/
	private static final List<DisplayMode> MAC_DISPLAY_MODES = Lists.newArrayList
			(new DisplayMode(2560, 1600), 
					new DisplayMode(2880, 1800));
	/**
	 * Main Pre-Initialization folders, resoloution from Game Configuration.
	 */
	/**Assets folder file. (Its not assets folder in jar file. Its folder for custom assets.)*/
	private final File fileAssets;
	/**Resourcepacks folder file.*/
	private final File fileResourcepacks;
	private final String engineVersion;
	/**Type of engine version. 'Alpha, Beta, Release'*/
	private final String engineVersionType;
	private final Session engineSession;
	/**If its demo that means its just demonstraction futures, or new stuff.*/
	private final boolean isDemoVersion;
	/**Close the game engine when user click on exit display bar.*/
	private boolean ON_EXIT_CLOSE;
	/**Checks for the pause stage. If the game is paused, some rendering moments stop.*/
	public  boolean isGamePause;
	private boolean jvm64bit;
	/**Checks when the game engine is running.*/
	private boolean isRunning;
	private boolean isOutMemoryRunning;
	/**Location of the engine options file.*/
	private File optionsFile;
	private File userdataFile;
	/**Location of lang's localization files.*/
	private File langEnFile;
	/**Load the language the the engine.*/
	private Language language;
	private InGameSettings inGameSettings;
	/**Instal instance crash manager.*/
	public Crash crash = new Crash();
	/**Folder '.craftix' with files and options.*/
	public File cxDataDir;
	private final DataFixer dataFixer;
	private GameConfiguration gameConfiguration;
	public boolean fullscreen;
	 /**Vertical Syncroniztion for a fullscreen mode window.*/
    public boolean vSync;
    private TextureManager textureManager;
	/**
	 * This resolution of the screen.
	 */
	public int displayWidth;
	public int displayHeight;
	long systemTime = getCurrentTime();
	private Timer timer = new Timer();
	public MouseHelper mouseHelper = new MouseHelper();
	/**Main Menu Scene.*/
	public MainMenuScene mainMenu;
	/**World scene.*/
	private WorldScene world;
	private boolean isInMenuScene = true;
	/**Check if user in the loaded world.*/
	private boolean isInWorldScene = false;
	public boolean quitGameScene = false;
	public boolean backToMenu = false;
	/**Does the actual gameplay have focus. If so then mouse and keys will effect the player instead of menus. */
	private boolean inGameHasFocus;
	/**Core of the game engine. Help load textures, models, and same on..*/
	public Loader cxLoader;
	/**Initialize all text in game enigne.*/
	private TextInit textInit;
	/**Render all characters and effect on text with shader and same text.*/
	private FontRenderer textRenderer;
	/**Render the guis into the game engine.*/
	public GuiRenderer guiRenderer;
	/**This main instance of the 'Craftix' class.*/
	public Craftix INSTANCE;
	private GameConfiguration gameConfig;
	public UsernameManager usernameManager;
	public GuiAdder guiAdder = new GuiAdder();
	public GuiLoadingSplash guiLoading = new GuiLoadingSplash();
	
	public Craftix(GameConfiguration gameConfiguration)
	{
		INSTANCE = this;
		this.gameConfig = gameConfiguration;
		this.cxDataDir = gameConfiguration.folderInfo.cxDataDir;
		this.fileAssets = gameConfiguration.folderInfo.assetsDir;
		this.fileResourcepacks = gameConfiguration.folderInfo.resourcepackDir;
		this.engineVersion = gameConfiguration.engineInfo.engineVersion;
		this.engineVersionType = gameConfiguration.engineInfo.typeOfVersion;
		this.isDemoVersion = gameConfiguration.engineInfo.isDemo;
		this.engineSession = gameConfiguration.userInfo.session;
		this.displayWidth = gameConfiguration.displayInfo.displayWidth > 0 ?
				gameConfiguration.displayInfo.displayWidth : 1;
		this.displayHeight = gameConfiguration.displayInfo.displayHeight > 0 ?
				gameConfiguration.displayInfo.displayHeight : 1;
		this.fullscreen = gameConfiguration.displayInfo.fullscreen;
		Locale.setDefault(Locale.ROOT);
		ResourceLocation.setCraftixProfileFolder();
		ImageIO.setUseCache(false);
		this.dataFixer = DataFixManager.createFixer();
		this.userdataFile = new File(ResourceLocation.CRAFTIX_FOLDER);
		this.usernameManager = new UsernameManager(this, this.userdataFile);
		this.cxLoader = new Loader();
		this.textInit = new TextInit();
		this.mainMenu = new MainMenuScene();
		this.world = new WorldScene();
		
	}
	
	/***
	* Run the hole game engine. Call super classes and load the scenes.
	 * @throws LWJGLException 
	*/
	public void run() throws LWJGLException
	{
		this.isRunning = true;
		
		try
		{
		}
		catch(Exception e)
		{
			this.crash.crashManager(1);
			LOGGER.error("Failed load the game engine!");
			e.printStackTrace();
			this.shutdown(-1);
		}
		
		while(true)
		{
			if(!this.isRunning() && this.isOutMemoryRunning)
			{
				try
				{
					;
				}
				catch(OutOfMemoryError noMemoryE)
				{
					LOGGER.fatal("Crash occurred due to lack of memory on the computer.");
					System.gc();
				}
			}
		}
	}
	
	/**
	 * Load the game: Initialization: Loading the main menu scene-screen.
	 * @throws LWJGLException
	 */
	public void init() throws LWJGLException
	{
		try
		{
			LOGGER.info("============= Craftix Info =============");
			this.langEnFile = new File(ResourceLocation.CRAFTIX_FOLDER);
			this.optionsFile = new File(ResourceLocation.CRAFTIX_FOLDER);
			this.language = new Language(this.langEnFile);
			this.inGameSettings = new InGameSettings(this, this.optionsFile);
			LOGGER.info("Current User: {}", (Object)this.engineSession.getUsername());
			LOGGER.info("LWJGL Version: " + (Object)Sys.getVersion());
			LOGGER.info("System 64 bit: " + (Object)Sys.is64Bit());
			LOGGER.info("Game resolution: " + InGameSettings.displayWidthIn + " x " + InGameSettings.displayHeightIn);
			LOGGER.info("Current Time:" + (Object)Sys.getTime());
			LOGGER.info("Game options configuration succes loading.");
			this.isJvm64bit();
			this.startTimerHackThread();
			this.disablePngDecoderInfo();
			this.setDisplayIcon();
			this.initDisplayMode();
			this.createDisplay();
			for (int j = 0; j < Math.min(10, this.timer.elapsedTicks); ++j)
		    {
		        this.runTick();
		    }
			this.setIngameNotInFocus();
			this.checkGLError("Pre startup");
			GlHelper.enableTexture2d();
			this.guiRenderer = new GuiRenderer(this.cxLoader);
			this.textRenderer = new FontRenderer();
			LOGGER.info("Display Framebuffer set on display resolution.");
			this.loadDefaultLanguage();
			AudioMaster.init();
			this.guiAdder.guiInit(this);
			this.textureManager = new TextureManager();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads scenes in the correct order and if necessary continues from the desired point.
	 * @throws LWJGLException 
	 */
	public void sceneLoader() throws LWJGLException
	{
		this.world.isGamePause = false;
		this.isInWorldScene = false;
		this.isInMenuScene = true;
		this.backToMenu = false; 
		this.quitGameScene = false;
		if(this.isInMenuScene())
		{
			try 
			{

			} 
			catch (LWJGLException e) 
			{
				this.crash.crashManager(2);
				this.saveAllGameOptions();
				LOGGER.error("Display in main menu not start updating.");
				e.printStackTrace();
			}
			
			if(!this.isInWorldScene() && this.isInMenuScene())
			{
				this.closeDisplay();
			}
			
		}
		if(this.isInWorldScene() && !this.isInMenuScene())
		{
			this.guiLoading.loadLoadingScreen();
			this.checkGLError("World Loading");
			this.saveAllGameOptions();
			this.isGamePause = false;
			this.quitTheScene();
			
			if(!this.isInMenuScene())
			{
				this.closeDisplay();
			}
		}
		this.saveAllGameOptions();
		
	}
	
	public void sceneReloader() throws LWJGLException
	{
		this.world.isGamePause = false;
		this.isInWorldScene = false;
		this.isInMenuScene = true;
		this.backToMenu = false; 
		this.quitGameScene = false;
		
		if(this.isInMenuScene())
		{
			try
			{
				this.updateMainMenu();
			}
			catch (LWJGLException e)
			{
				e.printStackTrace();
			}
		}
		if(this.isInWorldScene() && !this.isInMenuScene())
		{
			GuiRenderManager.renderInGameMenu = false;
			if(GuiRenderManager.renderInGameMenu)
			{TextInit.removeInGamePausePage();}
			this.checkGLError("World Loading");
			this.world.reloadScene();
			this.updateGameDisplay();
			this.world.cleanUpScene();
			this.saveAllGameOptions();
			this.isGamePause = false;
			this.quitTheScene();
			
			if(!this.isInMenuScene())
			{
				this.closeDisplay();
			}
		}
		this.saveAllGameOptions();
		
	}
	
	/**
	 * Create a display with use LWJGL. Load title, version and other parameters.
	 * @throws LWJGLException 
	 */
	public void createDisplay() throws IOException, LWJGLException
	{
		ContextAttribs attribs = new ContextAttribs(3, 3);
		attribs.withForwardCompatible(true);
		attribs.withProfileCore(true);
		
		try 
		{	
			this.setTitle(TITLE, this.engineVersion);
			this.setResizeble(false);
			
			Display.create(new PixelFormat().withDepthBits(24), attribs);
			this.inGameSettings.setMultisampleOption();
			
		} 
		catch (LWJGLException lwgjlE) 
		{
			this.crash.crashManager(10);
			this.saveAllGameOptions();
			LOGGER.fatal("Cannot create a display", (Throwable)lwgjlE);
			
			
			try 
			{
				Thread.sleep(1000L);
			} 
			catch (InterruptedException var3) 
			{
				var3.printStackTrace();
			}
			
			if(this.fullscreen)
			{
				this.updateDisplayMode();
			}
			
			Display.create();
		
		}
		
		GlHelper.glViewport(0, 0, this.getDisplayWidth(), this.getDisplayHeight());
		this.timer.initTime();
	} 
	
	/**
	 * Initialization the display mode.
	 */
	protected void initDisplayMode() throws LWJGLException
	{
		this.displayWidth = InGameSettings.displayWidthIn;
		this.displayHeight = InGameSettings.displayHeightIn;
		this.fullscreen = InGameSettings.useFullscreenIn;
		
		if(this.fullscreen)
		{
			this.setFullscreen(true);
			DisplayMode displayMode = Display.getDisplayMode();
			this.displayWidth = Math.max(1, displayMode.getWidth());
			this.displayHeight = Math.max(1, displayMode.getHeight());
		}
		else
		{
			Display.setResizable(false);
			Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
		}
	}
	
	private void updateDisplayMode() throws LWJGLException
	{
		Set<DisplayMode> set = Sets.<DisplayMode>newHashSet();
		Collections.addAll(set, Display.getAvailableDisplayModes());
		DisplayMode displayMode = Display.getDesktopDisplayMode();
		
		if(!set.contains(displayMode) && Util.getOSType() == Util.EnumOS.OSX)
		{
			label52:
				
			for(DisplayMode displayMode1 : MAC_DISPLAY_MODES)	
			{
				boolean flag = true;
				
				for (DisplayMode displayMode2 : set)
				{
					if(displayMode2.getBitsPerPixel() == 32 
							&& displayMode2.getWidth() == displayMode1.getWidth() && 
							displayMode2.getHeight() == displayMode1.getHeight())
					{
						flag = false;
						break;
					}
				}
				
				if(!flag)
				{
					@SuppressWarnings("rawtypes")
					Iterator iterator = set.iterator();
					DisplayMode displayMode3;
					
					while(true)
					{
						if(!iterator.hasNext())
						{
							continue label52;
						}
						
						displayMode3 = (DisplayMode)iterator.next();
						
						if (displayMode3.getBitsPerPixel() == 32 && 
								displayMode3.getWidth() == displayMode1.getWidth() / 2 && 
								displayMode3.getHeight() == displayMode1.getHeight() / 2)
						{
							break;
						}
					}
					
					displayMode = displayMode3;
				}
			}
		}
		
		Display.setDisplayMode(displayMode);
		this.displayWidth = displayMode.getWidth();
		this.displayHeight = displayMode.getHeight();
	}
	
	/**
	 * Controlls the display mode when user click on button in the game options menu.
	 * Set the fullscreen mode enable or disable.
	 * @throws LWJGLException
	 */
	public void initDisplayMode(int width, int height, boolean fullscreen) throws LWJGLException
	{
        if ((Display.getDisplayMode().getWidth() == width) && 
        		(Display.getDisplayMode().getHeight() == height) &&
        		(Display.isFullscreen() == fullscreen)) 
        		{
        			return;
        		}
        try
        {
            DisplayMode targetDisplayMode = null;
             
            if (fullscreen) 
            {
            	InGameSettings.enableVsync();
            	DisplayMode[] fullscreenModes = Display.getAvailableDisplayModes();
                int freq = 0;
                 
                for (int i = 0; i < fullscreenModes.length; i++) 
                {
                    DisplayMode current = fullscreenModes[i];
                     
                    if ((current.getWidth() == width) && (current.getHeight() == height)) 
                    {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) 
                        {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) 
                            {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }
 
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                            (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) 
                        {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } 
            else 
            {
            	InGameSettings.disableVsync();
                targetDisplayMode = new DisplayMode(width, height);
            }
            if (targetDisplayMode == null)
            {
            	Display.setDisplayMode(new DisplayMode(1600, 900));
            	LOGGER.info("Failed to find value mode: " + width + "x" + height + " Fullscreen = " + fullscreen);
            	LOGGER.info("Display set to default resolution: 1600 x 900.");
                return;
            }
            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);
            Display.setResizable(false);
             
        } 
        catch (LWJGLException e) 
        {
        	this.crash.crashManager(10);
            LOGGER.info("Unable to setup mode: " + width + "x" + height + " fullscreen = " + fullscreen + e);
        }
    }

	
	/**
	 * Standard update display method.
	 */
	public void updateDisplay()
	{
		Display.update();
		Display.sync(getCurrentFps());
	}
	
	/**
	 * Update when user in game menu. Render stuff in a game menu (Main page, options, 
	 * framebuffers, etc...)
	 */
	public void updateMainMenu() throws LWJGLException
	{
		this.setResizeble(InGameSettings.resizeDisplayIn);
		
		while(!Display.isCloseRequested() && !this.isInWorldScene)
		{
			this.mainMenu.renderScene();
			this.timer.setTime();
			this.updateFps();
			this.controllFullscreenMode();
			this.updateDisplay();
			
			if(!this.isRunning() && Display.isCloseRequested())
			{
				this.mainMenu.cleanUpScene();
			}
		}
	}
	
	public void updateLoadingSplashDisplay()
	{
		GuiRenderManager.renderLoadingSplash = true;
		boolean showLoadingScreen = true;
		TextMaster.renderLoadingSplash();

		if(GuiRenderManager.renderLoadingSplash)
		{
			
			this.guiRenderer.render(guiLoading.guisLoadingBackground);
			this.guiLoading.updateButtons();
			if(GuiLoadingSplash.progress25)
			{
				this.guiRenderer.render(guiLoading.guisLoadingState1);
			}
			if(GuiLoadingSplash.progress50)
			{
				this.guiRenderer.render(guiLoading.guisLoadingState2);
			}
			if(GuiLoadingSplash.progress75)
			{
				this.guiRenderer.render(guiLoading.guisLoadingState3);
				GuiRenderManager.renderGameOver = false;
			}
			this.guiRenderer.render(guiLoading.guisLoadingButtons);
		}
		
		while(!Display.isCloseRequested() && showLoadingScreen)
		{
			this.timer.setTime();
			this.updateFps();
			this.updateDisplay();
			if(GuiLoadingSplash.progress75 == false && GuiLoadingSplash.progress50 == false 
					&& GuiLoadingSplash.progress25 == false)
			{
				GuiRenderManager.renderLoadingSplash = false;
			}
			showLoadingScreen = false;
		}
	}
	
	/**
	 * Update a display each frame.
	 */
	public void updateGameDisplay()
	{	
		this.world.otherSetup();
		this.world.generateEntitiesOnTerrain();

		while(!Display.isCloseRequested() && !this.quitGameScene)
		{
	
			this.rendererGameLoop();
			this.timer.setTime();
			this.updateFps();
			this.updateDisplay();
			isGamePause = false;
		}
	
		if(!this.isRunning() && this.isInWorldScene())
		{
			this.world.cleanUpScene();
		}
		
	}
	
	/***
	 * Render entire a game engine. Render world, guis, entities.
	 */
	public void rendererGameLoop()
	{
		this.world.renderScene();
		
	}
	
	/**
	 * Distroy display and exit thread.
	 */
	public void closeDisplay()
	{
		LOGGER.info("Clean-Up all framebuffers.");
		this.cxLoader.cleanUp();
		this.textRenderer.cleanUp();
		TextMaster.cleanUp();
		LOGGER.info("Shutting down internal servers...");
		AudioMaster.shutdownAudioMaster();
		Display.destroy();
		this.exitController(0);
	}
	
	/**
	 * Render all post-proccessing effects.(Blur, contrast, etc)
	 * This method affects the overall loading of all post-effects. If you disable this method, 
	 * the boolean's in "PostEffectsInit" will not work. You can disable this method if
	 * you used "multisampleFramebuffer.resloveToScreen".
	 */
	public static void renderPostProcessingEffects()
	{
		if(InGameSettings.usePostEffectsIn)
		PostProcessing.doPostProcessing(WorldScene.outputFrameBuffer.getColourTexture(), 
				WorldScene.outputFrameBuffer.getColourTexture());
		else
		{
			//LOGGER.info("All post-proccessing effect has be disabled. And not load!");
		}
	}
	
	/**
	 * Set window icon. Work is actually comlpite!
	 */
	private void setDisplayIcon()
	{
		try 
		{
			Display.setIcon(new ByteBuffer[] 
			{
				loadIcon(getClass().getResource(ResourceLocation.LOGO_FOLDER + "logo_16x16.png")),
				loadIcon(getClass().getResource(ResourceLocation.LOGO_FOLDER + "logo_32x32.png")),
			});
		} 
		catch (IOException e) 
		{
			LOGGER.error("Can't load that display icon!");
			e.printStackTrace();
		}
	}
	
	private static ByteBuffer loadIcon(URL url) throws IOException 
	{
	    InputStream is = url.openStream();
	    try 
	    {
	    	PngDecoder decoder = new PngDecoder(is);
	        ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
	        decoder.decode(bb, decoder.getWidth() * 4, PngDecoder.Format.RGBA);
	        bb.flip();
	        return bb;
	    } 
	    finally 
	    {
	        is.close();
	    }
	}
	
	/**
	 * Update frame per seconds checker.
	 */
	public void updateFps()
	{	
		if (getCurrentTime() - this.timer.lastFPS > 1000) 
		{
			//Display.setTitle("FPS: " + this.timer.fps);
            this.timer.fps = 0;
            this.timer.lastFPS += 1000;
        }
       this.timer.fps++;
	}
	
	/**
	 * Save all game engine options with a "options.txt" file.
	 */
	public void saveAllGameOptions()
	{
		this.optionsFile = new File(ResourceLocation.CRAFTIX_FOLDER);
		this.inGameSettings.saveOptions();
	}

	
	/**
	 * Retrun the game current time in miliseconds.
	 */
	public static long getCurrentTime()
	{
		return Sys.getTime() * 1000L / Sys.getTimerResolution();
	}
	
	/**
	 * Check whether the display is changeble.
	 */
	private void setResizeble(boolean resizeble)
	{
		Display.setResizable(resizeble);
	}
	
	/**
	 * Check whether the display is visible.
	 */
	public void setVisible(boolean isVisible)
	{
		if(isVisible)
		{
			Display.isVisible();
		}
		else 
		{
			LOGGER.warn("Display not visible. Set the 'true' on setVisible().");
		}
	}
	
	/**
	 * Set the title and get verison for the application.
	 */
	private void setTitle(String title, String version)
	{
		Display.setTitle(title + version);
	}
	
	/**
	 * Initialise the the config for game engine.
	 */
	public void gameCongifurationInit() 
	{
		this.cxDataDir = this.gameConfiguration.folderInfo.cxDataDir;
		Locale.setDefault(Locale.ROOT);
		
	}
	
	/**
	 * Disable the info desc PngDecoder Util message.
	 */
	private void disablePngDecoderInfo()
	{
		Log.setVerbose(false);
	}
	
	/**
	 * Load the default language to the buffer.
	 */
	public void loadDefaultLanguage()
	{
		MainMenuScene mainScene = new MainMenuScene();
		mainScene.useRu = true;
	}
	
	/**
	 * If in the process nothing not is happening the thread falls asleep on time.
	 */
	private void startTimerHackThread()
    {
        Thread thread = new Thread("Timer hack thread!")
        {
            public void run()
            {
                while (Craftix.this.isRunning())
                {
                    try
                    {
                        Thread.sleep(2147483647L);
                    }
                    catch (InterruptedException var2)
                    {
                        var2.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
	
	/**
	 * Check for an OpenGL error. If there is an error in the console writes an error ID.
	 */
	private void checkGLError(String message)
	{
		int i = GlHelper.glGetError();
		
		if(i != 0)
		{
			String s = GLU.gluErrorString(i);
			LOGGER.error("============= GL ERROR =============");
			
			LOGGER.error("@ {}", (Object) message);
			LOGGER.error("{}: {}", Integer.valueOf(s), i);
			
		}
	}
	
	/**
	 * Check if Java Virtual Machime instlled on 64bit os system. And load game better.
	 * @return
	 */
	private boolean isJvm64bit()
    {
        String[] astring = new String[] {"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

        for (String s : astring)
        {
            String s1 = System.getProperty(s);

            if (s1 != null && s1.contains("64"))
            {
                return true;
            }
        }

        return false;
    }
	
	/**
    * Resets the player keystate, disables the ingame focus, and ungrabs the mouse cursor.
    */
	public void setIngameNotInFocus()
	{
		if(this.inGameHasFocus)
		{
			this.inGameHasFocus = false;
			this.mouseHelper.ungrabMouseCursor();
		}
	}
	
	/**
	 * Check whether the display is fullscreen or not. If display not fullscreen he be loaded
	 * in the windowed mode.
	 */
	protected void setDisplayAndFullscreenMode(boolean fullscreen, boolean displayMode) 
			throws LWJGLException
	{
		if(displayMode && !fullscreen)
		{
			this.setVSync(false);
			Display.setDisplayMode(new DisplayMode(getDisplayWidth(), getDisplayHeight()));
		}
		if(fullscreen && !displayMode)
		{
			this.setVSync(true);
			Display.setFullscreen(fullscreen);
		}
		if(!fullscreen && !displayMode)
		{
			this.setVSync(false);
			LOGGER.error("I cannot select the type of display to load.");
			
		}
	}
	
	/***
	 * Set the verical Sync for fullscreen mode.
	 */
	public void setVSync(boolean isSync)
	{
		this.vSync = isSync;
		Display.setVSyncEnabled(isSync);
	}
	
	/**
     * Will set the focus to ingame if the Craftix window is the active with focus. Also clears any GUI screen
     * currently displayed.
     */
	public void setIngameFocus()
	{
		if(Display.isActive())
		{
			if(!this.inGameHasFocus)
			{
				this.inGameHasFocus = true;
				this.mouseHelper.grabMouseCursor();
			}
		}
	}
	
	/**
	 * Quit the actilly game scene when now running. Works how always.
	 * @throws LWJGLException
	 */
	public void quitTheScene() throws LWJGLException
	{
		if(this.backToMenu)
		{
			this.returnToMainMenu();
		}
		
	}
	
	public void returnToMainMenu() throws LWJGLException
	{
		this.sceneReloader();
	}
	
	/**
	 * Set the game window in fullscreen mode.
	 * @param isFullscreen
	 */
	private void setFullscreen(boolean isFullscreen)
	{
		try 
		{
			Display.setFullscreen(isFullscreen);
		} 
		catch (LWJGLException e) 
		{
			LOGGER.error("Cannot set the display in fullscreen mode!");
			e.printStackTrace();
		}
	}
	
	protected void checkWindowResize()
	{
		if(!this.fullscreen && Display.wasResized())
		{
			int i = this.displayWidth;
			int j = this.displayHeight;
			this.displayWidth = Display.getWidth();
			this.displayHeight = Display.getHeight();
			
			 if (this.displayWidth != i || this.displayHeight != j)
	         {
	             if (this.displayWidth <= 0)
	             {
	                 this.displayWidth = 1;
	             }

	             if (this.displayHeight <= 0)
	             {
	                 this.displayHeight = 1;
	             }

	             this.resize(this.displayWidth, this.displayHeight);
	         }
		}
	}
	
	 /**
     * Called to resize the screen.
     */
	public void resize(int width, int height)
	{
		this.displayWidth = Math.max(1, width);
		this.displayHeight = Math.max(1, height);
	}
	
	
	/**
	 * This method not be complited.
	 */
	@StillWorking
	private void controllFullscreenMode()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_F12))
		{
			InGameSettings.useFullscreenIn = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F11))
		{
			InGameSettings.useFullscreenIn = false;
		}
		
	}
	
	/**
	 * Controls on the proper use of statuses of the output of the engine.
	 * @param status - status of the error. For example 0 its a normal exit.
	 */
	public void exitController(int status)
	{
		if(status == -1)
		{
			System.exit(-1);
		}
		else if(status == 0)
		{
			System.exit(0);
		}
		else if(status == 1)
		{
			System.exit(1);
		}
		else if(status >= 2 || status <= -2)
		{
			LOGGER.error("Exit status " + status + " not currect.");
			LOGGER.error("Only 1, 0, -1 statuses can be used.");
		}
	}
	
	/**
	 * Closes the program when the user clicks on the cross on the display.
	 */
	public void exitOnCross(int status)
	{
		this.ON_EXIT_CLOSE = true;
		if(this.ON_EXIT_CLOSE)
		{
			AudioMaster.shutdownAudioMaster();
			this.exitController(status);
		}
	}
	
	/**
	 * Exits the game engine after clean-up all buffers and shaders.
	 */
	private void shutdown(int status)
	{
		this.isRunning = false;
		this.exitController(status);
	}
	
	/**
     * Get the display with for future used.
     * @return - the display width from settings class.
     */
	public int getDisplayWidth() 
	{
		return InGameSettings.displayWidthIn;
	}

	 /**
     * Get the height with for future used.
     * @return - the display height from settings class.
     */
	public int getDisplayHeight() 
	{
		return InGameSettings.displayHeightIn;
	}
	
	/**
	 * Puts a fps limit in some game scenes such as the menu because the menu 
	 * is 60 as 120.
	 * @return limitFramerate - is current fps in the limit.
	 */
	public int getCurrentFps() 
	{
		int currentFps = 0;
		if(this.isInWorldScene())
		{
			currentFps = InGameSettings.maxFpsIn;
		}
		if(this.isInMenuScene())
		{
			currentFps = InGameSettings.limitFpsMenuIn;
		}
		
		return currentFps;
		
	}
	
	/**
	 * Normalization for correct use of the mouse in the game. Special use for gui's texture. Or for
	 * example buttons.
	 */
	public static Vector2f getNormalizedMouseCoords()
	{
		float normaizedX = -1.0f + 2.0f * (float) Mouse.getX() / (float) Display.getWidth();
		float normaizedY = 1.0f - 2.0f * (float) Mouse.getY() / (float) Display.getHeight();
			return new Vector2f(normaizedX, normaizedY);
	}
	
	private void runTick() throws IOException
	{
		if(this.getWorld().guiScreen != null)
		{
			try
			{
				this.getWorld().guiScreen.handleInput();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		if(this.getWorld().guiScreen == null)
		{
			this.runTickMouse();
		}
	}
	
	private void runTickMouse() throws IOException
	{
		while(Mouse.next())
		{
			int i = Mouse.getEventButton();
			KeyBinding.setKeyBindState(i - 100, Mouse.getEventButtonState());
			
			if(Mouse.getEventButtonState())
			{
				KeyBinding.onTick(i - 100);
			}
			
			long j = getCurrentTime() - this.systemTime;
			
			if(j <= 200L)
			{
				int k = Mouse.getEventDWheel();
				
				if(k != 0)
				{
					
				}
				
				if(this.getWorld().guiScreen == null)
				{
					if(!this.inGameHasFocus && Mouse.getEventButtonState())
					{
						this.setIngameFocus();
					}
				}
				else if(this.getWorld().guiScreen != null)
				{
					this.getWorld().guiScreen.handleMouseInput();
				}
				
			}
			
			
		}

	}
	
	/**
	 * Get the texture manager and return it.
	 */
	public TextureManager getTextureManager()
	{
		this.textureManager = new TextureManager();
		return textureManager;
	}
	
	/**
	 * Get the craftix engine version when it running.
	 */
	public String getVersion()
	{
		return VERSION;
	}
	
	/**
	 * Allows you to get in the 'InGameSettings' for all other classes.
	 */
	public InGameSettings getInGameSettings()
	{
		return this.inGameSettings;
	}
	
	public Language getLang()
	{
		return this.language;
	}
	
	public DataFixer getDataFixer()
	{
		return this.dataFixer;
	}
	
	public boolean isJava64bit()
    {
        return this.jvm64bit;
    }
	
	/**
	 * Get the WorldScene for other classes.
	 */
	public WorldScene getWorld()
	{
		return this.world;
	}

	public boolean isInMenuScene() 
	{
		return isInMenuScene;
	}

	public void setInMenuScene(boolean isInMenuScene) 
	{
		this.isInMenuScene = isInMenuScene;
	}

	public boolean isInWorldScene() 
	{
		return isInWorldScene;
	}

	public void setInWorldScene(boolean isInWorldScene) 
	{
		this.isInWorldScene = isInWorldScene;
	}
	
	/**
	 * Get the 'TextInit' class for future used in scenes classes.
	 * @return - the main textInit instance.
	 */
	public TextInit getTextInit()
	{
		return this.textInit;
	}
	
	/**
	 * Get the 'TextRenderer' to display text in the engine.
	 * @return - the main textRender instance.
	 */
	public FontRenderer getTextRenderer()
	{
		return this.textRenderer;
	}

	public boolean isRunning() 
	{
		return isRunning;
	}
	
	public Session getSession()
	{
		return this.engineSession;
	}
	
	public GameConfiguration getGameConfig()
	{
		return this.gameConfig;
	}

	public File getFileAssets() 
	{
		return fileAssets;
	}

	public File getFileResourcepacks() 
	{
		return fileResourcepacks;
	}

	/**
	 * Get the engine version.
	 */
	public String getEngineVersion() 
	{
		return engineVersion;
	}

	/**
	 * Get the type of the version.
	 */
	public String getEngineVersionType() 
	{
		return engineVersionType;
	}

	/**
	 * Get just the gemo version of the engine.
	 * @return
	 */
	public boolean isDemoVersion() 
	{
		return isDemoVersion;
	}
	
}

package com.kenny.craftix.client.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import com.google.common.base.Splitter;
import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiYesNo;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.settings.console.ConsoleErrorConfig;
import com.kenny.craftix.client.settings.nbt.NBTTagCompound;
import com.kenny.craftix.init.TextInit;
import com.kenny.craftix.utils.data.FixTypes;

@SuppressWarnings("deprecation")
public class InGameSettings
{
	public static final Splitter COLON_SPLITTER = Splitter.on(':');
	private static final Logger LOGGER = LogManager.getLogger("Settings");
	private static final String SETTINGS_CRAFTIX_VERSION = "0.0.16";
	private static TextInit lang = new TextInit();
	public static boolean hasError;
	/**This is width of the display.*/
	public static int displayWidthIn;
	/**This is height of the display*/
	public static int displayHeightIn;
	public static boolean resizeDisplayIn;
	/**Responsible for large size Gui.*/
	public static boolean guiScaleLargeIn = false;
	/**Responsible for medium size Gui.*/
	public static boolean guiScaleMediumIn = false;
	/**Responsible for small size Gui.*/
	public static boolean guiScaleSmallIn = false;
	public static int guiScale = 3;
	/**Its a current fps on the display when game has running.*/
	public static int maxFpsIn;
	public static int limitFpsMenuIn;
	/**Used to adjust the fullscreen mode in the game.*/
	public static boolean useFullscreenIn = true;
	public static boolean useVSync;
	/**Used to adjust the post-processing effect in the game.*/
	public static boolean usePostEffectsIn;
	/**If framebuffer is enable, than you can useds all post-proccessing effects.*/
	public static boolean useFboIn = false;
	public static boolean useShadowGuiIn;
	public static boolean usePoligonModeIn;
	public static boolean useAudioIn;
	public static boolean useMultisampleIn;
	/**Show the skybox on the game with rotation around player.*/
	public static boolean renderSkyBoxIn;
	/**Rendering the water tile in game engine.*/
	public static boolean renderWaterIn;
    private File optionsFile;
    public static String language;
    public ConsoleErrorConfig consoleConfig = new ConsoleErrorConfig();
    private Craftix cx;
	
	/**
	 * Sets the default engine settings on first launch. If user load game already then,
	 * be load the options "txt" file.
	 */
    public InGameSettings(Craftix cxIn, File optionsFileIn)
    {
    	this.cx = cxIn;
    	this.optionsFile = new File(optionsFileIn, "options.txt");
    	this.checkIfOptionsFileExist();
    	this.loadDefaultEngineOptions();
    	this.consoleConfig.errorManager(false);
    	this.loadOptions();
    	this.eventButtonsYesNo();
    	this.saveOptions();
    }
    
    public InGameSettings()
    {
    	
    }
    
	public void loadDefaultEngineOptions()
	{
		this.setDisplayMode();
		this.enableFullscreen();
		enableVsync();
		this.enableAudio();
		this.disableFbo();
		this.disableMultisample();
		this.enablePostEffects();
		this.enableRenderSkyBox();
		this.enableRenderWater();
	}
	
	/**
	 * Set resolution of the display on first load if display not in fullscreen.
	 */
	public void setDisplayMode()
	{
		this.setDisplayWidth();
		this.setDisplayHeight();
		this.setLimitFps();
		this.setCurrentFps();
		this.enableResizeDisplay();
	}
	
	private void setDisplayWidth()
	{
		displayWidthIn = 960;
	}
	
	private void setDisplayHeight()
	{
		displayHeightIn = 540;
	}
	
	private void setCurrentFps()
	{
		maxFpsIn = 120;
	}
	
	private void setLimitFps()
	{
		limitFpsMenuIn = 60;
	}
	
	/**
	 * Set the multisample option for a entity's egdes.
	 */
	public void setMultisampleOption()
	{
		if(useMultisampleIn)
		{
			GlHelper.enableMultisample();
		} else {
			GlHelper.disableMultisample();
		}
	}
	
	/**
	 * Enable the multisaple anti-analisizig.
	 */
	public void enableMultisample()
	{
		useMultisampleIn = true;
	}
	
	/**
	 * Disable the multisaple anti-analisizig.
	 */
	public void disableMultisample()
	{
		useMultisampleIn = false;
	}
	
	/**
	 * Enabled the fullscreen mode.
	 */
	public void enableFullscreen()
	{
		useFullscreenIn = true;
	}
	
	/**
	 * Disable the fullscreen mode.
	 */
	public void disableFullscreen()
	{
		useFullscreenIn = false;
	}
	
	public void enableResizeDisplay()
	{
		resizeDisplayIn = true;
	}
	
	public void disableResizeDisplay()
	{
		resizeDisplayIn = false;
	}
	
	/**
	 * Enabled the vertical synczronization for a fullscreen mode.
	 */
	public static void enableVsync()
	{
		if(useFullscreenIn)
		{
			useVSync = true;
			Display.setVSyncEnabled(true);
		}
	}
	
	/**
	 * Disable the vertical synczronization for a windowed mode.
	 */
	public static void disableVsync()
	{
		if(!useFullscreenIn)
		{
			useVSync = false;
			Display.setVSyncEnabled(false);
		}
	}
	
	/**
	 * Enabled the framebuffer object.
	 */
	public void enableFbo()
	{
		useFboIn = true;
	}
	
	/**
	 * Disable the framebuffer object.
	 */
	public void disableFbo()
	{
		useFboIn = false;
	}
	
	/**
	 * Enabled the post-processing effects, like a contrast, blur or bloom.
	 */
	public void enablePostEffects()
	{
		usePostEffectsIn = true;
	}
	
	/**
	 * Disable the post-processing effects.
	 */
	public void disablePostEffects()
	{
		usePostEffectsIn = false;
	}
	
	/**
	 * Enabled render the skybox on the game.
	 */
	public void enableRenderSkyBox()
	{
		renderSkyBoxIn = true;
	}
	
	/**
	 * Disable render the skybox on the game.
	 */
	public void disableRenderSkyBox()
	{
		renderSkyBoxIn = false;
	}
	
	/**
	 * Enabled render the water tile.
	 */
	public void enableRenderWater()
	{
		renderWaterIn = true;
	}
	
	/**
	 * Disable render the water tile.
	 */
	public void disableRenderWater()
	{
		renderWaterIn = false;
	}
	
	public static void enableTriangleMode()
	{
		if(usePoligonModeIn)
		{
			GlHelper.enablePoligonMode();
		}
	}
	
	public static void disableTriangleMode()
	{
		if(!usePoligonModeIn)
		{
			GlHelper.disablePoligonMode();
		}
	}
	
	public void enableShadowGui()
	{
		useShadowGuiIn = true;
	}
	
	public void disableShadowGui()
	{
		useShadowGuiIn = false;
	}
	
	public void enableAudio()
	{
		useAudioIn = true;
	}
	
	public void disableAudio()
	{
		useAudioIn = false;
	}
	
	public static void setLanguage()
	{
		if(lang.isRu)
		{
			language = "ru_ru";
		}
		//if(lang.isEn)
		//{
		//	language = "us_en";
		//}
	}
	
	public static void setGuiScaleLarge()
	{guiScaleLargeIn = true; guiScaleMediumIn = false; guiScaleSmallIn = false;}
	
	public static void setGuiScaleMedium()
	{guiScaleMediumIn = true; guiScaleSmallIn = false; guiScaleLargeIn = false;}
	
	public static void setGuiScaleSmall()
	{guiScaleSmallIn = true; guiScaleMediumIn = false; guiScaleLargeIn = false;}
	
	public String craftixAutors()
	{
		String mainAutor;
		String othersAutors;
		String autorText;
		mainAutor = "Kenny";
		othersAutors = "Bogdan, ThinMatrix, TheChernoProject";
		autorText = mainAutor + ", " + othersAutors;
		
			return autorText;
	}
	
	public void eventButtonsYesNo()
	{
		GuiYesNo.checkYesFullscreen();
		GuiYesNo.checkYesAudio();
		GuiYesNo.checkYesFramebuffer();
		GuiYesNo.checkYesRenderSkybox();
		GuiYesNo.checkYesRenderWater();
	}
	
	/**
	 * Checks if the options file has exist.
	 */
	public void checkIfOptionsFileExist()
	{
		try
		{
			this.optionsFile.setWritable(true);
			
			if(!this.optionsFile.exists())
			{
				LOGGER.info("Options file not extis.");
				
				try
				{
					this.loadDefaultEngineOptions();
					this.optionsFile.createNewFile();
					this.saveOptions();
					this.loadOptions();
				}
				catch(Exception e)
				{
					hasError = true;
					LOGGER.error("Cant't load and create the 'options.txt' file! Make sure");
					LOGGER.error("you have a folder ' .craftix ' in 'appdata/roaming'", (Throwable)e);
					e.printStackTrace();
				}
				
				return;
			}
			
		}
		catch(Exception e)
		{
		}
	}
	
	public void loadOptions()
	{
		try
		{	
			List<String> list = IOUtils.readLines(new FileInputStream(this.optionsFile));
			NBTTagCompound nbt = new NBTTagCompound();
			
			for(String s: list)
			{
				try
				{
					Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
					nbt.setString(iterator.next(), iterator.next());
				}
				catch(Exception e)
				{
					LOGGER.warn("Skipping bad option: {}", (Object)s);
				}
			}
			
			nbt = this.dataFix(nbt);
			
			for(String s1 : nbt.getKeySet())
			{
				String s2 = nbt.getString(s1);
				
				try
				{
					
					if("guiScale".equals(s1))
					{
						guiScale = Integer.parseInt(s2);
						if(guiScale == 0)
						{
							setGuiScaleLarge();
						}
						if(guiScale == 1)
						{
							setGuiScaleSmall();
						}
						if(guiScale == 2)
						{
							setGuiScaleMedium();
						}
						if(guiScale == 3)
						{
							setGuiScaleLarge();
						}
					}
					
					if("language".equals(s1))
					{
						language = s2;
					}
					
					if("fullscreenMode".equals(s1))
					{
						useFullscreenIn = "true".equals(s2);
					}
					
					if("resizebleMode".equals(s1))
					{
						resizeDisplayIn = "true".equals(s2);
					}
					
					if("framebuffer".equals(s1))
					{
						useFboIn = "true".equals(s2);
					}
					
					if("multisample".equals(s1))
					{
						useMultisampleIn = "true".equals(s2);
					}
					
					if("renderSkybox".equals(s1))
					{
						renderSkyBoxIn = "true".equals(s2);
					}
					
					if("renderWater".equals(s1))
					{
						renderWaterIn = "true".equals(s2);
					}
					
					if("maxFps".equals(s1))
					{
						maxFpsIn = Integer.parseInt(s2);
					}
					
					if("displayWidth".equals(s1))
					{
						displayWidthIn = Integer.parseInt(s2);
						if(displayWidthIn < 960)
						{
							displayWidthIn = 960;
							LOGGER.warn("Display width not be smaller that " + displayWidthIn);
							LOGGER.warn("Width set by: " + displayWidthIn);
						}
					}
					
					if("displayHeight".equals(s1))
					{
						displayHeightIn = Integer.parseInt(s2);
						if(displayHeightIn < 540)
						{
							displayHeightIn = 540;
							LOGGER.warn("Display height not be smaller that " + displayHeightIn);
							LOGGER.warn("Height set by: " + displayHeightIn);
						}
					}
					
					if("audio".equals(s1))
					{
						useAudioIn = "true".equals(s2);
					}
				}
				catch (Exception e)
				{
					LOGGER.warn("Skipping bad option: {}:{}", s1, s2);
				}
			}
			
		}
		catch(Exception e)
		{
			LOGGER.error("Failed to load options", (Throwable)e);
		}
	}
	

	/**
	 * Saves the options to options file.
	 */
	public void saveOptions()
	{
		PrintWriter printwriter = null;
		
		try
		{
			printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8));
			printwriter.println("craftixVersion:" + SETTINGS_CRAFTIX_VERSION);
			printwriter.println("autors:" + this.craftixAutors());
			printwriter.println("guiScale:" + guiScale);
			printwriter.println("language:" + language);
			printwriter.println("fullscreenMode:" + useFullscreenIn);
			printwriter.println("resizebleMode:" + resizeDisplayIn);
			printwriter.println("vSync:" + useVSync);
			printwriter.println("framebuffer:" + useFboIn);
			printwriter.println("multisample:" + useMultisampleIn);
			printwriter.println("renderSkybox:" + renderSkyBoxIn);
			printwriter.println("renderWater:" + renderWaterIn);
			printwriter.println("maxFps:" + maxFpsIn);
			printwriter.println("displayWidth:" + displayWidthIn);
			printwriter.println("displayHeight:" + displayHeightIn);
			printwriter.println("audio:" + useAudioIn);
		}
		catch(Exception e)
		{
			LOGGER.error("Failed to save options", (Throwable)e);
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly((Writer)printwriter);
		}
	}
	
	/**
     * Sets a key binding and then saves all settings.
     */
    public void setOptionKeyBinding(KeyBinding key, int keyCode)
    {
        key.setKeyCode(keyCode);
        this.saveOptions();
    }
	
	 private NBTTagCompound dataFix(NBTTagCompound p_189988_1_)
	    {
	        int i = 0;

	        try
	        {
	            i = Integer.parseInt(p_189988_1_.getString("craftixVersion"));
	        }
	        catch (RuntimeException var4)
	        {
	            ;
	        }
	        	return this.cx.getDataFixer().process(FixTypes.OPTIONS, p_189988_1_, i);
	    }
}

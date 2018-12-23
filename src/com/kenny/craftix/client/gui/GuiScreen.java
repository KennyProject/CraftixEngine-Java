package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import com.google.common.collect.Lists;
import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.Buttons;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.settings.InGameSettings;

public class GuiScreen extends GuiAdder
{
	/**Its a location folder of all guis in the engine.*/
	public static final String GUI_LOCATION = "guis/";
	public static final String BUTTON_QUAD = GUI_LOCATION + "menu/button_base";
	/**Loaction of the button rectange texture.*/
	public static final String BUTTON_RECT = GUI_LOCATION + "menu/button_base";
	private static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	/**Scale multibuttons on x axis.*/
	public float buttonScaleM_x = 0.035f;
	/**Scale multibuttons on y axis.*/
	public float buttonScaleM_y = 0.060f;
	/**Scale buttons on x axis.*/
	public final float buttonScale_x = 0.000f;
	/**Scale buttons on y axis.*/
	public final float buttonScale_y = 0.000f;
	/**Update button scale every tick.*/
	public float updateButtonScale_x;
	public float updateButtonScale_y;
	/** A list of all the buttons in this container. */
	public Buttons b = new Buttons();
	/**Gui screen object width on pixels.*/
	public int screenWidth;
	/**Gui screen object height on pixels.*/
	public int screenHeight;
	/**Its main screen background texture.*/
	public GuiQuad background;
	protected Loader loader = new Loader();
	/**Link on the browse addrees.*/
	private URI clickedLinkURI;
	/**The button that was just pressed.*/
    protected GuiButton selectedButton;
	boolean flag = false;
	/**Tracks the number of fingers currently on the screen. Prevents subsequent fingers registering as clicks. */
    private int touchValue;
    private int eventButton;
    private long lastMouseEvent;
    public GuiRenderer guiRenderer;
	/**A list of all the buttons in this container.*/
    public List<GuiButton> buttonList = Lists.<GuiButton>newArrayList();
	
	protected <T extends GuiButton> T addButton(T buttonIn)
	{
		this.buttonList.add(buttonIn);
		return buttonIn;
	}
	
	/**
	 * Draw the all components in screen.
	 */
	public void drawScreen()
	{
		for (int i = 0; i < this.buttonList.size(); ++i) 
		{
			((GuiButton)this.buttonList.get(i)).renderButton(true, false);
		}
	}
	
	/**
	 * Render everything component on the screen. (Called in update method.)
	 */
	public void renderScreen()
	{
		this.guiRenderer = new GuiRenderer(this.loader);
	}
	
	/**
    * Called by the controls from the buttonList when activated.
    */
    protected void actionPerformed(GuiButton button) throws IOException
    {
    }
    
    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
    }
	
    /**
     * Handle the mouse and keyboard input
     */
    public void handleInput() throws IOException
    {
    	if(Mouse.isCreated())
    	{
    		while(Mouse.next())
    		{
    			this.handleMouseInput();
    		}
    	}
    	
    	if(Keyboard.isCreated())
    	{
    		while(Keyboard.next())
    		{
    			this.handleKeyboardInput();
    		}
    	}
    }
    
    /**
     * Called when mouse is been clicked.
     */
	protected void mouseClicked(int mouseButton, GuiButton button) throws IOException
	{
		if(mouseButton == 0)
		{
			for(int i = 0; i < this.buttonList.size(); ++i)
			{
				button = this.buttonList.get(i);
				
				this.selectedButton = button;
				System.out.println("grgr");
				this.actionPerformed(button);	
				
			}
		}
	}
	
	/**
     * Called when a mouse button is released.
     */
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		if(this.selectedButton != null && state == 0)
		{
			this.selectedButton.mouseReleased(mouseX, mouseY, state);
			this.selectedButton = null;
		}
	}
	
	public void handleMouseInput() throws IOException
	{
		int i = Mouse.getEventX() * this.cx.displayWidth;
		int j = Mouse.getEventY() * this.cx.displayHeight - 1;
		int k = Mouse.getEventButton();
		
		if(Mouse.getEventButtonState())
		{
			if(this.touchValue++ > 0)
			{
				return;
			}
			
			this.eventButton = k;
			this.lastMouseEvent = Craftix.getCurrentTime();
			///this.mouseClicked(i, j, this.eventButton);
		}
		else if(k != -1)
		{
			if(--this.touchValue > 0)
			{
				return;
			}
			
			this.eventButton = -1;
			this.mouseReleased(i, j, k);
		}
		else if (this.eventButton != -1 && this.lastMouseEvent > 0L)
		{
			long l = Craftix.getCurrentTime() - this.lastMouseEvent;
			this.mouseClickMove(i, j, this.eventButton, l);		
		}
	}
	
	public void handleKeyboardInput() throws IOException
	{
		char c0 = Keyboard.getEventCharacter();
		
		if(Keyboard.getEventKey() == 0 && c0 >= ' ' || Keyboard.getEventKeyState())
		{
			this.keyTyped(c0, Keyboard.getEventKey());
		}
		
	}
	
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if(keyCode == 1)
		{
			this.cx.setIngameFocus();
		}
	}

	@Override
	public void onUpdate() 
	{
		super.onUpdate();
		while(Keyboard.next())
		{
			if(isF12KeyDown())
			{
				if(!flag)
				{
					InGameSettings.useFullscreenIn = true;
					flag = true;
				}
				else
				{
					InGameSettings.useFullscreenIn = false;
					flag = false;
				}
				
			}
		}
	}
	
	
	
	/**
	 * Draw the main screen background.
	 */
	public void drawBackground(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.background = drawGui(GUI_LOCATION + "menu/in_background", this.cx.cxLoader, 
				0.0f, -0.95f, 1.95f, 1.95f);
	}
	
	/**
	 * Render the main background screen. (Call on update method.)
	 */
	public void renderBackground(Craftix craftxIn)
	{
		this.cx = craftxIn;
		GuiRenderManager.renderGuiScreenBack = true;
		if(this.cx.isInMenuScene() && !this.cx.isInWorldScene())
		{
			if(GuiRenderManager.renderSingleplayerMenu || GuiRenderManager.renderCreditsMenu ||
					GuiRenderManager.renderOptionsMenu || GuiRenderManager.renderGraphicMenu || 
						GuiRenderManager.renderAudioMenu || GuiRenderManager.renderLanguageMenu)
			{
				if(GuiRenderManager.renderGuiScreenBack)
				{
					this.cx.guiRenderer.render(this.background);
				}
			}
		}
		else if(!this.cx.isInMenuScene() && this.cx.isInWorldScene())
		{
			if(GuiRenderManager.renderOptionsInGame)
			{
				if(GuiRenderManager.renderGuiScreenBack)
				{
					//this.cx.guiRenderer.render(this.background);
				}
			}
		}
	}
	
	/**
	 * This is custom X and Y values for a for multi-size buttons.
	 */
	@SuppressWarnings("unused")
	public void updateGuiScale()
	{
		float x = 0f; float y = 0f;

		if(InGameSettings.guiScaleSmallIn)
		{
			x = 0f; 
			y = 0f;
		}
		if(InGameSettings.guiScaleMediumIn)
		{
			x = 0.010f; 
			y = 0.020f;
		}
		if(InGameSettings.guiScaleLargeIn)
		{
			x = 0f; 
			y = 0f;
		}
		
		//this.buttonScaleM_x =- x;
		//this.buttonScaleM_y =- y;
	}
	
	public void isClickedOnURI(boolean result, int id)
	{
		try 
		{
			this.clickedLinkURI = new URI("https://vk.com/club175351714");
		} 
		catch (URISyntaxException e) 
		{
			
			e.printStackTrace();
		}
		if(id == 21022003)
		{
			if(result)
			{
				this.openWebLink(this.clickedLinkURI);
			}
		}
		this.clickedLinkURI = null;
	}
	
	/**
	 * Opens the page to which the link was specified.
	 */
	private void openWebLink(URI urlAddress)
	{
		try
		{
			Class<?> clazz = Class.forName("java.awt.Desktop");
			Object object = clazz.getMethod("getDesktop").invoke((Object) null);
			clazz.getMethod("browse", URI.class).invoke(object, urlAddress);
		}
		catch(Throwable t1)
		{
			Throwable t = t1.getCause();
			LOGGER.error("Can't opend this link: {}", (Object)(t == null ? "<UNKNOWN>" : t.getMessage()));
		}
	}
	
	/**
	 * Use for a change a fullscreen mode, in engine.
	 */
	public static boolean isF12KeyDown()
	{
		return Keyboard.isKeyDown(88);
	}
	
	/**
	 * Return true if right shift of left shift keys is down.
	 */
	public static boolean isShiftKeyDown()
	{
		return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
	}
	
	/**
	 * Return true if right alt or left alt keys is down.
	 */
	public static boolean isAltKeyDown()
	{
		return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
	}
	
	/**
	 * Returns true if the strl button on the Mac is pressed otherwise the 
	 * button is pressed on the Windows.
	 */
	public static boolean isCtrlKeyDown()
	{
		if(Craftix.IS_RUNNING_ON_MAC)
		{
			return Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220);
		}
		else
		{
			return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
		}
	}
	
	public static boolean isKeyComboCtrlX(int keyID)
    {
        return keyID == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlV(int keyID)
    {
        return keyID == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlC(int keyID)
    {
        return keyID == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlA(int keyID)
    {
        return keyID == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

	public Loader getLoader() 
	{
		return loader;
	}
	
	public GuiButton getButton()
	{
		return this.selectedButton;
	}


}

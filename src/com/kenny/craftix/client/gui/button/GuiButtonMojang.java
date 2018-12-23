package com.kenny.craftix.client.gui.button;

import org.lwjgl.opengl.GL11;

import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.resources.StillWorking;

public class GuiButtonMojang 
{
	protected static final int BUTTON_TEXTURES = new Loader().loadTexture("textures/guis/menu/button_base");
	 /** Button width in pixels */
    public int width;
    /** Button height in pixels */
    public int height;
    /** The x position of this control. */
    public int x;
    /** The y position of this control. */
    public int y;
    /** The string displayed on this control. */
    public String displayString;
    public int id;
    /** True if this control is enabled, false to disable. */
    public boolean enabled;
    /** Hides the button completely if false. */
    public boolean visible;
    protected boolean hovered;
    public int packedFGColour;
    
    public GuiButtonMojang(int buttonId, int x, int y, String buttonText)
    {
    	this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiButtonMojang(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
    	this.width = 200;
    	this.height = 20;
    	this.enabled = true;
    	this.visible = true;
    	this.id = buttonId;
    	this.x = x;
    	this.y = y;
    	this.width = widthIn;
    	this.height = heightIn;
    	this.displayString = buttonText;
    }
    
    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver)
    {
    	int i = 1;
    	
    	if(!this.enabled)
    	{
    		i = 0;
    	}
    	else if (mouseOver)
    	{
    		i = 2;
    	}
    	
    	return i;
    }
    
    @SuppressWarnings({ "unused", "static-access" })
	public void drawButton(CraftixOld craftix, int mouseX, int mouseY, float partialTicks)
    {
    	if(this.visible)
    	{
    		/**Font Renderer*/
    		craftix.getTextureManager().bindTexture2d(BUTTON_TEXTURES);
    		GlHelper.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    		int i = this.getHoverState(this.hovered);
    		GlHelper.glEnable(GL11.GL_BLEND);
    		GlHelper.glBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    		/**DrawTexturedModalRect*/
    		this.mouseDragged(craftix, mouseX, mouseY);
    		int j = 14737632;
    		
    		if(packedFGColour != 0)
    		{
    			j = packedFGColour;
    		}
    		else
    		if(!enabled)
    		{
    			j = 10526880;
    		}
    		else if(this.hovered)
    		{
    			j = 16777120;
    		}
    		
    		/**DrawCenteredString*/
    		
    	}
    }
    
    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(CraftixOld craftix, int mouseX, int mouseY)
    {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
    }
    
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(CraftixOld craftix, int mouseX, int mouseY)
    {
    	return this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }
    
    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver()
    {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
    }
    
    @StillWorking
    public void playPressSound()
    { 
    }
    
    public int getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
    
}

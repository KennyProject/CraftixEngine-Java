package com.kenny.craftix.client.gui.button;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.font.GuiEngineText;
import com.kenny.craftix.client.font.Texts;
import com.kenny.craftix.client.gui.GuiQuad;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.gui.GuiScreen;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.init.TextInit;
import com.kenny.craftix.utils.math.Maths;

public abstract class GuiButton extends GuiScreen
{
	/**Position x of gui button.*/
	public float x;
	/**Position y of gui button.*/
	public float y;
	/**Width button in pixels.*/
	public float width;
	/**Height button in pixels.*/
	public float height;
	public Vector2f buttonScale;
	public Vector2f buttonPos;
	/**Current Gui Object.*/
	public GuiQuad currentGui;
	/**File name in the "assets" folder.*/
	public int textureId;
	public int buttonId;
	/**Loader data about button texture in the Vbo.*/
	public Loader loader = new Loader();
	public GuiRenderer buttonRenderer = new GuiRenderer(this.loader);
	public boolean visible;
	public boolean enable;
	public boolean renderer;
	protected boolean hovered;
	public boolean onButton;
	/**Necessary to control the number of clicks or taps on the screen.*/
	public int clickValue;
	/**When an event occurs when the button is clicked.*/
	public int eventButton;
	private long lastMouseEvent;
	private GuiEngineText buttonText;
	public float buttonTextSize;
	public float buttonTextLength;
	public float textX; 
	public float textY;
	public String text;
	public TextInit textInit = new TextInit();
	public Texts t;
	
	/**
	 * This constructor a draw a rectange form of the button.
	 */
	public GuiButton(int buttonId, String fileIn, float x, float y, 
			float tX, float tY, String textIn)
	{
		this(buttonId, fileIn, x, y, tX, tY, 0.26f, 0.08f, textIn);
	}
	
	public GuiButton(int buttonIdIn, String fileIn, float x, float y, 
			float tX, float tY, float widthIn, float heightIn, String buttonTextIn) 
	{
		this.buttonId = buttonIdIn;
		this.textureId = this.loader.loadTexture(fileIn);
		this.x = x;
		this.y = y;
		this.textX = tX;
		this.textY = tY;
		this.buttonPos = new Vector2f(this.x, this.y);
		this.buttonScale = new Vector2f(this.width, this.height);
		this.textInit.loadDefaultFonts();
		this.buttonTextSize = 1.6f;
		this.buttonTextLength = 0.6f;
		this.buttonText = new GuiEngineText(buttonTextIn, this.buttonTextSize, 
				this.textInit.setDefaultFont(), new Vector2f(this.textX, this.textY), 
					this.buttonTextLength, true);
		this.buttonText.setColour(1, 1, 1);
		this.visible = true;
		this.enable = true;
	}
	
	/**
	 * If type button 0 its basic rectangle, if type button 1 the its simple quad.
	 */
	public GuiButton(int buttonIdIn, String fileIn, float x, float y,
			float tX, float tY, int typeIn, String buttonTextIn) 
	{
		if(typeIn == 0)
		{
			this.width = 0.26f;
			this.height = 0.08f;
		}
		if(typeIn == 1)
		{
			this.width = 0.20f;
			this.height = 0.20f;
		}
		this.buttonId = buttonIdIn;
		this.textureId = this.loader.loadTexture(fileIn);
		this.x = x;
		this.y = y;
		this.textX = tX;
		this.textY = tY;
		this.buttonPos = new Vector2f(this.x, this.y);
		this.buttonScale = new Vector2f(this.width, this.height);
		this.textInit.loadDefaultFonts();
		this.buttonTextSize = 1.6f;
		this.buttonTextLength = 0.6f;
		this.buttonText = new GuiEngineText(buttonTextIn, this.buttonTextSize, 
				this.textInit.setDefaultFont(), new Vector2f(this.textX, this.textY), 
					this.buttonTextLength, true);
		this.buttonText.setColour(1, 1, 1);
		this.visible = true;
		this.enable = true;
	}
	
	public GuiButton(int buttonIdIn, String fileIn, float x, float y, int typeIn, String buttonTextIn) 
	{
		if(typeIn == 0)
		{
			this.width = 0.26f;
			this.height = 0.08f;
		}
		if(typeIn == 1)
		{
			this.width = 0.055f;
			this.height = 0.098f;
		}
		if(typeIn == 2)
		{
			this.width = 0.18f;
			this.height = 0.08f;
		}
		this.buttonId = buttonIdIn;
		this.textureId = this.loader.loadTexture(fileIn);
		this.x = x;
		this.y = y;
		this.buttonPos = new Vector2f(this.x, this.y);
		this.buttonScale = new Vector2f(this.width, this.height);
		this.visible = true;
		this.enable = true;
		this.text = buttonTextIn;
	}
	
	/**
	 * Return 0 if the button is not enabled, and 1if the mouse is bot hovering over this button. 
	 * and 2 if it IS hovering over.
	 * @param mouseOver - false if mouse not hovering over this button.
	 */
	protected int getHoverState(boolean mouseOver)
	{
		int j = 1;
		if(!this.enable)
		{
			j = 0;
			
		} else if (mouseOver)
		{
			j = 2;
		}
			return j;
	}
	
	/**
	 * Render the button into the screen.
	 */
	public void renderButton(boolean isUpdate, boolean isLink)
	{
		if(this.visible)
		{
			this.buttonRenderer.shader.start();
			GlHelper.glBindVertexArray(this.buttonRenderer.quad.getVaoID());
			GlHelper.glEnableVertexAttribArray(0);
			
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(this.getTextureId());
			GlHelper.enableBlend();
			GlHelper.tryBlendFuncSeperate(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA, Blend.ONE, Blend.ZERO);
			GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
			GlHelper.disableDepthTest();
			Matrix4f matrix = Maths.createTransformationMatrix(this.getPosition(), this.getScale());
			this.buttonRenderer.shader.loadTransformation(matrix);
			TextureManager.glDrawTrinangleStrips(0, this.buttonRenderer.quad.getVertexCount());
			
			GlHelper.enableDepthTest();
			GlHelper.disableBlend();
			GlHelper.glDisableVertexAttribArray(0);
			GlHelper.glBindVertexArray(0);
			this.buttonRenderer.shader.stop();
			
			@SuppressWarnings("unused")
			int j = 100;
			
			if(!this.enable)
			{
				j = 105;
			}
			else if(this.hovered)
			{
				j = 160;
			}
			
			if(isUpdate && this.enable)
			{
				if(GuiRenderManager.renderLinkWarning)
				{
					if(isLink)
					{
						this.isUpdate(false);
					}
				}
				this.isUpdate(isUpdate);
			}
			
		}
		
	}
	
	public void isUpdate(boolean isUpdate)
	{
		if(isUpdate && this.enable)
		{
			this.checkHover();
			this.onButtonClick();
		}
	}

	
	public void checkHover()
	{
		if(this.visible)
		{
			Vector2f buttonPos = this.getPosition();
			Vector2f buttonScale = this.getScale();
			Vector2f mouseCoords = Craftix.getNormalizedMouseCoords();
			
			if (buttonPos.y + buttonScale.y > -mouseCoords.y && buttonPos.y - buttonScale.y < -mouseCoords.y 
					&& buttonPos.x + buttonScale.x > mouseCoords.x && buttonPos.x - buttonScale.x < mouseCoords.x) 
			
			{
				if(!this.onButton)
				{
					this.playHoverAnimation(0.004f);
					this.onButton = true;
				}

			}
			else
			{
				if(this.onButton)
				{
					this.onButton = false;
					this.reload();
				}
			}
		}
	}
	
	/**
	 * Brings together all the methods responsible for click events 
	 * and sets limits for each of nick.
	 */
	protected void onButtonClick()
	{
		int k = Mouse.getEventButton();
		
		if(this.enable && this.onButton)
		{
			if(Mouse.getEventButtonState())	
			{
				if(this.clickValue++ > 0)
				{
					return;
				}
				
				this.eventButton = k;
				this.lastMouseEvent = Craftix.getCurrentTime();
				this.onClick(this);
		
			}
			else if(k != -1)
			{
				if(--this.clickValue > 0)
				{
					return;
				}
				
				this.eventButton = -1;
				this.onRelease(this);
			}
			else if(this.eventButton != -1 && this.lastMouseEvent > 0L)
			{
				long l = Craftix.getCurrentTime() - lastMouseEvent;
				this.onMoveClick(this, this.eventButton, l);
			}
		}
	}
	
	public void initText(GuiButton button)
	{
		
	}
	
	public void initText(Texts t)
	{
		
	}
	
	public void hide()
	{
		this.setScale(new Vector2f(0, 0));
	}
	
	public void show(int typeIn)
	{
		if(typeIn == 0)
		{
			this.width = 0.26f;
			this.height = 0.08f;
		}
		if(typeIn == 1)
		{
			this.width = 0.055f;
			this.height = 0.098f;
		}
		if(typeIn == 2)
		{
			this.width = 0.18f;
			this.height = 0.08f;
		}
	}
	
	public void onClick(GuiButton button) 
	{
		this.playSound();
	}
	
	/**
	 * Called when a mouse button is pressed and the mouse is 
	 * moved around.
	 */
	public void onMoveClick(GuiButton button, int clickedMouseButton, long timeLastClick)
	{
		
	}
	
	public void onRelease(GuiButton button)
	{
		
	}

	public void playHoverAnimation(float scaleFactor)
	{
		this.setScale(new Vector2f(buttonScale.x + scaleFactor, buttonScale.y + scaleFactor));
	}
    
    public void playSound()
	{
		SoundLoader.soundPlay(SoundLoader.sourceButtonClick, 
				SoundLoader.bufferButtonClick);
		if(!SoundLoader.sourceButtonClick.isPlaying())
		{
			SoundLoader.sourceMenu1.play(SoundLoader.bufferMenu1);
		}
	
	}
    
    /**
     * Reloads the data about the button such as size, etc.
     */
    public void reload()
    {
    	float minusScaleFactor = 0.004f;
    	this.setScale(new Vector2f(this.buttonScale.x - minusScaleFactor, this.buttonScale.y - minusScaleFactor));
    }
	
	/**
    * Fired when the mouse button is dragged.
    */
    protected void mouseDragged(Craftix cx, int mouseX, int mouseY)
    {
    }
	
	public int getTextureId()
	{
		return this.textureId;
	}
	
	public void setTextureId(int id)
	{
		this.textureId = id;
	}
	
	public Vector2f getPosition()
	{
		return this.buttonPos;
	}
	
	public void setPosition(int xPos, int yPos)
	{
		this.buttonPos = new Vector2f(xPos, yPos);
	}
	
	public Vector2f getScale() 
	{
		return buttonScale;
	}

	public void setScale(Vector2f scale)
	{
		this.buttonScale = scale;
	}
	
	public float getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public GuiEngineText getText()
    {
    	return this.buttonText;
    }
    
}

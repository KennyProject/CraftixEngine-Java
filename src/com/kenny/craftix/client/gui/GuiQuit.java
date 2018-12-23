package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiQuit extends GuiScreen implements IGui 
{
	public List<GuiQuad> guisBackground = new ArrayList<GuiQuad>();

	public void loadQuitScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/logo", 0f, 0.62f, 0.50f, 0.70f));
	}

	@Override
	public void drawGuiButtons() 
	{
		this.b.b14 = new GuiButton(14, BUTTON_RECT, -0.20f, -0.35f, 2, "quit.return") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 14)
				{
					GuiRenderManager.renderQuit = false;
					GuiRenderManager.renderMainMenu = true;
				}
			}
		};
		
		this.b.b15 = new GuiButton(15, BUTTON_RECT, 0.20f, -0.35f, 2, "quit.quitgame") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 15)
				{
					AL.destroy();
					System.exit(1);
				}
			}
		};
	}
	
	@Override
	public void renderScreen() 
	{
		this.b.b14.renderButton(true, false);
		this.b.b15.renderButton(true, false);
	}

	@Override
	public void addToList() 
	{
	}

	@Override
	public void updateButtons()
	{
	}

}

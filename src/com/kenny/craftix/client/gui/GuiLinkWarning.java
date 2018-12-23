package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;


import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;


public class GuiLinkWarning extends GuiScreen implements IGui
{
	public List<GuiQuad> guisBackground = new ArrayList<GuiQuad>();
	
	public void loadLinkWarningScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis()
	{
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", 0.76f, 0.13f, 0.28f, 0.10f));
	}


	@Override
	public void drawGuiButtons() 
	{
		this.b.b12 = new GuiButton(12, BUTTON_RECT, 0.69f, -0.32f, 2, "url.back") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 12)
				{
					GuiRenderManager.renderLinkWarning = false;
				}
			}
		};
		
		this.b.b13 = new GuiButton(13, BUTTON_RECT, 0.69f, -0.12f, 2, "url.continue") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 13)
				{
					isClickedOnURI(true, 21022003);
				}
			}
		};
	}

	@Override
	public void renderScreen() 
	{
		this.b.b12.renderButton(true, false);
		this.b.b13.renderButton(true, false);
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

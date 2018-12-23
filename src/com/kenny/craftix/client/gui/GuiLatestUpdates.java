package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiLatestUpdates extends GuiScreen implements IGui
{
	public List<GuiQuad> guisBackground = new ArrayList<GuiQuad>();
	
	/**Here all gui-textures-buttons*/
	public GuiAbstractButton button_lUpdates_back;
	
	public void loadLatestUpdatesScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.73f, 0.63f, 0.20f, 0.10f));
	}

	@Override
	public void drawGuiButtons() 
	{
		this.b.b11 = new GuiButton(11, BUTTON_RECT, 0.68f, -0.72f, 2, "updates.back") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 11)
				{
					GuiRenderManager.renderMainMenu = true;
					GuiRenderManager.renderUpdatesMenu = false;
				}
			}
		};
	}

	@Override
	public void renderScreen() 
	{
		this.b.b11.renderButton(true, false);
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

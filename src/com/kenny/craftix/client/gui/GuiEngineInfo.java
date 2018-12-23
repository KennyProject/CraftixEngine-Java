package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiEngineInfo extends GuiScreen implements IGui
{
	public List<GuiQuad> guisBackground = new ArrayList<GuiQuad>();
	/**The Main Craftix Instance.*/
	public Craftix cx;
	
	/**All init of Engine Info Gui Buttons-textures.*/
	public GuiAbstractButton button_engineInfo_back;
	
	public void loadEngineInfoScreen(Craftix cxIn)
	{
		this.cx = cxIn;
		this.drawGuis();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.49f, 0.63f, 0.44f, 0.10f));
	}

	@Override
	public void drawGuiButtons()
	{	
		this.b.b10 = new GuiButton(10, BUTTON_RECT, 0.68f, -0.72f, 2, "engineinfo.back") 
		{
			public void onClick(GuiButton button) 
			{
				super.onClick(button);
				if(button.buttonId == 10)
				{
					GuiRenderManager.renderEngineInfo = false;
					GuiRenderManager.renderMainMenu = true;
				}
			}
		};
	}
	
	public void renderScreen() 
	{
		this.b.b10.renderButton(true, false);
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

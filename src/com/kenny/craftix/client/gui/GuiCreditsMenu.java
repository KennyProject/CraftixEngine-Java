package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.init.TextInit;

public class GuiCreditsMenu extends GuiScreen implements IGui
{
	public List<GuiQuad> guisMenuCreditsButtons = new ArrayList<GuiQuad>();
	
	/**All init of Credits Menu Gui textures.*/
	public GuiQuad gui_credits_credit;
	
	/**All init of Creidts Menu Gui Buttons-textures.*/
	public GuiAbstractButton button_credits_back;
	
	public void loadCreditsMenuScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.gui_credits_credit = drawGui("guis/menu/credits", this.cx.cxLoader, 0.0f, 0.75f, 0.40f, 0.45f);
	}
	
	@Override
	public void drawGuiButtons() 
	{
		this.button_credits_back = new GuiAbstractButton("guis/menu/button_medium_base",
				new Vector2f(0.72f,-0.72f), -0.04f, 0f) 
		{
			public void onClick(IButton button) 
			{
				TextInit textInit = new TextInit();
				TextInit.removeCreditsPage();
				textInit.loadDefaultFonts();
				GuiRenderManager.renderCreditsMenu = false;
				GuiRenderManager.renderMainMenu = true;
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_credits_back.show(guisMenuCreditsButtons);
	}

	@Override
	public void addToList() 
	{
		this.guisMenuCreditsButtons.add(gui_credits_credit);
	}

	@Override
	public void updateButtons() 
	{
		this.button_credits_back.update();
	}
	
}

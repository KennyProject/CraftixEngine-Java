package com.kenny.craftix.client.font.text.manager;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.font.GuiEngineText;
import com.kenny.craftix.client.font.render.TextMaster;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiScaled;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.init.TextInit;
import static com.kenny.craftix.init.TextInit.*;

import java.util.ArrayList;
import java.util.List;


import static com.kenny.craftix.client.language.Language.*;

public class TextDraw 
{
	private TextInit textInit;
	private GuiScaled guiScale;
	private Loader loader;
	public static List<GuiEngineText> text = new ArrayList<GuiEngineText>();
	
	public static GuiEngineText t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24, t25, t26, t27, t28, t29, t30;
	
	public void textDraw(Craftix cx)
	{
		this.prepareDraw();
		
		t1 = new GuiEngineText(1, 0.202f, 0.476f - Y, true, false, "mainmenu.singleplayer");
		t2 = new GuiEngineText(2, 0.202f, 0.576f - Y, true, false, "mainmenu.multiplayer");
		t3 = new GuiEngineText(3, 0.202f, 0.676f - Y, true, false, "mainmenu.options");
		t4 = new GuiEngineText(4, 0.202f, 0.776f - Y, true, false, "mainmenu.credits");
		t5 = new GuiEngineText(5, 0.202f, 0.876f - Y, true, false, "mainmenu.quit");
		t6 = new GuiEngineText(6, 0.010f, 0.940f - Y, false, false, "mainmenu.version");
		t7 = new GuiEngineText(7, 0.0f + cX, 0.0f + cY, false, false, "mainmenu.company");
		t8 = new GuiEngineText(8, 0.05f, 0.15f, false, false, "engineinfo.title");
		t9 = new GuiEngineText(9, 0.05f, 0.25f, false, false, "engineinfo.info.craftix");
		t10 = new GuiEngineText(10, 0.05f, 0.30f, false, false, "engineinfo.info.version");
		t11 = new GuiEngineText(11, 0.05f, 0.35f, false, false, "engineinfo.info.type");
		t12 = new GuiEngineText(12, 0.05f, 0.40f, false, false, "engineinfo.info.username");
		t13 = new GuiEngineText(13, 0.05f, 0.45f, false, false, "engineinfo.info.userid");
		t14 = new GuiEngineText(14, 0.05f, 0.50f, false, false, "engineinfo.info.sessionid");
		t15 = new GuiEngineText(15, 0.81f, 0.835f-Y, false, false, "engineinfo.back");
		t16 = new GuiEngineText(16, 0.05f, 0.15f, false, false, "updates.title");
		t17 = new GuiEngineText(17, 0.05f, 0.25f, false, false, "updates.version");
		t18 = new GuiEngineText(18, 0.05f, 0.35f, false, false, "updates.desc");
		t19 = new GuiEngineText(19, 0.81f, 0.835f-Y, false, false, "updates.back");
		t20 = new GuiEngineText(20, 0.75f, 0.40f, false, false, "url.title");
		t21 = new GuiEngineText(21, 0.55f, 0.535f-Y, true, false, "url.continue");
		t22 = new GuiEngineText(22, 0.55f, 0.635f-Y, true, false, "url.back");
		t23 = new GuiEngineText(23, 0.202f, 0.386f-Y, true, false, "quit.desc");
		t24 = new GuiEngineText(24, 0.302f, 0.650f-Y, true, false, "quit.quitgame");
		t25 = new GuiEngineText(25, 0.102f, 0.650f-Y, true, false, "quit.return");
		
		this.endDraw(cx);
	}
	
	protected void prepareDraw()
	{
		this.textInit = new TextInit();
		this.guiScale = new GuiScaled();
		this.loader = new Loader();
		TextMaster.init(this.loader);
		this.guiScale.loadGuiScale();
		this.textInit.loadTextScaleFactor();
		
		
	}
	
	protected void endDraw(Craftix cx)
	{
		this.setupUnlocalizedMessages(cx);
	}
	
	
	protected void setupUnlocalizedMessages(Craftix cx) 
	{
		if(t1.getText() == "mainmenu.singleplayer")
		{
			t1.setText(SINGLEPLAYER);
		}
		if(t2.getText() == "mainmenu.multiplayer")
		{
			t2.setText(MULTIPLAYER);
		}
		if(t3.getText() == "mainmenu.options")
		{
			t3.setText(OPTIONS);
		}
		if(t4.getText() == "mainmenu.credits")
		{
			t4.setText(CREDITS);
		}
		if(t5.getText() == "mainmenu.quit")
		{
			t5.setText(EXIT_GAME);
		}
		if(t6.getText() == "mainmenu.version")
		{
			t6.setText(INFO_ENGINE_VER);
		}
		if(t7.getText() == "mainmenu.company")
		{
			t7.setText(COMPANY);
		}
		if(t8.getText() == "engineinfo.title")
		{
			t8.setText(ENGINE_INFO_TITLE + ":");
		}
		if(t9.getText() == "engineinfo.info.craftix")
		{
			t9.setText(INFO_CRAFTIX);
		}
		if(t10.getText() == "engineinfo.info.version")
		{
			t10.setText(INFO_ENGINE_VER);
		}
		if(t11.getText() == "engineinfo.info.type")
		{
			t11.setText(INFO_VERSION_TYPE + cx.getGameConfig().engineInfo.typeOfVersion);
		}
		if(t12.getText() == "engineinfo.info.username")
		{
			t12.setText(INFO_USERNAME + cx.getSession().getUsername());
		}
		if(t13.getText() == "engineinfo.info.userid")
		{
			t13.setText(INFO_USER_ID + cx.getSession().getPlayerId());
		}
		if(t14.getText() == "engineinfo.info.sessionid")
		{
			t14.setText(INFO_SESSION_ID + cx.getSession().getSessionId());
		}
		if(t15.getText() == "engineinfo.back")
		{
			t15.setText(BACK);
		}
		if(t16.getText() == "updates.title")
		{
			t16.setText(LATEST_UPDATES_TITLE + ":");
		}
		if(t17.getText() == "updates.version")
		{
			t17.setText(UPDATES_VERSION);
		}
		if(t18.getText() == "updates.desc")
		{
			t18.setText(UPDATES_LOG_DESC);
		}
		if(t19.getText() == "updates.back")
		{
			t19.setText(BACK);
		}
		if(t20.getText() == "url.title")
		{
			t20.setText(URI_TITLE);
		}
		if(t21.getText() == "url.continue")
		{
			t21.setText(CONTINUE);
		}
		if(t22.getText() == "url.back")
		{
			t22.setText(BACK);
		}
		if(t23.getText() == "quit.desc")
		{
			t23.setText(QUIT_GAME_DESC);
		}
		if(t24.getText() == "quit.quitgame")
		{
			t24.setText(EXIT);
		}
		if(t25.getText() == "quit.return")
		{
			t25.setText(RETURN);
		}
		
	}
	
	/**
	 * State of the gui screen (Main Menu, Options, Credits, a etc..)
	 */
	public static void renderStates()
	{
		if(GuiRenderManager.renderMainMenu && !GuiRenderManager.renderQuit)
		{
			t1.show(); t2.show(); t3.show(); t4.show(); 
			t5.show(); t6.show(); t7.show();
		} else {
			t1.hide(); t2.hide(); t3.hide(); t4.hide(); 
			t5.hide(); t6.hide(); t7.hide();
		}
		if(GuiRenderManager.renderEngineInfo)
		{
			t8.show(); t9.show(); t10.show(); t11.show();
			t12.show(); t13.show(); t14.show(); t15.show();
		} else {
			t8.hide(); t9.hide(); t10.hide(); t11.hide();
			t12.hide(); t13.hide(); t14.hide(); t15.hide();
		}
		if(GuiRenderManager.renderUpdatesMenu)
		{
			t16.show(); t17.show(); t18.show(); t19.show();
		} else {
			t16.hide(); t17.hide(); t18.hide(); t19.hide();
		}
		if(GuiRenderManager.renderLinkWarning)
		{
			if(!GuiRenderManager.renderSingleplayerMenu || !GuiRenderManager.renderOptionsMenu ||
					!GuiRenderManager.renderCreditsMenu || !GuiRenderManager.renderEngineInfo || !GuiRenderManager.renderUpdatesMenu)
			{
				t20.show(); t21.show(); t22.show();
			}
		} else {
			t20.hide(); t21.hide(); t22.hide();
		}
		if(GuiRenderManager.renderQuit && !GuiRenderManager.renderMainMenu)
		{
			t23.show(); t24.show(); t6.show(); t7.show();
			t25.show();
		} else {
			t23.hide(); t24.hide(); 
			t25.hide();
		}
	}
	
	public TextInit getTextInit()
	{
		return this.textInit;
	}
}

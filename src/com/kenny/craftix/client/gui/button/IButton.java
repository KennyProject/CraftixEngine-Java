package com.kenny.craftix.client.gui.button;

import java.util.List;

import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.gui.GuiQuad;

/**
 * @author Kenny
 */
public interface IButton 
{
	/**Is simple event. Like what happening on click? If button clicked then something
	 * happening.*/
	default void onClick(IButton button)
	{
		GuiAbstractButton.isHoldingOnScreen = false;
		SoundLoader.soundPlay(SoundLoader.sourceButtonClick, SoundLoader.bufferButtonClick);
	}
	public static void onMouseClick(IButton button)
	{
		SoundLoader.soundPlay(SoundLoader.sourceButtonClick, SoundLoader.bufferButtonClick);
	}
	/**An event occurs when the user start hovers over the button area.*/
	//public void onStartHover(IButton button);
	/**An event occurs when the user stop hovers over the button area.*/
	//public void onStopHover(IButton button);
	/**The event occurs when the data in the time you've hover on to the button.*/
	//public void whileHovering(IButton button);
	/**Showing the gui list for button.*/
	public void show(List<GuiQuad> guiTextureList);
	/**Hiding the gui list for button*/
	public void hide(List<GuiQuad> guiTextureList);
	/**This responds to the start of the button animation playback when the cursor is over it.*/
	public void playHoverAnimation(float scaleFactor);
	/**"The "Reset Scale' changes the size of the GUI texture when the animation is running.*/
	public void resetScale();
	/**This is a simple update method for buttons.*/
	public void update();
	public void isVisible(boolean visibleIn);
	public void stopUpdate();
}

package com.kenny.craftix.client.audio;

import java.io.IOException;

import com.kenny.craftix.client.audio.sound.SoundLoader;

/***
 * ITS OLD TEST AUDIO LOADER. SOON THIS CLASS WE BE REMOVED!
 * 
 * @author Kenny
 */
public class AudioMain 
{
	public static void main(String[] args) throws IOException
	{
		AudioMaster.init();
		SoundLoader.loadGameSounds();
		
		char c = ' ';
		while(c != 'q')
		{
			c = (char)System.in.read();
			
			if(c == 'p')
			{
				SoundLoader.soundPlay(SoundLoader.sourceButtonClick, 
						SoundLoader.bufferButtonClick);
			}
			
			if(c == 'o')
			{
				SoundLoader.soundPlay(SoundLoader.sourcePlayerJump, SoundLoader.bufferPlayerJump);
			}
		}
		
		SoundLoader.cleanUpSounds();
		AudioMaster.shutdownAudioMaster();
	}

}

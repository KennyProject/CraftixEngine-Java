package com.kenny.craftix.utils.crash;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.Craftix;

public class Crash 
{
	public static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	
	/**Normal status has index [0].*/
	public boolean statusNormal;
	/**Critical status has index [1].*/
	public boolean statusCritical;
	/**Fatal status has index [2].*/
	public boolean statusFatal;
	/**GlDisplay status has index [10].*/
	public boolean statusGLDisplay;
	
	public void crashManager(int crashStatus)
	{
		if(crashStatus == 0)
		{
			this.statusNormal = true;
			this.crashNormalStatus();
		}
		else if(crashStatus == 1)
		{
			this.statusCritical = true;
			this.crashCriticalStatus();
		}
		else if(crashStatus == 2)
		{
			this.statusFatal = true;
			this.crashFatalStatus();
		}
		else if(crashStatus == 10)
		{
			this.statusGLDisplay = true;
			this.crashGlDisplayStatus();
		}
	}
	
	private void crashNormalStatus()
	{
		if(this.statusNormal)
		{
			LOGGER.error("####### CRASH NORMAL STATUS #######");
			LOGGER.error("Please check if you have made a mistake in the shader ");
			LOGGER.error("or maybe just did not call the right method to work.");
			
		}
	}
	
	private void crashCriticalStatus()
	{
		if(this.statusCritical)
		{
			LOGGER.error("####### CRASH CRITICAL STATUS #######");
			LOGGER.error("The critical status of error indicates that you may have ");
			LOGGER.error("instalize a specific class or made a mistake instalize the ");
			LOGGER.error("accuracy of the methods.");
			
		}
	}
	
	private void crashFatalStatus()
	{
		if(this.statusFatal)
		{
			LOGGER.error("####### CRASH FATAL STATUS #######");
			LOGGER.error("If a fatal crash is caused, it is likely that you have made many ");
			LOGGER.error("different errors in the method or the whole class. If you added ");
			LOGGER.error("a new Fitch then go back to that class and review it again.");
			
		}
	}
	
	public void crashGlDisplayStatus()
	{
		if(this.statusGLDisplay)
		{
			LOGGER.error("####### CRASH GL DISPLAY STATUS #######");
			LOGGER.error("Maybe you incorrectly created or installed display. Maybe ");
			LOGGER.error("even forgot the table of contents.");
		}
	}
}

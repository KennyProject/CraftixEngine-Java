package com.kenny.craftix.command;

import com.kenny.craftix.client.scenes.WorldScene;

public class CommandKill extends Command
{
	/**
	 * Kills a living entity.
	 */
	
	public CommandKill(String commandNameIn, int commandIdIn) 
	{
		super(commandNameIn, commandIdIn);
	}
	
	
	@Override
	public void runCommand(WorldScene worldIn) 
	{
		super.runCommand(worldIn);
		this.world.getPlayer().setHealth(0f);
	}
}

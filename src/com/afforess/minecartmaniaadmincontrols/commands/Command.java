package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public interface Command extends CommandExecutor{
	
	public boolean canExecuteCommand(CommandSender sender);
	public boolean isPlayerOnly();
	public boolean isValidCommand(String label, String[] args);
	public CommandType getCommand();
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args);
	

}

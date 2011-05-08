package com.afforess.minecartmaniaadmincontrols.commands;

public class PermanentEjectCommand extends EjectCommand {
	
	@Override
	public CommandType getCommand() {
		return CommandType.PermEject;
	}
	
	@Override
	public boolean isPermenantEject() {
		return true;
	}

}

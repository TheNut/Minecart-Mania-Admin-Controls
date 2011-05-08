package com.afforess.minecartmaniaadmincontrols.commands;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class ClearEmptyCartsCommand extends ClearAllCartsCommand{
	
	@Override
	public CommandType getCommand() {
		return CommandType.ClearEmptyCarts;
	}
	
	@Override
	public boolean shouldRemoveMinecart(MinecartManiaMinecart minecart) {
		return minecart.isStandardMinecart() && minecart.minecart.getPassenger() == null;
	}

}

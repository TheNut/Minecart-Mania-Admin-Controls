package com.afforess.minecartmaniaadmincontrols.commands;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class ClearStalledCartsCommand extends ClearAllCartsCommand{
	
	@Override
	public CommandType getCommand() {
		return CommandType.ClearStalledCarts;
	}
	
	@Override
	public boolean shouldRemoveMinecart(MinecartManiaMinecart minecart) {
		return !minecart.isMoving();
	}

}
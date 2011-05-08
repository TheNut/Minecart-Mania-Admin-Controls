package com.afforess.minecartmaniaadmincontrols.commands;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class ClearStorageCartsCommand extends ClearAllCartsCommand{
	
	@Override
	public CommandType getCommand() {
		return CommandType.ClearStorageCarts;
	}
	
	@Override
	public boolean shouldRemoveMinecart(MinecartManiaMinecart minecart) {
		return minecart.isStorageMinecart();
	}

}
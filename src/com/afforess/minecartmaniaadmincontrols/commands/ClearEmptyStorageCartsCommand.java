package com.afforess.minecartmaniaadmincontrols.commands;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.minecart.MinecartManiaStorageCart;

public class ClearEmptyStorageCartsCommand extends ClearAllCartsCommand{
	
	@Override
	public CommandType getCommand() {
		return CommandType.ClearEmptyStorageCarts;
	}
	
	@Override
	public boolean shouldRemoveMinecart(MinecartManiaMinecart minecart) {
		return minecart.isStorageMinecart() && ((MinecartManiaStorageCart)minecart).isEmpty();
	}

}
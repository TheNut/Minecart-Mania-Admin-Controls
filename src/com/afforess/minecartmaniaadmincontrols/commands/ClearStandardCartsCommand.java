package com.afforess.minecartmaniaadmincontrols.commands;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class ClearStandardCartsCommand extends ClearAllCartsCommand{
	
	@Override
	public CommandType getCommand() {
		return CommandType.ClearStandardCarts;
	}
	
	@Override
	public boolean shouldRemoveMinecart(MinecartManiaMinecart minecart) {
		return minecart.isStandardMinecart();
	}

}

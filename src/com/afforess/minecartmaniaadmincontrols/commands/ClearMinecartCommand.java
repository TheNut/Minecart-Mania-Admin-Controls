package com.afforess.minecartmaniaadmincontrols.commands;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public interface ClearMinecartCommand extends Command {
	public boolean shouldRemoveMinecart(MinecartManiaMinecart minecart);
}

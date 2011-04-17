package com.afforess.minecartmanaiaadmincontrols.permissions;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class PermissionManager {
	
	private Plugin permissions = null;	
	private PermissionHandler handler = null;
	public PermissionManager(Server server) {
		permissions = server.getPluginManager().getPlugin("Permissions");
		
		if (permissions == null) {
			MinecartManiaLogger.getInstance().log("Permissions not found. Using OP for admin commands.");
		}
		else {
			
			try {
				this.handler = ((Permissions)permissions).getHandler();
				MinecartManiaLogger.getInstance().log("Permissions detected. Using permissions.");
			}
			catch (Exception e) {
				MinecartManiaLogger.getInstance().severe("Permissions failed to load properly!");
			}
		}
	}
	
	public boolean isHasPermissions() {
		return this.handler != null;
	}
	
	public boolean canUseAdminCommand(Player player, String command) {
		if (player.isOp()) {
			return true;
		}
		if (isHasPermissions()) {
			return this.handler.permission(player, "minecartmania.commands." + command.toLowerCase());
		}
		return false;
	}
	
	public boolean canUseCommand(Player player, String command) {
		if (player.isOp()) {
			return true;
		}
		if (isHasPermissions()) {
			return this.handler.permission(player, "minecartmania.commands." + command.toLowerCase());
		}
		return true;
	}

}

package com.afforess.bukkit.minecartmaniaadmincontrols;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class AdminControlsPlayerListener extends PlayerListener{
	public void onPlayerCommand(PlayerChatEvent event) {

		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		String command = event.getMessage();

		if (player.isOp()) {
			if (!event.isCancelled()) {
				event.setCancelled(AdminCommands.doEjectPlayer(player, command));
			}
			
			if (!event.isCancelled()) {
				event.setCancelled(AdminCommands.doPermEjectPlayer(player, command));
			}
			
			if (!event.isCancelled()) {
				event.setCancelled(AdminCommands.doKillEmptyCarts(player, command));
			}
			
			if (!event.isCancelled()) {
				event.setCancelled(AdminCommands.doKillPoweredCarts(player, command));
			}
			
			if (!event.isCancelled()) {
				event.setCancelled(AdminCommands.doKillOccupiedCarts(player, command));
			}
			
			if (!event.isCancelled()) {
				event.setCancelled(AdminCommands.doKillStorageCarts(player, command));
			}
			
			if (!event.isCancelled()) {
				event.setCancelled(AdminCommands.doKillAllCarts(player, command));
			}
			
			if (!event.isCancelled()) {
				event.setCancelled(AdminCommands.reloadConfig(player, command));
			}
			
			if (!event.isCancelled()) {
				event.setCancelled(AdminCommands.setConfigurationKey(player, command));
			}
		}
		
		
		if (!event.isCancelled()) {
			event.setCancelled(PlayerCommands.getConfigurationKey(player, command));
		}
		
		if (!event.isCancelled()) {
			event.setCancelled(PlayerCommands.doStopAtSign(player, command));
		}
		
		if (!event.isCancelled()) {
			event.setCancelled(PlayerCommands.listConfigurationKeys(player, command));
		}
		
		if (!event.isCancelled()) {
			event.setCancelled(PlayerCommands.doMomentumCommand(player, command));
		}
		
		
		
	}
}

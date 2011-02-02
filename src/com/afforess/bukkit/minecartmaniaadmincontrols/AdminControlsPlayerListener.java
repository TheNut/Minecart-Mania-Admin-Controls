package com.afforess.bukkit.minecartmaniaadmincontrols;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemEvent;
import org.bukkit.event.player.PlayerListener;

import com.afforess.bukkit.minecartmaniacore.MinecartManiaWorld;

public class AdminControlsPlayerListener extends PlayerListener{
	
    public void onPlayerItem(PlayerItemEvent event) {
    	if (event.isCancelled()) {
			return;
		}
		if (event.getMaterial().getId() == Material.WOOD_PICKAXE.getId()) {
			if (event.getBlockClicked().getTypeId() == Material.RAILS.getId()) {
				int oldData = event.getBlockClicked().getData();
				int data = oldData + 1;
				if (data > 9) data = 0;
				MinecartManiaWorld.setBlockData(event.getBlockClicked().getX(), event.getBlockClicked().getY(), event.getBlockClicked().getZ(), data);
				event.setCancelled(true);
			}
		}
    }
    
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
			event.setCancelled(PlayerCommands.doSetPower(player, command));
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

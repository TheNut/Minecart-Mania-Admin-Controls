package com.afforess.minecartmaniaadmincontrols.permissions;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;

import com.afforess.minecartmaniaadmincontrols.MinecartManiaAdminControls;
import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.event.MinecartManiaSignFoundEvent;
import com.afforess.minecartmaniacore.event.MinecartManiaSignUpdatedEvent;
import com.afforess.minecartmaniacore.signs.MinecartTypeSign;
import com.afforess.minecartmaniacore.signs.Sign;
import com.afforess.minecartmaniacore.signs.SignAction;
import com.afforess.minecartmaniacore.signs.SignManager;

public class PermissionBlockListener extends BlockListener{
	
	@Override
	public void onSignChange(SignChangeEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (!(event.getBlock().getState() instanceof org.bukkit.block.Sign)) {
			return;
		}
		Sign sign = SignManager.getSignAt(event.getBlock().getLocation(), event.getPlayer());
		String[] old = new String[4];
		for (int i = 0; i < 4; i++) {
			old[i] = ((org.bukkit.block.Sign)event.getBlock().getState()).getLine(i);
			sign.setLine(i, event.getLine(i), false);
		}
		
		MinecartManiaSignFoundEvent mmsfe = new MinecartManiaSignUpdatedEvent(sign, event.getPlayer());
		MinecartManiaCore.server.getPluginManager().callEvent(mmsfe);
		
		Collection<SignAction> actions = sign.getSignActions();
		Iterator<SignAction> i = actions.iterator();
		Player player = event.getPlayer();
		while (i.hasNext()) {
			SignAction action = i.next();
			if (!MinecartManiaAdminControls.permissions.canCreateSign(player, action.getName())) {
				event.setCancelled(true);
				player.sendMessage(LocaleParser.getTextKey("LackPermissionForSign", action.getFriendlyName()));
				SignManager.updateSign(sign.getLocation(), null);
				break;
			}
		}
		
		if (!event.isCancelled() && sign instanceof MinecartTypeSign) {
			if (!MinecartManiaAdminControls.permissions.canCreateSign(player, "minecarttypesign")) {
				player.sendMessage(LocaleParser.getTextKey("LackPermissionForSign", "Minecart Type Sign"));
				SignManager.updateSign(sign.getLocation(), null);
				event.setCancelled(true);
			}
		}
		
		for (int j = 0; j < 4; j++) {
			sign.setLine(j, old[j], false);
		}
	}
	
	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getBlock().getState() instanceof org.bukkit.block.Sign) {
			Sign sign = SignManager.getSignAt(event.getBlock().getLocation());
			Collection<SignAction> actions = sign.getSignActions();
			Iterator<SignAction> i = actions.iterator();
			Player player = event.getPlayer();
			while (i.hasNext()) {
				SignAction action = i.next();
				if (!MinecartManiaAdminControls.permissions.canBreakSign(player, action.getName())) {
					event.setCancelled(true);
					player.sendMessage(LocaleParser.getTextKey("LackPermissionToRemoveSign", action.getFriendlyName()));
					break;
				}
			}
			
			if (sign instanceof MinecartTypeSign) {
				if (!MinecartManiaAdminControls.permissions.canBreakSign(player, "minecarttypesign")) {
					player.sendMessage(LocaleParser.getTextKey("LackPermissionToRemoveSign", "Minecart Type Sign"));
					event.setCancelled(true);
				}
			}
		}
		if (event.isCancelled()) {
			MinecartManiaCore.server.getScheduler().scheduleSyncDelayedTask(MinecartManiaCore.instance, new SignTextUpdater(event.getBlock().getLocation()), 5);
		}
	}
}

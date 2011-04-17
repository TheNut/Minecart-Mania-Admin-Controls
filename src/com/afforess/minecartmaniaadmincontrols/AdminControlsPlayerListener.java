package com.afforess.minecartmaniaadmincontrols;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import com.afforess.minecartmaniacore.Item;
import com.afforess.minecartmaniacore.MinecartManiaWorld;

public class AdminControlsPlayerListener extends PlayerListener{
	
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if ((Item)MinecartManiaWorld.getConfigurationValue("MinecartTrackAdjuster") == null) {
			return;
		}
		if (event.getItem() == null) {
			return;
		}
		int id = event.getItem().getTypeId();
		int data = Item.getItem(id).size() == 1 ? 0 : event.getItem().getDurability();
		Item holding = Item.getItem(id, data);
		if (holding.equals((Item)MinecartManiaWorld.getConfigurationValue("MinecartTrackAdjuster"))) {
			if (event.getClickedBlock() != null && event.getClickedBlock().getTypeId() == Item.RAILS.getId()) {
				int oldData = event.getClickedBlock().getData();
				data = oldData + 1;
				if (data > 9) data = 0;
				MinecartManiaWorld.setBlockData(event.getPlayer().getWorld(), event.getClickedBlock().getX(), event.getClickedBlock().getY(), event.getClickedBlock().getZ(), data);
				event.setCancelled(true);
			}
		}
	}
}

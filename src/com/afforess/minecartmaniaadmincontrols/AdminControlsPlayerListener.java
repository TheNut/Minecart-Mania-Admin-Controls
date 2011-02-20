package com.afforess.minecartmaniaadmincontrols;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemEvent;
import org.bukkit.event.player.PlayerListener;
import com.afforess.minecartmaniacore.MinecartManiaWorld;

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
				MinecartManiaWorld.setBlockData(event.getPlayer().getWorld(), event.getBlockClicked().getX(), event.getBlockClicked().getY(), event.getBlockClicked().getZ(), data);
				event.setCancelled(true);
			}
		}
    }
    
	public void onPlayerCommand(PlayerChatEvent event) {

		if (event.isCancelled()) {
			return;
		}
		
		
	}
}

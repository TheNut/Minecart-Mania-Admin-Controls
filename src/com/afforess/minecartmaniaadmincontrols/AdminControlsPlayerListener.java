package com.afforess.minecartmaniaadmincontrols;
import org.bukkit.Material;
import org.bukkit.event.Event.Type;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import com.afforess.minecartmaniacore.MinecartManiaWorld;

public class AdminControlsPlayerListener extends PlayerListener{
	
    public void onPlayerInteract(PlayerInteractEvent event) {
    	if (event.isCancelled()) {
			return;
		}
    	if (event.getType() != Type.BLOCK_DAMAGE) {
    		return;
    	}
		if (event.getMaterial().getId() == Material.WOOD_PICKAXE.getId()) {
			if (event.getClickedBlock() != null && event.getClickedBlock().getTypeId() == Material.RAILS.getId()) {
				int oldData = event.getClickedBlock().getData();
				int data = oldData + 1;
				if (data > 9) data = 0;
				MinecartManiaWorld.setBlockData(event.getPlayer().getWorld(), event.getClickedBlock().getX(), event.getClickedBlock().getY(), event.getClickedBlock().getZ(), data);
				event.setCancelled(true);
			}
		}
    }
}

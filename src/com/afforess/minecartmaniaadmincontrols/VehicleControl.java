package com.afforess.minecartmaniaadmincontrols;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleListener;
import com.afforess.minecartmaniacore.MinecartManiaWorld;
public class VehicleControl extends VehicleListener{
	
	public static void toggleBlockFromEntering(Player player) {
		if (isBlockedFromEntering(player)) {
			MinecartManiaWorld.getMinecartManiaPlayer(player).setDataValue("Blocked From Entering Minecarts", null);
		}
		else {
			MinecartManiaWorld.getMinecartManiaPlayer(player).setDataValue("Blocked From Entering Minecarts", Boolean.TRUE);
		}
	}
	
	public static boolean isBlockedFromEntering(Player player) {
		return MinecartManiaWorld.getMinecartManiaPlayer(player).getDataValue("Blocked From Entering Minecarts") != null;
	}
	
	public static int getMinecartKillTimer() {
		return MinecartManiaWorld.getIntValue(MinecartManiaWorld.getConfigurationValue("Empty Minecart Kill Timer"));
	}
	
	
	 public void onVehicleEnter(VehicleEnterEvent event) {
		if (event.isCancelled()) {
	    	return;
	    }
    	if (event.getVehicle() instanceof Minecart) {
    		if (event.getEntered() instanceof Player) {
    			if (isBlockedFromEntering((Player)event.getEntered())) {
    				event.setCancelled(true);
    			}
    		}
    	}
    }
}

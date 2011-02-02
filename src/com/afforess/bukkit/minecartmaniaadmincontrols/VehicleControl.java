package com.afforess.bukkit.minecartmaniaadmincontrols;

import java.util.ArrayList;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleListener;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaWorld;
public class VehicleControl extends VehicleListener{
	
	private static final ArrayList<String> blockedFromEnteringMinecarts = new ArrayList<String>();

	public static void toggleBlockFromEntering(String name) {
		if (isBlockedFromEntering(name)) {
			blockedFromEnteringMinecarts.remove(name);
		}
		else {
			blockedFromEnteringMinecarts.add(name);
		}
	}
	
	public static boolean isBlockedFromEntering(String name) {
		return blockedFromEnteringMinecarts.contains(name);
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
    			if (isBlockedFromEntering(((Player)event.getEntered()).getName())) {
    				event.setCancelled(true);
    			}
    		}
    	}
    }
}

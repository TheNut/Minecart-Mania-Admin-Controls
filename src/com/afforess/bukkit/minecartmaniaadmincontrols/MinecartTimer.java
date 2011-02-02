package com.afforess.bukkit.minecartmaniaadmincontrols;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.bukkit.minecartmaniacore.event.MinecartManiaListener;
import com.afforess.bukkit.minecartmaniacore.event.MinecartTimeEvent;

public class MinecartTimer extends MinecartManiaListener{
	public void onMinecartTimeEvent(MinecartTimeEvent event) {
		MinecartManiaMinecart minecart = event.getMinecart();
		if (minecart.isStandardMinecart()) {
			if (VehicleControl.getMinecartKillTimer() > 0){
				//No Passenger
				if (minecart.minecart.getPassenger() == null) {
					//No timer, start counting
					if (minecart.getDataValue("Empty Timer") == null) {
						minecart.setDataValue("Empty Timer", new Integer(VehicleControl.getMinecartKillTimer() * 60));
					}
					else {
						//Decrement timer
						Integer timeLeft = (Integer)minecart.getDataValue("Empty Timer");
						if (timeLeft > 1) {
							minecart.setDataValue("Empty Timer", new Integer(timeLeft.intValue()-1));
						}
						else {
							minecart.kill();
						}
					}
				}
				//has passenger, resent counter if already set
				else {
					if (minecart.getDataValue("Empty Timer") != null) {
						minecart.setDataValue("Empty Timer", null);
					}
				}
			}
		}
	}
}

package com.afforess.minecartmaniaadmincontrols.permissions;

import org.bukkit.Location;

public class SignTextUpdater implements Runnable{
	private Location sign;
	
	public SignTextUpdater(Location location) {
		sign = location;
	}

	@Override
	public void run() {
		sign.getBlock().getState().update(true);
	}

}

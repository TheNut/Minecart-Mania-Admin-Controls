package com.afforess.minecartmanaiaadmincontrols.permissions;

import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;

public class PermissionBlockListener extends BlockListener{
	
	@Override
	public void onSignChange(SignChangeEvent event) {
		if (event.isCancelled()) {
			return;
		}
		for (String line : event.getLines()) {
		}
	}

}

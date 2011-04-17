package com.afforess.minecartmaniaadmincontrols.commands;

public enum Commands {
	Debug(true),
	Eject(true),
	PermEject(true),
	ClearEmptyCarts(true),
	ClearPoweredCarts(true),
	ClearOccupiedCarts(true),
	ClearStorageCarts(true),
	ClearAllCarts(true),
	SetConfigKey(true),
	GetConfigKey(false),
	ListConfigKeys(false),
	TruCompass(false),
	St(false),
	Throttle(false),
	Momentum(false);
	
	
	private boolean admin = false;
	private Commands(boolean admin) {
		this.admin = admin;
	}
	
	public boolean isAdminCommand() {
		return admin;
	}
	
	public String toString() {
		return name().toLowerCase();
	}
	
	public static boolean isAdminCommand(String command) {
		for (Commands c : values()){
			if (c.toString().equals(command)) {
				return c.isAdminCommand();
			}
		}
		return false;
	}

}

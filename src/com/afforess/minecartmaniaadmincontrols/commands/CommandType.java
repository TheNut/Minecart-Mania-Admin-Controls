package com.afforess.minecartmaniaadmincontrols.commands;

import java.lang.reflect.Constructor;

public enum CommandType {
	MM(false, MinecartInfoCommand.class),
	Debug(true, DebugCommand.class),
	Eject(true, EjectCommand.class),
	PermEject(true, PermanentEjectCommand.class),
	ClearEmptyCarts(true, ClearEmptyCartsCommand.class),
	ClearStandardCarts(true, ClearStandardCartsCommand.class),
	ClearPoweredCarts(true, ClearPoweredCartsCommand.class),
	ClearOccupiedCarts(true, ClearOccupiedCartsCommand.class),
	ClearStorageCarts(true, ClearStorageCartsCommand.class),
	ClearEmptyStorageCarts(true, ClearEmptyStorageCartsCommand.class),
	ClearMovingCarts(true, ClearMovingCartsCommand.class),
	ClearStalledCarts(true, ClearStalledCartsCommand.class),
	ClearAllCarts(true, ClearAllCartsCommand.class),
	SetConfigKey(true, SetConfigurationKeyCommand.class),
	GetConfigKey(false, GetConfigurationKeyCommand.class),
	ListConfigKeys(false, ListConfigurationKeysCommand.class),
	TruCompass(false, TruCompassCommand.class),
	St(false, StationCommand.class),
	Throttle(false, ThrottleCommand.class),
	Momentum(false, MomentumCommand.class),
	Redraw(true, RedrawMinecartCommand.class),
	Hide(true, HideMinecartCommand.class),
	Info(false, MinecartInfoCommand.class);
	
	
	private boolean admin = false;
	private Class<? extends Command> command = null;
	private CommandType(boolean admin, Class<? extends Command> command) {
		this.admin = admin;
		this.command = command;
	}
	
	public boolean isAdminCommand() {
		return admin;
	}
	
	public Command getCommand() {
		try {
			Constructor<? extends Command> c = this.command.getConstructor();
			return c.newInstance((Object[])null);
		}
		catch (Exception e) {}
		return null;
	}
	
	public String toString() {
		return name().toLowerCase();
	}
	
	public static boolean isAdminCommand(String command) {
		for (CommandType c : values()){
			if (c.toString().equalsIgnoreCase(command)) {
				return c.isAdminCommand();
			}
		}
		return false;
	}
	


}

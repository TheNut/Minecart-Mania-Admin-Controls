package com.afforess.minecartmaniaadmincontrols;
import com.afforess.minecartmaniacore.config.Setting;

public class SettingList {
	public final static Setting[] config = {
		new Setting(
				"Empty Minecart Kill Timer", 
				new Integer(-1),
				"After a minecart has been empty longer than the set time (in minutes) it will be destroyed. Negative values will disable the timer.",
				MinecartManiaAdminControls.description.getName()
		)
	};

}

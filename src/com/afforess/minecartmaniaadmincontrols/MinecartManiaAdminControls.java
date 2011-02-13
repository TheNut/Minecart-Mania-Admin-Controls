package com.afforess.minecartmaniaadmincontrols;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

import com.afforess.minecartmaniacore.Configuration;



public class MinecartManiaAdminControls extends JavaPlugin{

	public MinecartManiaAdminControls(PluginLoader pluginLoader,
			Server instance, PluginDescriptionFile desc, File folder,
			File plugin, ClassLoader cLoader) {
		super(pluginLoader, instance, desc, folder, plugin, cLoader);
		server = instance;
		description = desc;
	}

	public static Logger log;
	public static Server server;
	public static PluginDescriptionFile description;
	private static final AdminControlsPlayerListener playerListener = new AdminControlsPlayerListener();
	private static final VehicleControl vehicleListener = new VehicleControl();
	private static final MinecartTimer timer = new MinecartTimer();
	

	public void onEnable(){
		log = Logger.getLogger("Minecraft");
		PluginDescriptionFile pdfFile = this.getDescription();
		
		Plugin MinecartMania = server.getPluginManager().getPlugin("Minecart Mania Core");
		
		if (MinecartMania == null) {
			log.severe(pdfFile.getName() + " requires Minecart Mania Core to function!");
			log.severe(pdfFile.getName() + " is disabled!");
			this.setEnabled(false);
		}
		else {
			Configuration.loadConfiguration(description, SettingList.config);
	        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_ITEM, playerListener, Priority.Normal, this);
	        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Lowest, this);
	        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_ENTER, vehicleListener, Priority.Normal, this);
	        getServer().getPluginManager().registerEvent(Event.Type.CUSTOM_EVENT, timer, Priority.Normal, this);
	        
	        
	        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		}
	}
	
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
    	System.out.println("CommandLabel: "+commandLabel+" Command: "+command);
		if (commandLabel.contains("reloadconfig")) {
			Configuration.loadConfiguration(description, SettingList.config);
		}
		return false;
	}
}

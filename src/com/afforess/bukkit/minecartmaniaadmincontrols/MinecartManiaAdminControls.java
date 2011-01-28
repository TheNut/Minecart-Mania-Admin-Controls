package com.afforess.bukkit.minecartmaniaadmincontrols;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;



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
	private static final VehicleControl ejectionListener = new VehicleControl();
	

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
	        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
	        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_ENTER, ejectionListener, Priority.Normal, this);
	        
	        
	        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		}
	}
	
	public void onDisable(){
		
	}
	
	public boolean onCommand(Player player, Command c, String s, String[] list) {
		if (s.contains("reloadconfig")) {
			//Configuration.loadConfiguration();
		}
		return true;
	}
}

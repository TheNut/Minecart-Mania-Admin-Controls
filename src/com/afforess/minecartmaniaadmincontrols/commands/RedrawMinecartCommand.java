package com.afforess.minecartmaniaadmincontrols.commands;

import java.util.ArrayList;

import net.minecraft.server.Packet;
import net.minecraft.server.Packet23VehicleSpawn;
import net.minecraft.server.Packet29DestroyEntity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftMinecart;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class RedrawMinecartCommand extends MinecartManiaCommand{

	@Override
	public boolean isPlayerOnly() {
		return false;
	}

	@Override
	public CommandType getCommand() {
		return CommandType.Hide;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player[] online = Bukkit.getServer().getOnlinePlayers();
		ArrayList<MinecartManiaMinecart> minecarts = MinecartManiaWorld.getMinecartManiaMinecartList();
		for (Player p : online) {
			CraftPlayer player = (CraftPlayer)p;
			for (final MinecartManiaMinecart minecart : minecarts) {
				final Entity passenger = minecart.minecart.getPassenger();
				minecart.minecart.eject();
				Packet packet = new Packet29DestroyEntity(minecart.minecart.getEntityId());
				player.getHandle().netServerHandler.sendPacket(packet);
				packet = null;
				if (minecart.isStandardMinecart()) {
					packet = new Packet23VehicleSpawn(((CraftMinecart)minecart.minecart).getHandle(), 10);
				}
				else if (minecart.isPoweredMinecart()) {
					packet = new Packet23VehicleSpawn(((CraftMinecart)minecart.minecart).getHandle(), 12);
				}
				else if (minecart.isStorageMinecart()) {
					packet = new Packet23VehicleSpawn(((CraftMinecart)minecart.minecart).getHandle(), 11);
				}
				player.getHandle().netServerHandler.sendPacket(packet);
				if (passenger != null) {
					Runnable update = new Runnable() {
						public void run() {
							minecart.minecart.setPassenger(passenger);
						}
					};
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MinecartManiaCore.instance, update, 10);
					
				}
			}
		}
		sender.sendMessage("All minecarts redrawn");
		return true;
	}

}

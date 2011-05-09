package com.afforess.minecartmaniaadmincontrols.commands;

import java.util.ArrayList;

import net.minecraft.server.Packet;
import net.minecraft.server.Packet29DestroyEntity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class HideMinecartCommand extends MinecartManiaCommand{

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
			for (MinecartManiaMinecart minecart : minecarts) {
				Packet packet = new Packet29DestroyEntity(minecart.minecart.getEntityId());
				player.getHandle().netServerHandler.sendPacket(packet);
			}
		}
		sender.sendMessage(LocaleParser.getTextKey("AdminControlsHideMinecarts"));
		return true;
	}

}

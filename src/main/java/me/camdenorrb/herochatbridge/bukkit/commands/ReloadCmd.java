package me.camdenorrb.herochatbridge.bukkit.commands;

import me.camdenorrb.herochatbridge.bukkit.BukkitPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public final class ReloadCmd implements CommandExecutor {

	private final BukkitPlugin plugin;


	public ReloadCmd(final BukkitPlugin plugin) {
		this.plugin = plugin;
	}


	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

		plugin.loadConfig();
		sender.sendMessage(ChatColor.GREEN + "The config has been reloaded!");

		return true;
	}

}

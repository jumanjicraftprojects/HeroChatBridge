package me.camdenorrb.herochatbridge.bungee.commands;

import me.camdenorrb.herochatbridge.bungee.BungeePlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import org.bukkit.ChatColor;


public final class ReloadCmd extends Command {

	private final BungeePlugin plugin;

	public ReloadCmd(final BungeePlugin plugin) {
		super("herobungeechatreload", "HeroChatBridge.reload");
		this.plugin = plugin;
	}


	@Override
	public void execute(final CommandSender sender, final String[] args) {
		plugin.loadConfig();
		sender.sendMessage(new TextComponent(ChatColor.GREEN + "The config has been reloaded!"));
	}

}

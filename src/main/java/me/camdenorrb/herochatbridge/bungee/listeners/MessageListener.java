package me.camdenorrb.herochatbridge.bungee.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.camdenorrb.herochatbridge.bungee.BungeePlugin;
import me.camdenorrb.herochatbridge.bungee.config.Config;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;


public final class MessageListener implements Listener {

	private final BungeePlugin plugin;

	public MessageListener(final BungeePlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPluginMessage(final PluginMessageEvent event) {

		if (!event.getTag().equalsIgnoreCase("herochat:bridge")) {
			return;
		}
		
		final Config config = plugin.getBungeeConfig();

		final ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());

		final String chatChannel = in.readUTF().toLowerCase();
		in.readUTF(); // Symbol prefix
		final String message = in.readUTF();

		if (config.shouldLog()) {
			plugin.getProxy().getLogger().info(message);
		}
		
		if (config.shouldFilter() && config.shouldWhitelist() != config.getChannels().contains(chatChannel)) {
			return;
		}
		
		final InetSocketAddress addr = event.getSender().getAddress();
		
		for (ServerInfo server : plugin.getProxy().getServers().values()) {
			if (server.getAddress().equals(addr) || server.getPlayers().isEmpty()) continue;
			server.sendData("herochat:bridge", event.getData());
		}
	}

}

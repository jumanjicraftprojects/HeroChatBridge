package me.camdenorrb.herochatbridge.bukkit;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.Herochat;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.LangUtil;
import github.scarsz.discordsrv.util.PluginUtil;
import me.camdenorrb.herochatbridge.bukkit.commands.ReloadCmd;
import me.camdenorrb.herochatbridge.bukkit.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Objects;


public final class BukkitPlugin extends JavaPlugin implements PluginMessageListener {

	private String symbolPrefix = "";

	private boolean loadedDiscordSRV;

	@Override
	public void onEnable() {

		loadConfig();

		getServer().getMessenger().registerOutgoingPluginChannel(this, "herochat:bridge");
		getServer().getMessenger().registerIncomingPluginChannel(this, "herochat:bridge", this);

		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

		Objects.requireNonNull(getCommand("herobukkitchatreload")).setExecutor(new ReloadCmd(this));

		loadedDiscordSRV = getServer().getPluginManager().getPlugin("MyPlugin") != null;
	}


	@Override
	public void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {

		if (!channel.equals("herochat:bridge")) {
			return;
		}

		final ByteArrayDataInput input = ByteStreams.newDataInput(message);

		final String chatChannel = input.readUTF();
		final String symbolPrefix = input.readUTF();
		final String playerMessage = input.readUTF();

		final Channel heroChannel = Herochat.getChannelManager().getChannel(chatChannel);

		if (heroChannel == null) {
			return;
		}

		heroChannel.sendRawMessage(symbolPrefix + playerMessage);

		if (loadedDiscordSRV) {

			if (!player.hasPermission("discordsrv.chat")) {
				DiscordSRV.debug("User " + player.getName() + " sent a message but it was not delivered to Discord due to lack of permission");
				return;
			}

			getServer().getScheduler().runTaskAsynchronously(this, () -> {
				DiscordSRV.getPlugin().processChatMessage(player, event.getMessage(), DiscordSRV.getPlugin().getChannels().size() == 1 ? null : "global", event.isCancelled());

			});

		}
	}


	public void loadConfig() {
		saveDefaultConfig();
		symbolPrefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("symbol-prefix", "")));
	}

	public String getSymbolPrefix() {
		return symbolPrefix;
	}

}

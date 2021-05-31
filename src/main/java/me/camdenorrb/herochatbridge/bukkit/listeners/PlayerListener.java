package me.camdenorrb.herochatbridge.bukkit.listeners;

import com.dthielke.herochat.ChatCompleteEvent;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.camdenorrb.herochatbridge.bukkit.BukkitPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;


public final class PlayerListener implements Listener {

	private final BukkitPlugin plugin;


	public PlayerListener(final BukkitPlugin plugin) {
		this.plugin = plugin;
	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void onChatComplete(final ChatCompleteEvent event) {

		final ByteArrayDataOutput output = ByteStreams.newDataOutput();

		output.writeUTF(event.getChannel().getName());
		output.writeUTF(plugin.getSymbolPrefix());
		output.writeUTF(event.getMsg());
		output.writeUTF(event.getSender().getName());

		event.getSender().getPlayer().sendPluginMessage(plugin, "herochat:bridge", output.toByteArray());
	}

}

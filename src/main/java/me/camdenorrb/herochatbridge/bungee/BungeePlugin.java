package me.camdenorrb.herochatbridge.bungee;

import me.camdenorrb.herochatbridge.bungee.commands.ReloadCmd;
import me.camdenorrb.herochatbridge.bungee.config.Config;
import me.camdenorrb.herochatbridge.bungee.listeners.MessageListener;
import me.camdenorrb.jcommons.utils.TryUtils;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;


public final class BungeePlugin extends Plugin {

	private Config bungeeConfig;


	@Override
	public void onEnable() {

		loadConfig();

		getProxy().registerChannel("herochat:bridge");

		getProxy().getPluginManager().registerCommand(this, new ReloadCmd(this));

		getProxy().getPluginManager().registerListener(this, new MessageListener(this));
	}


	public Config getBungeeConfig() {
		return bungeeConfig;
	}

	public void loadConfig() {
		TryUtils.attemptOrPrintErr(() ->
			bungeeConfig = Config.fromOrMake(new File(getDataFolder(), "bungeeConfig.yml"))
		);
	}
}

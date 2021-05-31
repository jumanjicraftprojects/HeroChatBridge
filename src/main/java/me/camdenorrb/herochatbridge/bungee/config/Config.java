package me.camdenorrb.herochatbridge.bungee.config;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;


public final class Config {

	private final List<String> channels;

	private final boolean filter, log, whitelist;


	public Config(List<String> channels, boolean log, boolean filter, boolean whitelist) {
		this.log = log;
		this.filter = filter;
		this.channels = channels;
		this.whitelist = whitelist;
	}


	public boolean shouldLog() {
		return log;
	}

	public boolean shouldFilter() {
		return filter;
	}

	public boolean shouldWhitelist() {
		return whitelist;
	}

	public List<String> getChannels() {
		return channels;
	}


	public static Config fromOrMake(final File file) throws IOException {

		file.getParentFile().mkdirs();

		if (!file.exists()) {

			file.createNewFile();

			final InputStream inputStream = Config.class.getResourceAsStream("bungeeConfig.yml");
			Files.copy(inputStream, file.toPath());
			inputStream.close();
		}

		final Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

		final boolean filter = config.getBoolean("filter", false);
		final boolean whitelist = config.getBoolean("whitelist", true);
		final List<String> channels = config.getStringList("channels");
		final boolean log = config.getBoolean("log", false);

		return new Config(channels, log, filter, whitelist);
	}

}

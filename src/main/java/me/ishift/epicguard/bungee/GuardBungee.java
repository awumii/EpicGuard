package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.command.GuardCommand;
import me.ishift.epicguard.bungee.listener.ProxyPingListener;
import me.ishift.epicguard.bungee.listener.ProxyPreLoginListener;
import me.ishift.epicguard.bungee.task.AttackClearTask;
import me.ishift.epicguard.bungee.task.CloudTask;
import me.ishift.epicguard.bungee.task.DisplayTask;
import me.ishift.epicguard.bungee.util.MessagesBungee;
import me.ishift.epicguard.bungee.util.Metrics;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.ServerType;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import me.ishift.epicguard.universal.util.LogoPrinter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class GuardBungee extends Plugin {
    public static boolean log = true;
    public static boolean status = false;
    private static GuardBungee instance;

    public static GuardBungee getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        try {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }

            final File dir1 = new File(this.getDataFolder() + "/logs");
            if (!dir1.exists()) {
                dir1.mkdir();
            }

            final File dir3 = new File(this.getDataFolder() + "/data");
            if (!dir3.exists()) {
                dir3.mkdir();
            }

            instance = this;
            Logger.create(ServerType.BUNGEE);
            LogoPrinter.print();
            this.loadConfig();
            MessagesBungee.load();
            GeoAPI.create(ServerType.BUNGEE);
            new Metrics(this, 5956);
            this.getProxy().getPluginManager().registerListener(this, new ProxyPreLoginListener());
            this.getProxy().getPluginManager().registerListener(this, new ProxyPingListener());

            this.getProxy().getScheduler().schedule(this, new AttackClearTask(), 1L, 30L, TimeUnit.SECONDS);
            this.getProxy().getScheduler().schedule(this, new DisplayTask(), 1L, 300L, TimeUnit.MILLISECONDS);
            this.getProxy().getScheduler().schedule(this, new CloudTask(), 1L, Config.cloudTime, TimeUnit.SECONDS);
            this.getProxy().getPluginManager().registerCommand(this, new GuardCommand("guard"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try {
            final File file = new File(getDataFolder(), "config_bungee.yml");
            if (!file.exists()) {
                try (InputStream in = getResourceAsStream("config_bungee.yml")) {
                    Files.copy(in, file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            final Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config_bungee.yml"));
            Config.firewallEnabled = config.getBoolean("firewall");
            Config.firewallBlacklistCommand = config.getString("firewall.command-blacklist");
            Config.firewallWhitelistCommand = config.getString("firewall.command-whitelist");
            Config.connectSpeed = config.getInt("speed.connection");
            Config.pingSpeed = config.getInt("speed.ping-speed");
            Config.apiKey = config.getString("antibot.api-key");
            Config.countryList = config.getStringList("countries.list");
            Config.countryMode = config.getString("countries.mode");
            Config.antibot = config.getBoolean("antibot.enabled");
            Config.attackResetTimer = config.getLong("speed.attack-timer-reset");
            Config.blockedNames = config.getStringList("antibot.name-contains");
            Config.cloudEnabled = config.getBoolean("cloud.enabled");
            Config.cloudBlacklist = config.getBoolean("cloud.features.blacklist");
            Config.cloudTime = config.getLong("cloud.sync-every-seconds");
            Config.filterEnabled = config.getBoolean("console-filter.enabled");
            Config.filterValues = config.getStringList("console-filter.messages");
            Config.bandwidthOptimizer = config.getBoolean("bandwidth-optimizer");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

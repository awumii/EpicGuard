package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.command.GuardCommand;
import me.ishift.epicguard.bungee.file.CustomFile;
import me.ishift.epicguard.bungee.file.FileManager;
import me.ishift.epicguard.bungee.listener.ProxyPingListener;
import me.ishift.epicguard.bungee.listener.ProxyPreLoginListener;
import me.ishift.epicguard.bungee.task.AttackClearTask;
import me.ishift.epicguard.bungee.task.CloudTask;
import me.ishift.epicguard.bungee.task.DisplayTask;
import me.ishift.epicguard.bungee.util.MessagesBungee;
import me.ishift.epicguard.bungee.util.Metrics;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import me.ishift.epicguard.universal.util.ServerType;
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
    public static Plugin plugin;
    public static boolean log = true;
    public static boolean status = false;

    @SuppressWarnings("ResultOfMethodCallIgnored")
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

            plugin = this;
            new Logger(ServerType.BUNGEE);
            Logger.info("Starting plugin...");
            this.loadConfig();
            MessagesBungee.load();
            new GeoAPI(ServerType.BUNGEE);
            new Metrics(this);
            this.getProxy().getPluginManager().registerListener(this, new ProxyPreLoginListener());
            this.getProxy().getPluginManager().registerListener(this, new ProxyPingListener());

            this.getProxy().getScheduler().schedule(this, new AttackClearTask(), 1L, 30L, TimeUnit.SECONDS);
            this.getProxy().getScheduler().schedule(this, new DisplayTask(), 1L, 300L, TimeUnit.MILLISECONDS);
            this.getProxy().getScheduler().schedule(this, new CloudTask(), 1L, Config.cloudTime, TimeUnit.SECONDS);
            this.getProxy().getPluginManager().registerCommand(this, new GuardCommand("guard"));
        } catch (IOException e) {
            Logger.throwException(e);
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
            final Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config_bungee.yml"));
            Config.firewallEnabled = cfg.getBoolean("firewall");
            Config.firewallBlacklistCommand = cfg.getString("firewall.command-blacklist");
            Config.firewallWhitelistCommand = cfg.getString("firewall.command-whitelist");
            Config.connectSpeed = cfg.getInt("speed.connection");
            Config.pingSpeed = cfg.getInt("speed.ping-speed");
            Config.antibotQuery1 = cfg.getString("antibot.checkers.1.adress");
            Config.antibotQuery2 = cfg.getString("antibot.checkers.2.adress");
            Config.antibotQuery3 = cfg.getString("antibot.checkers.3.adress");
            Config.antibotQueryContains = cfg.getStringList("antibot.checkers.responses");
            Config.countryList = cfg.getStringList("countries.list");
            Config.countryMode = cfg.getString("countries.mode");
            Config.antibot = cfg.getBoolean("antibot.enabled");
            Config.attackResetTimer = cfg.getLong("speed.attack-timer-reset");
            Config.blockedNames = cfg.getStringList("antibot.name-contains");

            final String path = this.getDataFolder() + "/cloud.yml";
            FileManager.createFile(path);
            final CustomFile cloudFile = FileManager.getFile(path);
            if (!cloudFile.exist()) {
                cloudFile.create();
                cloudFile.getConfig().set("cloud.enabled", true);
                cloudFile.getConfig().set("cloud.sync-every-seconds", 1800);
                cloudFile.getConfig().set("cloud.features.blacklist", true);
                cloudFile.save();
            }

            Config.cloudEnabled = cloudFile.getConfig().getBoolean("cloud.enabled");
            Config.cloudBlacklist = cloudFile.getConfig().getBoolean("cloud.features.blacklist");
            Config.cloudTime = cloudFile.getConfig().getLong("cloud.sync-every-seconds");

        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}

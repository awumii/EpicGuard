package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.command.GuardCommand;
import me.ishift.epicguard.bungee.listener.ProxyPingListener;
import me.ishift.epicguard.bungee.listener.ProxyPreLoginListener;
import me.ishift.epicguard.bungee.task.AttackClearTask;
import me.ishift.epicguard.bungee.task.DisplayTask;
import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.bungee.util.MessagesBungee;
import me.ishift.epicguard.bungee.util.Metrics;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import me.ishift.epicguard.universal.util.ServerType;
import net.md_5.bungee.api.chat.TextComponent;
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
    public static boolean display = false;
    public static boolean log = true;

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
            this.getProxy().getScheduler().schedule(this, new AttackClearTask(), 1, 30, TimeUnit.SECONDS);
            this.getProxy().getScheduler().schedule(this, new DisplayTask(), 1, 300, TimeUnit.MILLISECONDS);
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
            Config.FIREWALL = cfg.getBoolean("firewall");
            Config.FIREWALL_BL = cfg.getString("firewall.command-blacklist");
            Config.FIREWALL_WL = cfg.getString("firewall.command-whitelist");
            Config.CONNECT_SPEED = cfg.getInt("speed.connection");
            Config.PING_SPEED = cfg.getInt("speed.ping-speed");
            Config.ANTIBOT_QUERY_1 = cfg.getString("antibot.checkers.1.adress");
            Config.ANTIBOT_QUERY_2 = cfg.getString("antibot.checkers.2.adress");
            Config.ANTIBOT_QUERY_3 = cfg.getString("antibot.checkers.3.adress");
            Config.ANTIBOT_QUERY_CONTAINS = cfg.getStringList("antibot.checkers.responses");
            Config.COUNTRIES = cfg.getStringList("countries.list");
            Config.COUNTRY_MODE = cfg.getString("countries.mode");
            Config.ANTIBOT = cfg.getBoolean("antibot.enabled");
            Config.ATTACK_TIMER = cfg.getLong("speed.attack-timer-reset");
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}

package io.github.polskistevek.epicguard.bungee;

import io.github.polskistevek.epicguard.bungee.listener.ProxyPingListener;
import io.github.polskistevek.epicguard.bungee.listener.ProxyPreLoginListener;
import io.github.polskistevek.epicguard.bungee.task.AttackClearTask;
import io.github.polskistevek.epicguard.bungee.util.MessagesBungee;
import io.github.polskistevek.epicguard.bungee.util.Metrics;
import io.github.polskistevek.epicguard.universal.util.GeoDataase;
import io.github.polskistevek.epicguard.universal.util.Logger;
import io.github.polskistevek.epicguard.universal.util.ServerType;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class GuardBungee extends Plugin {
    public static Plugin plugin;
    public static String FIREWALL_BL;
    public static String FIREWALL_WL;
    public static boolean FIREWALL;
    public static int CPS_ACTIVATE;
    public static int CPS_PING_ACTIVATE;
    public static String ANTIBOT_QUERY_1;
    public static String ANTIBOT_QUERY_2;
    public static String ANTIBOT_QUERY_3;
    public static List<String> ANTIBOT_QUERY_CONTAINS;
    public static List<String> COUNTRIES;
    public static String COUNTRY_MODE;
    public static boolean ANTIBOT;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onEnable() {
        try {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }

            File dir1 = new File(this.getDataFolder() + "/logs");
            if (!dir1.exists()){
                dir1.mkdir();
            }

            File dir3 = new File(this.getDataFolder() + "/data");
            if (!dir3.exists()){
                dir3.mkdir();
            }

            plugin = this;
            new Logger(ServerType.BUNGEE);
            Logger.info("Starting plugin...");
            loadConfig();
            MessagesBungee.load();
            AttackClearTask.start();
            Logger.info("Loading GeoIP Database..");
            new GeoDataase(ServerType.BUNGEE);
            Logger.info("Error with GeoIP Database. Do not report this, this is not a bug. Download database at resource site.");
            new Metrics(this);
            getProxy().getPluginManager().registerListener(this, new ProxyPreLoginListener());
            getProxy().getPluginManager().registerListener(this, new ProxyPingListener());
        } catch (IOException e) {
            Logger.throwException(e);
        }
    }

    private void loadConfig() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            FIREWALL = cfg.getBoolean("firewall");
            FIREWALL_BL = cfg.getString("firewall.command-blacklist");
            FIREWALL_WL = cfg.getString("firewall.command-whitelist");
            CPS_ACTIVATE = cfg.getInt("speed.connection");
            CPS_PING_ACTIVATE = cfg.getInt("speed.ping-speed");
            ANTIBOT_QUERY_1 = cfg.getString("antibot.checkers.1.adress");
            ANTIBOT_QUERY_2 = cfg.getString("antibot.checkers.2.adress");
            ANTIBOT_QUERY_3 = cfg.getString("antibot.checkers.3.adress");
            ANTIBOT_QUERY_CONTAINS = cfg.getStringList("antibot.checkers.responses");
            COUNTRIES = cfg.getStringList("countries.list");
            COUNTRY_MODE = cfg.getString("countries.mode");
            ANTIBOT = cfg.getBoolean("antibot.enabled");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

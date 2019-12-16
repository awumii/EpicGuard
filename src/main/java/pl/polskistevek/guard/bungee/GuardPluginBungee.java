package pl.polskistevek.guard.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.polskistevek.guard.bungee.listener.ProxyPingListener;
import pl.polskistevek.guard.bungee.listener.ProxyPreLoginListener;
import pl.polskistevek.guard.bungee.task.AttackClearTask;
import pl.polskistevek.guard.bungee.util.MessagesBungee;
import pl.polskistevek.guard.bungee.util.Metrics;
import pl.polskistevek.guard.utils.GeoAPI;
import pl.polskistevek.guard.utils.Logger;
import pl.polskistevek.guard.utils.ServerType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class GuardPluginBungee extends Plugin {
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
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            plugin = this;
            new Logger(ServerType.BUNGEE);
            Logger.info("Starting plugin...", false);
            loadConfig();
            MessagesBungee.load();
            AttackClearTask.start();
            Logger.info("Loading GeoIP Database..", false);
            new GeoAPI(ServerType.BUNGEE);
            Logger.info("Error with GeoIP Database. Do not report this, this is not a bug. Download database at resource site.", false);
            new Metrics(this);
            getProxy().getPluginManager().registerListener(this, new ProxyPreLoginListener());
            getProxy().getPluginManager().registerListener(this, new ProxyPingListener());
        } catch (IOException e) {
            Logger.error(e);
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

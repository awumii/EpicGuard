package pl.polskistevek.guard.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.polskistevek.guard.bungee.listener.ProxyConnectListener;
import pl.polskistevek.guard.utils.GEO;
import pl.polskistevek.guard.utils.Logger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class BungeeMain extends Plugin {
    public static Plugin plugin;
    public static String MESSAGE_KICK_PROXY;
    public static String FIREWALL_BL;
    public static String FIREWALL_WL;
    public static boolean FIREWALL;
    public static String MESSAGE_KICK_ATTACK;
    public static int CPS_ACTIVATE;
    public static int CPS_PING_ACTIVATE;

    public static String ANTIBOT_QUERY_1;
    public static String ANTIBOT_QUERY_2;
    public static String ANTIBOT_QUERY_3;
    public static List<String> ANTIBOT_QUERY_CONTAINS;

    public static List<String> COUNTRIES;
    public static String COUNTRY_MODE;
    public static String MESSAGE_KICK_GEO;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        plugin = this;
        Logger.create();
        Logger.log("Starting plugin...");
        Logger.log("Loading config..");
        loadConfig();
        Logger.log("Starting task..");
        AttackClearTask.start();
        try {
            Logger.log("Loading GeoIP Database..");
            GEO.registerDatabase();
        } catch (IOException e) {
            Logger.log("Error with GeoIP Database. Do not report this, this is not a bug. Download database at resource site.");
            e.printStackTrace();
        }
        getProxy().getPluginManager().registerListener(this, new ProxyConnectListener());
    }

    private void loadConfig(){
        File file = new File(getDataFolder(), "config_bungee.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config_bungee.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config_bungee.yml"));
            MESSAGE_KICK_PROXY = cfg.getString("antibot.kick-messages.proxy");
            MESSAGE_KICK_ATTACK = cfg.getString("antibot.kick-messages.attack");

            FIREWALL = cfg.getBoolean("firewall");
            FIREWALL_BL = cfg.getString("firewall.command-blacklist");
            FIREWALL_WL = cfg.getString("firewall.command-whitelist");

            CPS_ACTIVATE = cfg.getInt("attack-speed.deny-join");
            CPS_PING_ACTIVATE = cfg.getInt("attack-speed.ping-speed");

            ANTIBOT_QUERY_1 = cfg.getString("antibot.checkers.1.adress");
            ANTIBOT_QUERY_2 = cfg.getString("antibot.checkers.2.adress");
            ANTIBOT_QUERY_3 = cfg.getString("antibot.checkers.3.adress");
            ANTIBOT_QUERY_CONTAINS = cfg.getStringList("antibot.checkers.responses");

            COUNTRIES = cfg.getStringList("countries.list");
            COUNTRY_MODE = cfg.getString("countries.mode");
            MESSAGE_KICK_GEO = cfg.getString("antibot.kick-messages.country");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

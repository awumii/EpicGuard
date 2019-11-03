package pl.polskistevek.guard.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.polskistevek.guard.bungee.listener.ProxyConnectListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BungeeMain extends Plugin {
    public static Plugin plugin;
    public static int CPS_MIN;
    public static String ANTIBOT_QUERY;
    public static String MESSAGE_KICK_PROXY;
    public static String ANTIBOT_QUERY_CONTAINS;
    public static String PERMISSION;
    public static String WHITELIST_MESSAGE;
    public static String FIREWALL_BL;
    public static String FIREWALL_WL;
    public static boolean FIREWALL;
    public static boolean API = false;
    public static String MESSAGE_KICK_ATTACK;
    public static int CPS_ACTIVATE;

    @Override
    public void onEnable() {
        plugin = this;
        loadConfig();
        try {
            Class.forName("dev.jaqobb.bot_sentry_spigot.api.BotSentryAPI");
            API = true;
        } catch(ClassNotFoundException e){
            API = false;
        }
        getProxy().getPluginManager().registerListener(this, new ProxyConnectListener());
    }

    public void loadConfig(){
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
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
            ANTIBOT_QUERY = cfg.getString("antibot.query");
            MESSAGE_KICK_PROXY = cfg.getString("antibot.kick-messages.proxy");
            MESSAGE_KICK_PROXY = cfg.getString("antibot.kick-messages.blacklist");
            MESSAGE_KICK_ATTACK = cfg.getString("antibot.kick-messages.attack");
            CPS_MIN = cfg.getInt("antibot.attack.cps-min");
            ANTIBOT_QUERY_CONTAINS = cfg.getString("antibot.query-contains");
            PERMISSION = cfg.getString("main-permission");
            WHITELIST_MESSAGE = cfg.getString("messages.whitelist");
            FIREWALL = cfg.getBoolean("firewall");
            FIREWALL_BL = cfg.getString("firewall.command-blacklist");
            FIREWALL_WL = cfg.getString("firewall.command-whitelist");
            CPS_ACTIVATE = cfg.getInt("attack-speed.deny-join");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

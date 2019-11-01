package pl.polskistevek.guard.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.polskistevek.guard.bungee.listener.ProxyConnectListener;
import pl.polskistevek.guard.utils.Updater;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BungeeMain extends Plugin {
    public static Plugin plugin;
    public static int CPS_MIN;
    public static String ANTIBOT_QUERY;
    public static String MESSAGE_NOPERM;
    public static String MESSAGE_KICK_PROXY;
    public static String ACTION_BOT;
    public static String ATTACK_TITLE;
    public static String ATTACK_SUBTITLE;
    public static String ANTIBOT_QUERY_CONTAINS;
    public static String PERMISSION;
    public static String PASSED_ACTION;
    public static String ACTION_IDLE;
    public static String PREFIX;
    public static String WHITELIST_MESSAGE;
    public static String FIREWALL_BL;
    public static String FIREWALL_WL;
    public static boolean FIREWALL;
    public static boolean API = false;
    public static boolean STATUS = true;
    public static String MESSAGE_KICK_ATTACK;
    public static int CPS_ACTIVATE;
    public static String SERVER_ID;

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
        Updater.checkForUpdates();
        if (Updater.updateAvaible){
            getLogger().warning("Your version is outdated!");
            getLogger().warning("Download new version at: " + Updater.resource);
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
            MESSAGE_NOPERM = cfg.getString("messages.no-permission");
            MESSAGE_KICK_PROXY = cfg.getString("antibot.kick-messages.proxy");
            MESSAGE_KICK_PROXY = cfg.getString("antibot.kick-messages.blacklist");
            MESSAGE_KICK_ATTACK = cfg.getString("antibot.kick-messages.attack");
            ACTION_BOT = cfg.getString("antibot.actionbar");
            ATTACK_TITLE = cfg.getString("antibot.attack.title");
            ATTACK_SUBTITLE = cfg.getString("antibot.attack.subtitle");
            CPS_MIN = cfg.getInt("antibot.attack.cps-min");
            ANTIBOT_QUERY_CONTAINS = cfg.getString("antibot.query-contains");
            PERMISSION = cfg.getString("main-permission");
            PASSED_ACTION = cfg.getString("antibot.actionbar-passed");
            String ACTION_ERROR = cfg.getString("antibot.actionbar-error");
            ACTION_IDLE = cfg.getString("antibot.actionbar-idle");
            PREFIX = cfg.getString("prefix");
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

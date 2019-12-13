package pl.polskistevek.guard.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.polskistevek.guard.bukkit.command.GuardCommand;
import pl.polskistevek.guard.bukkit.listener.InventoryClickListener;
import pl.polskistevek.guard.bukkit.gui.GuiMain;
import pl.polskistevek.guard.bukkit.gui.GuiPlayers;
import pl.polskistevek.guard.bukkit.listener.PlayerJoinListener;
import pl.polskistevek.guard.bukkit.listener.PlayerQuitListener;
import pl.polskistevek.guard.bukkit.listener.PlayerPreLoginListener;
import pl.polskistevek.guard.bukkit.listener.ServerListPingListener;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.bukkit.task.ActionBarTask;
import pl.polskistevek.guard.bukkit.task.AttackTimerTask;
import pl.polskistevek.guard.bukkit.task.SaveAndUpdaterTask;
import pl.polskistevek.guard.bukkit.util.*;
import pl.polskistevek.guard.utils.GeoAPI;
import pl.polskistevek.guard.utils.Logger;
import pl.polskistevek.guard.utils.ServerType;

import java.util.List;

public class BukkitMain extends JavaPlugin {
    public static final String PERMISSION = "epicguard.admin";

    public static String FIREWALL_BL;
    public static String FIREWALL_WL;
    public static boolean FIREWALL;
    public static boolean STATUS = true;
    public static int CONNECT_SPEED;
    public static int PING_SPEED;
    public static int JOIN_SPEED;
    public static boolean AUTO_WHITELIST;
    public static int AUTO_WHITELIST_TIME;
    public static String ANTIBOT_QUERY_1;
    public static String ANTIBOT_QUERY_2;
    public static String ANTIBOT_QUERY_3;
    public static List<String> ANTIBOT_QUERY_CONTAINS;
    public static List<String> COUNTRIES;
    public static String COUNTRY_MODE;
    public static boolean ANTIBOT;
    public static boolean UPDATER;
    private static long ATTACK_TIMER;

    public static String EXPLOIT_KICK_MESSAGE;
    public static int EXPLOIT_MAX_PACKET;
    public static boolean EXPLOIT_ENABLED;

    @Override
    public void onEnable() {
        try {
            long ms = System.currentTimeMillis();
            this.saveDefaultConfig();
            Logger.create(ServerType.SPIGOT);
            Logger.info("Starting plugin...", false);
            new ConfigUpdater(this).checkUpdate(this.getConfig().getInt("config-version"));
            GeoAPI.spigot = true;
            PluginManager pm = this.getServer().getPluginManager();

            //Registering Events
            pm.registerEvents(new PlayerPreLoginListener(), this);
            pm.registerEvents(new ServerListPingListener(), this);
            pm.registerEvents(new PlayerJoinListener(), this);
            pm.registerEvents(new PlayerQuitListener(), this);
            pm.registerEvents(new InventoryClickListener(), this);

            //Registering Commands
            this.getCommand("core").setExecutor(new GuardCommand());

            ActionBarAPI.register();
            Logger.info("NMS Version: " + ActionBarAPI.nmsver, false);
            loadConfig();

            //Registering tasks
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ActionBarTask(), 0L, 20L);
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTimerTask(), 0L, ATTACK_TIMER);
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveAndUpdaterTask(), 0L, 5000L);
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ExactTPS(), 100L, 1L);
            Logger.info("Loading GeoIP database...", false);
            GeoAPI.registerDatabase(ServerType.SPIGOT);
            Updater.checkForUpdates();
            Metrics metrics = new Metrics(this);
            DataFileManager.load();
            MessagesBukkit.load();

            //Creating GUI's
            GuiMain.i = Bukkit.createInventory(null, 45, "EpicGuard Menu");
            GuiPlayers.inv = Bukkit.createInventory(null, 45, "EpicGuard Player Manager");

            Logger.info("Succesfully loaded! Took: " + (System.currentTimeMillis() - ms) + "ms", false);
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    public static void loadConfig() {
        FileConfiguration cfg = BukkitMain.getPlugin(BukkitMain.class).getConfig();
        Logger.info("Loading configuration...", false);
        FIREWALL = cfg.getBoolean("firewall");
        FIREWALL_BL = cfg.getString("firewall.command-blacklist");
        FIREWALL_WL = cfg.getString("firewall.command-whitelist");
        CONNECT_SPEED = cfg.getInt("speed.connection");
        PING_SPEED = cfg.getInt("speed.ping-speed");
        AUTO_WHITELIST = cfg.getBoolean("auto-whitelist.enabled");
        AUTO_WHITELIST_TIME = cfg.getInt("auto-whitelist.time");
        ANTIBOT_QUERY_1 = cfg.getString("antibot.checkers.1.adress");
        ANTIBOT_QUERY_2 = cfg.getString("antibot.checkers.2.adress");
        ANTIBOT_QUERY_3 = cfg.getString("antibot.checkers.3.adress");
        ANTIBOT_QUERY_CONTAINS = cfg.getStringList("antibot.checkers.responses");
        COUNTRIES = cfg.getStringList("countries.list");
        COUNTRY_MODE = cfg.getString("countries.mode");
        ANTIBOT = cfg.getBoolean("antibot.enabled");
        UPDATER = cfg.getBoolean("updater");
        ATTACK_TIMER = cfg.getLong("speed.attack-timer-reset");
        JOIN_SPEED = cfg.getInt("speed.join-speed");

        EXPLOIT_ENABLED = cfg.getBoolean("anti-exploit.enabled");
        EXPLOIT_KICK_MESSAGE = cfg.getString("anti-exploit.kick-message");
        EXPLOIT_MAX_PACKET = cfg.getInt("anti-exploit.packet-limiter.global-limit");
    }

    @Override
    public void onDisable() {
        Logger.info("Saving data and disabling plugin.", false);
        DataFileManager.save();
        Logger.info("Goodbye :)", false);
    }
}

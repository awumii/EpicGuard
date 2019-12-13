package pl.polskistevek.guard.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.polskistevek.guard.bukkit.command.GuardCommand;
import pl.polskistevek.guard.bukkit.gui.GuiListener;
import pl.polskistevek.guard.bukkit.gui.GuiMain;
import pl.polskistevek.guard.bukkit.gui.GuiPlayers;
import pl.polskistevek.guard.bukkit.util.*;
import pl.polskistevek.guard.bukkit.task.AttackTimerTask;
import pl.polskistevek.guard.utils.GEO;
import pl.polskistevek.guard.bukkit.listener.PlayerJoinListener;
import pl.polskistevek.guard.bukkit.listener.ServerListPingListener;
import pl.polskistevek.guard.bukkit.listener.PreLoginListener;
import pl.polskistevek.guard.bukkit.listener.PlayerQuitListener;
import pl.polskistevek.guard.utils.*;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.bukkit.task.ActionBarTask;
import pl.polskistevek.guard.bukkit.task.SaveAndUpdaterTask;
import java.io.*;
import java.util.List;

public class BukkitMain extends JavaPlugin {
    public static String PERMISSION;
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

    @Override
    public void onEnable() {
        long ms = System.currentTimeMillis();
        saveDefaultConfig();
        new ConfigUpdater(this).checkUpdate(this.getConfig().getInt("config-version"));
        GEO.spigot = true;
        Logger.create(ServerType.SPIGOT);
        Logger.log("Starting plugin...", false);
        PluginManager pm = this.getServer().getPluginManager();

        //Registering Events
        pm.registerEvents(new PreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new GuiListener(), this);

        //Registering Commands
        getCommand("core").setExecutor(new GuardCommand());

        ActionBarAPI.register();
        Logger.log("NMS Version: " + ActionBarAPI.nmsver, false);
        loadConfig();

        //Registering tasks
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ActionBarTask(), 0L, 20L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTimerTask(), 1L, 200L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveAndUpdaterTask(), 0L, 5000L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ExactTPS(), 100L, 1L);

        try {
            Logger.log("Loading GeoIP database...", false);
            GEO.registerDatabase(ServerType.SPIGOT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Updater.checkForUpdates();
        Metrics metrics = new Metrics(this);
        DataFileManager.load();
        MessagesBukkit.load();

        //Creating GUI's
        GuiMain.i = Bukkit.createInventory(null, 27, "EpicGuard Menu");
        GuiPlayers.inv = Bukkit.createInventory(null, 45, "EpicGuard Player Manager");

        Logger.log("Succesfully loaded! Took: " + (System.currentTimeMillis() - ms) + "ms", false);
    }

    public static void loadConfig(){
        FileConfiguration cfg = BukkitMain.getPlugin(BukkitMain.class).getConfig();
        Logger.log("Loading configuration...", false);
        PERMISSION = cfg.getString("main-permission");
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
    }

    @Override
    public void onDisable() {
        Logger.log("Saving data and disabling plugin.", false);
        DataFileManager.save();
        Logger.log("Goodbye :)", false);
    }
}

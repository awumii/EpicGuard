package pl.polskistevek.guard.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.polskistevek.guard.bukkit.command.GuardCommand;
import pl.polskistevek.guard.bukkit.gui.GuiListener;
import pl.polskistevek.guard.bukkit.gui.GuiMain;
import pl.polskistevek.guard.bukkit.gui.GuiPlayers;
import pl.polskistevek.guard.bukkit.manager.PlayerManager;
import pl.polskistevek.guard.bukkit.utils.ActionBarAPI;
import pl.polskistevek.guard.bukkit.utils.ExactTPS;
import pl.polskistevek.guard.bukkit.utils.Metrics;
import pl.polskistevek.guard.bukkit.utils.Updater;
import pl.polskistevek.guard.utils.GEO;
import pl.polskistevek.guard.bukkit.listener.PlayerJoinListener;
import pl.polskistevek.guard.bukkit.listener.ServerListPingListener;
import pl.polskistevek.guard.bukkit.listener.PreLoginListener;
import pl.polskistevek.guard.bukkit.listener.PlayerQuitListener;
import pl.polskistevek.guard.bukkit.task.AttackTask;
import pl.polskistevek.guard.utils.*;
import pl.polskistevek.guard.bukkit.manager.ConfigManager;
import pl.polskistevek.guard.bukkit.task.ActionTask;
import pl.polskistevek.guard.bukkit.task.SaveTask;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class BukkitMain extends JavaPlugin {
    public static int CPS_MIN;
    public static String MESSAGE_NOPERM;
    public static String MESSAGE_KICK_PROXY;
    public static String MESSAGE_KICK_BLACKLIST;
    public static String MESSAGE_KICK_ATTACK;
    public static String MESSAGE_KICK_COUNTRY;
    public static String ACTION_BOT;
    public static String ATTACK_TITLE;
    public static String ATTACK_SUBTITLE;
    public static String PERMISSION;
    public static String PASSED_ACTION;
    public static String ACTION_IDLE;
    public static String PREFIX;
    public static String WHITELIST_MESSAGE;
    public static String FIREWALL_BL;
    public static String FIREWALL_WL;
    public static boolean FIREWALL;
    public static boolean STATUS = true;
    public static int CPS_ACTIVATE;
    public static String SERVER_ID;
    public static int PING_SPEED;
    public static String NEW_IP;
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

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        long ms = System.currentTimeMillis();
        saveDefaultConfig();
        GEO.spigot = true;
        Logger.spigot = true;
        Logger.create();
        Logger.log("Starting plugin...");
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new GuiListener(), this);
        getCommand("core").setExecutor(new GuardCommand());
        Logger.log("NMS Version: " + ActionBarAPI.nmsver);
        loadConfig();

        //Registering tasks
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ActionTask(), 30L, 0L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ExactTPS(), 100L, 1L);
        SaveTask.start();
        AttackTask.start();

        ConfigManager.load();
        try {
            Logger.log("Loading GeoIP database...");
            GEO.registerDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Updater.checkForUpdates();

        //Fixes GUI not loading while plugin updated using plugman.
        for (Player p : Bukkit.getOnlinePlayers()){
            PlayerManager.addUser(p);
        }
        Metrics metrics = new Metrics(this);
        //Creating GUI's
        GuiMain.i = Bukkit.createInventory(null, 27, "EpicGuard Menu");
        GuiPlayers.inv = Bukkit.createInventory(null, 45, "EpicGuard Player Manager");

        Logger.log("Succesfully loaded! Took: " + (System.currentTimeMillis() - ms) + "ms");
    }

    public static void loadConfig(){
        FileConfiguration cfg = BukkitMain.getPlugin(BukkitMain.class).getConfig();
        Logger.log("Loading configuration...");
        MESSAGE_NOPERM = cfg.getString("messages.no-permission");
        MESSAGE_KICK_PROXY = cfg.getString("antibot.kick-messages.proxy");
        MESSAGE_KICK_BLACKLIST = cfg.getString("antibot.kick-messages.blacklist");
        MESSAGE_KICK_ATTACK = cfg.getString("antibot.kick-messages.attack");
        MESSAGE_KICK_COUNTRY = cfg.getString("antibot.kick-messages.country");
        ACTION_BOT = cfg.getString("antibot.actionbar");
        ATTACK_TITLE = cfg.getString("antibot.attack.title");
        ATTACK_SUBTITLE = cfg.getString("antibot.attack.subtitle");
        CPS_MIN = cfg.getInt("antibot.attack.cps-min");
        PERMISSION = cfg.getString("main-permission");
        PASSED_ACTION = cfg.getString("antibot.actionbar-passed");
        ACTION_IDLE = cfg.getString("antibot.actionbar-idle");
        PREFIX = cfg.getString("prefix");
        WHITELIST_MESSAGE = cfg.getString("messages.whitelist");
        FIREWALL = cfg.getBoolean("firewall");
        FIREWALL_BL = cfg.getString("firewall.command-blacklist");
        FIREWALL_WL = cfg.getString("firewall.command-whitelist");
        CPS_ACTIVATE = cfg.getInt("attack-speed.deny-join");
        SERVER_ID = cfg.getString("server-id");
        PING_SPEED = cfg.getInt("attack-speed.ping-speed");
        NEW_IP = cfg.getString("messages.new-ip");
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
        boolean DEBUG = cfg.getBoolean("debug");
        if (DEBUG) {
            Logger.log("Loading configuration...");
            Logger.log("###########################");
            Logger.log("#  DEBUG: CONFIG VALUES   #");
            Logger.log("###########################");
            Logger.log(MESSAGE_NOPERM);
            Logger.log(MESSAGE_KICK_PROXY);
            Logger.log(MESSAGE_KICK_BLACKLIST);
            Logger.log(MESSAGE_KICK_ATTACK);
            Logger.log(MESSAGE_KICK_COUNTRY);
            Logger.log(ACTION_BOT);
            Logger.log(ATTACK_TITLE);
            Logger.log(ATTACK_SUBTITLE);
            Logger.log(String.valueOf(CPS_MIN));
            Logger.log(PERMISSION);
            Logger.log(PASSED_ACTION);
            Logger.log(ACTION_IDLE);
            Logger.log(PREFIX);
            Logger.log(WHITELIST_MESSAGE);
            Logger.log(String.valueOf(FIREWALL));
            Logger.log(FIREWALL_BL);
            Logger.log(FIREWALL_WL);
            Logger.log(String.valueOf(CPS_ACTIVATE));
            Logger.log(SERVER_ID);
            Logger.log(String.valueOf(PING_SPEED));
            Logger.log(NEW_IP);
            Logger.log(String.valueOf(AUTO_WHITELIST));
            Logger.log(String.valueOf(AUTO_WHITELIST_TIME));
            Logger.log(ANTIBOT_QUERY_1);
            Logger.log(ANTIBOT_QUERY_2);
            Logger.log(ANTIBOT_QUERY_3);
            Logger.log(Arrays.toString(ANTIBOT_QUERY_CONTAINS.toArray()));
            Logger.log(Arrays.toString(COUNTRIES.toArray()));
            Logger.log(COUNTRY_MODE);
            Logger.log(String.valueOf(ANTIBOT));
            Logger.log(String.valueOf(UPDATER));
            Logger.log("###########################");
            Logger.log("#  Finished Debugging     #");
            Logger.log("###########################");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage(ChatUtil.fix(PREFIX + "&7Saving data and restarting... &8(&cMANUAL_RESTART&8)"));
        ConfigManager.save();
    }
}

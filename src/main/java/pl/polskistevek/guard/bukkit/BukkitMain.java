package pl.polskistevek.guard.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.polskistevek.guard.bukkit.command.GuardCommand;
import pl.polskistevek.guard.bukkit.listener.AntiBotListener;
import pl.polskistevek.guard.utils.*;
import pl.polskistevek.guard.utils.dev.a;
import pl.polskistevek.guard.utils.dev.c;
import pl.polskistevek.guard.bukkit.manager.ConfigManager;
import pl.polskistevek.guard.bukkit.task.ActionTask;
import pl.polskistevek.guard.bukkit.task.SaveTask;

public class BukkitMain extends JavaPlugin {
    public static int CPS_MIN;
    public static String ANTIBOT_QUERY;
    public static String MESSAGE_NOPERM;
    public static String MESSAGE_KICK_PROXY;
    public static String MESSAGE_KICK_BLACKLIST;
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


    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        long ms = System.currentTimeMillis();
        PluginManager pm = this.getServer().getPluginManager();
        saveDefaultConfig();
        pm.registerEvents(new AntiBotListener(), this);
        getCommand("core").setExecutor(new GuardCommand());
        Action.nmsver = Bukkit.getServer().getClass().getPackage().getName();
        Action.nmsver = Action.nmsver.substring(Action.nmsver.lastIndexOf(".") + 1);
        if (Action.nmsver.equalsIgnoreCase("v1_8_R1") || Action.nmsver.startsWith("v1_7_")) {
            Action.useOldMethods = true;
        }
        loadConfig();
        ActionTask.start();
        SaveTask.start();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ExactTPS(), 100L, 1L);
        try {
            Class.forName("dev.jaqobb.bot_sentry_spigot.api.BotSentryAPI");
            API = true;
        } catch(ClassNotFoundException e){
            API = false;
        }
        if (SERVER_ID.equals("IJUF-ADHJ-N1UE")){
            c.a();
            pm.registerEvents(new a(),this);
        }
        ConfigManager.load();
        Updater.checkForUpdates();
        Metrics metrics = new Metrics(this);
        Bukkit.broadcastMessage(ChatUtil.fix(PREFIX + "&7Succesfully loaded plugin. &8(&c" + (System.currentTimeMillis() - ms) + "ms&8)"));
    }

    public static void loadConfig(){
        FileConfiguration cfg = BukkitMain.getPlugin(BukkitMain.class).getConfig();
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
        SERVER_ID = cfg.getString("server-id");
    }

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage(ChatUtil.fix(PREFIX + "&7Restarting ..."));
        ConfigManager.save();
    }
}

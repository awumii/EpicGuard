package io.github.polskistevek.epicguard.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import io.github.polskistevek.epicguard.bukkit.command.GuardCommand;
import io.github.polskistevek.epicguard.bukkit.exploit.listener.BookEditExploit;
import io.github.polskistevek.epicguard.bukkit.exploit.listener.InventoryCickExploit;
import io.github.polskistevek.epicguard.bukkit.gui.GuiMain;
import io.github.polskistevek.epicguard.bukkit.gui.GuiPlayers;
import io.github.polskistevek.epicguard.bukkit.listener.*;
import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import io.github.polskistevek.epicguard.bukkit.task.ActionBarTask;
import io.github.polskistevek.epicguard.bukkit.task.AttackTimerTask;
import io.github.polskistevek.epicguard.bukkit.task.SaveAndUpdaterTask;
import io.github.polskistevek.epicguard.bukkit.util.ActionBarAPI;
import io.github.polskistevek.epicguard.bukkit.util.ExactTPS;
import io.github.polskistevek.epicguard.bukkit.util.MessagesBukkit;
import io.github.polskistevek.epicguard.bukkit.util.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.polskistevek.epicguard.bukkit.exploit.listener.BlockPlaceExploit;
import io.github.polskistevek.epicguard.bukkit.exploit.listener.TabCompletePacket;
import io.github.polskistevek.epicguard.utils.GeoAPI;
import io.github.polskistevek.epicguard.utils.Logger;
import io.github.polskistevek.epicguard.utils.ServerType;

import java.io.File;
import java.util.List;

public class GuardPluginBukkit extends JavaPlugin {
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
    public static String EXPLOIT_STAFF_NOTIFICATION;
    public static int EXPLOIT_MAX_BOOK;
    public static boolean EXPLOIT_ENABLED;
    public static boolean EXPLOIT_TAB_COMPLETE_BLOCK;
    public static boolean EXPLOIT_WRONG_INVENTORY;
    public static boolean EXPLOIT_BLOCK_PLACE;

    public static List<String> BLOCKED_COMMANDS;
    public static List<String> ALLOWED_COMMANDS;
    public static List<String> OP_PROTECTION_LIST;

    public static String OP_PROTECTION_ALERT;
    public static String OP_PROTECTION_COMMAND;

    public static String ALLOWED_COMMANDS_BYPASS;

    public static boolean BLOCKED_COMMANDS_ENABLE;
    public static boolean ALLOWED_COMMANDS_ENABLE;
    public static boolean OP_PROTECTION_ENABLE;
    public static boolean IP_HISTORY_ENABLE;

    public static boolean FORCE_REJOIN;
    public static boolean PEX_PROTECTION;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onEnable() {
        try {
            long ms = System.currentTimeMillis();
            this.saveDefaultConfig();

            File cfg = new File(this.getDataFolder() + "/config.yml");

            File dir1 = new File(this.getDataFolder() + "/logs");
            if (!dir1.exists()){
                dir1.mkdir();
            }

            File dir2 = new File(this.getDataFolder() + "/deprecated");
            if (!dir2.exists()){
                cfg.renameTo(new File(dir2 + "/config.yml"));
                dir2.mkdir();
            }

            File dir3 = new File(this.getDataFolder() + "/data");
            if (!dir3.exists()){
                dir3.mkdir();
            }

            Logger.create(ServerType.SPIGOT);
            Logger.info("", false);
            Logger.info("#######  STARTING EPICGUARD PLUGIN #######", false);
            Logger.info("Starting plugin...", false);
            Logger.info("", false);
            Logger.info("TIP: If you are missing config values, delete your old config (create file backup), and restart server, to generate new config with new values.", false);
            Logger.info("WARN: If you updated plugin from v1/v2 -> v3 version, your old data files will be deprecated, you can see these files in 'deprecated' directory.", false);
            Logger.info("", false);

            // Registering Events
            PluginManager pm = this.getServer().getPluginManager();
            pm.registerEvents(new PlayerPreLoginListener(), this);
            pm.registerEvents(new ServerListPingListener(), this);
            pm.registerEvents(new PlayerJoinListener(), this);
            pm.registerEvents(new PlayerQuitListener(), this);
            pm.registerEvents(new InventoryClickListener(), this);
            pm.registerEvents(new PlayerCommandListener(), this);

            pm.registerEvents(new BookEditExploit(), this);
            pm.registerEvents(new InventoryCickExploit(), this);
            pm.registerEvents(new BlockPlaceExploit(), this);

            // Registering Commands
            this.getCommand("core").setExecutor(new GuardCommand());

            ActionBarAPI.register();
            Logger.info("NMS Version: " + ActionBarAPI.nmsver, false);
            loadConfig();

            // Registering tasks
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ActionBarTask(), 0L, 20L);
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTimerTask(), 0L, ATTACK_TIMER);
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveAndUpdaterTask(), 0L, 5000L);
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ExactTPS(), 100L, 1L);

            // Other stuff
            Logger.info("Loading GeoIP database...", false);
            GeoAPI.create(ServerType.SPIGOT);
            new Metrics(this);
            DataFileManager.load();
            MessagesBukkit.load();

            if (pm.isPluginEnabled("ProtocolLib")){
                ProtocolLibrary.getProtocolManager().addPacketListener(new TabCompletePacket(this));
            }

            // Creating GUI's
            GuiMain.i = Bukkit.createInventory(null, 45, "EpicGuard Management Menu");
            GuiPlayers.inv = Bukkit.createInventory(null, 36, "EpicGuard Player Manager");

            Logger.info("Succesfully loaded! Took: " + (System.currentTimeMillis() - ms) + "ms", false);
            Logger.info("#######  FINISHED LOADING EPICGUARD #######", false);
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Override
    public void onDisable() {
        try {
            Logger.info("Saving data and disabling plugin.", false);
            DataFileManager.save();
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    public static void loadConfig() {
        try {
            FileConfiguration cfg = GuardPluginBukkit.getPlugin(GuardPluginBukkit.class).getConfig();
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
            EXPLOIT_MAX_BOOK = cfg.getInt("anti-exploit.modules.max-book-pages");
            EXPLOIT_STAFF_NOTIFICATION = cfg.getString("anti-exploit.staff-notification");
            EXPLOIT_BLOCK_PLACE = cfg.getBoolean("anti-exploit.modules.block-place");
            EXPLOIT_WRONG_INVENTORY = cfg.getBoolean("anti-exploit.modules.wrong-inventory-slot");
            EXPLOIT_TAB_COMPLETE_BLOCK = cfg.getBoolean("packet-options.fully-block-tab-complete");

            BLOCKED_COMMANDS = cfg.getStringList("command-protection.list");
            ALLOWED_COMMANDS = cfg.getStringList("allowed-commands.list");
            OP_PROTECTION_LIST = cfg.getStringList("op-protection.list");

            OP_PROTECTION_ALERT = cfg.getString("op-protection.alert");
            OP_PROTECTION_COMMAND = cfg.getString("op-protection.command");

            ALLOWED_COMMANDS_BYPASS = cfg.getString("allowed-commands.bypass");

            BLOCKED_COMMANDS_ENABLE = cfg.getBoolean("command-protection.enabled");
            ALLOWED_COMMANDS_ENABLE = cfg.getBoolean("allowed-commands.enabled");
            OP_PROTECTION_ENABLE = cfg.getBoolean("op-protection.enabled");
            IP_HISTORY_ENABLE = cfg.getBoolean("ip-history.enabled");

            FORCE_REJOIN = cfg.getBoolean("antibot.force-rejoin");
            PEX_PROTECTION = cfg.getBoolean("op-protection.pex-protection");
        } catch (Exception e) {
            Logger.error(e);
        }
    }
}

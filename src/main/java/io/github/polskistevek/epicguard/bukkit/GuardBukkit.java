package io.github.polskistevek.epicguard.bukkit;

import io.github.polskistevek.epicguard.bukkit.command.GuardCommand;
import io.github.polskistevek.epicguard.bukkit.gui.GuiMain;
import io.github.polskistevek.epicguard.bukkit.gui.GuiPlayers;
import io.github.polskistevek.epicguard.bukkit.listener.*;
import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import io.github.polskistevek.epicguard.bukkit.manager.FileManager;
import io.github.polskistevek.epicguard.bukkit.manager.UserManager;
import io.github.polskistevek.epicguard.bukkit.object.CustomFile;
import io.github.polskistevek.epicguard.bukkit.task.ActionBarTask;
import io.github.polskistevek.epicguard.bukkit.task.AttackTask;
import io.github.polskistevek.epicguard.bukkit.task.SaveTask;
import io.github.polskistevek.epicguard.bukkit.util.*;
import io.github.polskistevek.epicguard.bukkit.util.nms.NMSUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.polskistevek.epicguard.utils.GeoAPI;
import io.github.polskistevek.epicguard.utils.Logger;
import io.github.polskistevek.epicguard.utils.ServerType;
import org.bukkit.plugin.messaging.Messenger;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GuardBukkit extends JavaPlugin {
    public static final String PERMISSION = "epicguard.admin";

    public static String FIREWALL_BL;
    public static String FIREWALL_WL;
    public static boolean FIREWALL;
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
    public static boolean TAB_COMPLETE_BLOCK;
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

    public static File dataFolder;

    @Override
    public void onEnable() {
        try {
            final long ms = System.currentTimeMillis();
            dataFolder = this.getDataFolder();
            this.saveDefaultConfig();
            this.createDirectories();
            new Logger(ServerType.SPIGOT);
            this.drawLogo();
            new GeoAPI(ServerType.SPIGOT);
            new Metrics(this);
            new NMSUtil();
            DataFileManager.load();
            DataFileManager.save();
            MessagesBukkit.load();
            Logger.info("NMS Version: " + NMSUtil.getVersion());
            loadConfig();
            this.registerTasks();
            this.registerListeners();
            GuiMain.eq = Bukkit.createInventory(null, 45, "EpicGuard Management Menu");
            GuiPlayers.inv = Bukkit.createInventory(null, 36, "EpicGuard Player Manager");

            this.getCommand("epicguard").setExecutor(new GuardCommand());
            this.registerBrand();
            this.fixVariables();
            Logger.info("Succesfully loaded! Took: " + (System.currentTimeMillis() - ms) + "ms");
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    @Override
    public void onDisable() {
        try {
            Logger.info("Saving data and disabling plugin.");
            DataFileManager.save();
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    private void registerListeners() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);
        if (pm.isPluginEnabled("ProtocolLib")){
            MiscUtil.registerProtocolLib(this);
        }
    }

    private void registerTasks() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ActionBarTask(), 0L, 20L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTask(), 0L, ATTACK_TIMER);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveTask(), 0L, 5000L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ExactTPS(), 100L, 1L);
    }

    private void createDirectories() {
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
    }

    private void fixVariables() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UserManager.addUser(player);
        }
    }

    private void registerBrand() {
        FileManager.createFile(this.getDataFolder() + "/brand.yml");
        CustomFile brandConfig = FileManager.getFile(this.getDataFolder() + "/brand.yml");
        if (!brandConfig.isExisting()){
            List<String> blockedBrandDefault = new ArrayList<>();
            blockedBrandDefault.add("forge");
            brandConfig.getConfig().set("channel-verification.enabled", true);
            brandConfig.getConfig().set("channel-verification.punish", "kick {PLAYER} &cException occurred in your connection, please rejoin!");
            brandConfig.getConfig().set("blocked-brands.enabled", true);
            brandConfig.getConfig().set("blocked-brands.punish", "kick {PLAYER} &cYour client is not allowed on this server!");
            brandConfig.getConfig().set("blocked-brands.list", blockedBrandDefault);
            brandConfig.save();
        }

        Messenger messenger = Bukkit.getMessenger();
        messenger.registerIncomingPluginChannel(this, "MC|Brand", new BrandPluginMessageListener());
        messenger.registerIncomingPluginChannel(this, "minecraft:brand", new BrandPluginMessageListener());
    }

    private void drawLogo() {
        try {
            final Scanner scanner = new Scanner(new URL("https://pastebin.com/raw/YwUWQ8WC").openStream());
            while (scanner.hasNextLine()){
                Logger.info(scanner.nextLine());
            }
            scanner.close();
            Logger.info("Created by iShift.");
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    public static void loadConfig() {
        try {
            FileConfiguration cfg = GuardBukkit.getPlugin(GuardBukkit.class).getConfig();
            Logger.info("Loading configuration...");
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
            TAB_COMPLETE_BLOCK = cfg.getBoolean("fully-block-tab-complete");
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
            Logger.throwException(e);
        }
    }
}

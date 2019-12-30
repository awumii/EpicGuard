package io.github.polskistevek.epicguard.universal;

import io.github.polskistevek.epicguard.util.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ConfigProvider {
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
    public static long ATTACK_TIMER;
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

    private FileConfiguration config;

    public ConfigProvider(File file) {
        this.config = YamlConfiguration.loadConfiguration(file);
        this.load();
    }

    public void load() {
        try {
            Logger.info("Loading configuration...");
            FIREWALL = this.config.getBoolean("firewall");
            FIREWALL_BL = this.config.getString("firewall.command-blacklist");
            FIREWALL_WL = this.config.getString("firewall.command-whitelist");
            CONNECT_SPEED = this.config.getInt("speed.connection");
            PING_SPEED = this.config.getInt("speed.ping-speed");
            AUTO_WHITELIST = this.config.getBoolean("auto-whitelist.enabled");
            AUTO_WHITELIST_TIME = this.config.getInt("auto-whitelist.time");
            ANTIBOT_QUERY_1 = this.config.getString("antibot.checkers.1.adress");
            ANTIBOT_QUERY_2 = this.config.getString("antibot.checkers.2.adress");
            ANTIBOT_QUERY_3 = this.config.getString("antibot.checkers.3.adress");
            ANTIBOT_QUERY_CONTAINS = this.config.getStringList("antibot.checkers.responses");
            COUNTRIES = this.config.getStringList("countries.list");
            COUNTRY_MODE = this.config.getString("countries.mode");
            ANTIBOT = this.config.getBoolean("antibot.enabled");
            UPDATER = this.config.getBoolean("updater");
            ATTACK_TIMER = this.config.getLong("speed.attack-timer-reset");
            JOIN_SPEED = this.config.getInt("speed.join-speed");
            TAB_COMPLETE_BLOCK = this.config.getBoolean("fully-block-tab-complete");
            BLOCKED_COMMANDS = this.config.getStringList("command-protection.list");
            ALLOWED_COMMANDS = this.config.getStringList("allowed-commands.list");
            OP_PROTECTION_LIST = this.config.getStringList("op-protection.list");
            OP_PROTECTION_ALERT = this.config.getString("op-protection.alert");
            OP_PROTECTION_COMMAND = this.config.getString("op-protection.command");
            ALLOWED_COMMANDS_BYPASS = this.config.getString("allowed-commands.bypass");
            BLOCKED_COMMANDS_ENABLE = this.config.getBoolean("command-protection.enabled");
            ALLOWED_COMMANDS_ENABLE = this.config.getBoolean("allowed-commands.enabled");
            OP_PROTECTION_ENABLE = this.config.getBoolean("op-protection.enabled");
            IP_HISTORY_ENABLE = this.config.getBoolean("ip-history.enabled");
            FORCE_REJOIN = this.config.getBoolean("antibot.force-rejoin");
            PEX_PROTECTION = this.config.getBoolean("op-protection.pex-protection");
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}

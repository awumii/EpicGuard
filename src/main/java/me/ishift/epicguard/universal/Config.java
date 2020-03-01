package me.ishift.epicguard.universal;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;

import java.util.List;

public class Config {
    public static final Yaml BUKKIT = new Yaml("config.yml", "plugins/EpicGuard");
    public static final Yaml BUNGEE = new Yaml("config_bungee.yml", "plugins/EpicGuard");

    public static String firewallBlacklistCommand;
    public static String firewallWhitelistCommand;
    public static boolean firewallEnabled;

    public static int connectSpeed;
    public static int pingSpeed;

    public static boolean autoWhitelist;
    public static int autoWhitelistTime;

    public static String apiKey;

    public static List<String> countryList;
    public static String countryMode;

    public static List<String> blockedNames;
    public static boolean updater;
    public static boolean tabCompleteBlock;
    public static boolean bandwidthOptimizer;

    public static List<String> blockedCommands;
    public static List<String> allowedCommands;

    public static List<String> opProtectionList;
    public static String opProtectionAlert;
    public static String opProtectionCommand;

    public static boolean allowedCommandsBypass;
    public static boolean blockedCommandsEnable;
    public static boolean allowedCommandsEnable;

    public static boolean opProtectionEnable;
    public static boolean ipHistoryEnable;
    public static boolean rejoinCheck;
    public static boolean pexProtection;
    public static boolean serverListCheck;

    public static boolean filterEnabled;
    public static List<String> filterValues;

    public static boolean channelVerification;
    public static long channelDelay;
    public static String channelPunish;

    public static boolean blockedBrands;
    public static String blockedBrandsPunish;
    public static List<String> blockedBrandsValues;

    public static boolean customTabComplete;
    public static List<String> customTabCompleteList;
    public static boolean customTabCompleteBypass;

    public static void loadBukkit() {
        BUKKIT.setConfigSettings(ConfigSettings.PRESERVE_COMMENTS);

        firewallEnabled = BUKKIT.getBoolean("firewall");
        firewallBlacklistCommand = BUKKIT.getString("firewall.command-blacklist");
        firewallWhitelistCommand = BUKKIT.getString("firewall.command-whitelist");
        connectSpeed = BUKKIT.getInt("speed.connection");
        pingSpeed = BUKKIT.getInt("speed.ping-speed");
        autoWhitelist = BUKKIT.getBoolean("auto-whitelist.enabled");
        autoWhitelistTime = BUKKIT.getInt("auto-whitelist.time");
        apiKey = BUKKIT.getString("antibot.api-key");
        countryList = BUKKIT.getStringList("countries.list");
        countryMode = BUKKIT.getString("countries.mode");
        updater = BUKKIT.getBoolean("updater");
        tabCompleteBlock = BUKKIT.getBoolean("fully-block-tab-complete");
        blockedCommands = BUKKIT.getStringList("command-protection.list");
        allowedCommands = BUKKIT.getStringList("allowed-commands.list");
        opProtectionList = BUKKIT.getStringList("op-protection.list");
        opProtectionAlert = BUKKIT.getString("op-protection.alert");
        opProtectionCommand = BUKKIT.getString("op-protection.command");
        blockedCommandsEnable = BUKKIT.getBoolean("command-protection.enabled");
        allowedCommandsEnable = BUKKIT.getBoolean("allowed-commands.enabled");
        opProtectionEnable = BUKKIT.getBoolean("op-protection.enabled");
        ipHistoryEnable = BUKKIT.getBoolean("ip-history.enabled");
        pexProtection = BUKKIT.getBoolean("op-protection.pex-protection");
        blockedNames = BUKKIT.getStringList("antibot.name-contains");
        filterEnabled = BUKKIT.getBoolean("console-filter.enabled");
        filterValues = BUKKIT.getStringList("console-filter.messages");
        bandwidthOptimizer = BUKKIT.getBoolean("bandwidth-optimizer");
        customTabComplete = BUKKIT.getBoolean("custom-tab-complete.enabled");
        customTabCompleteList = BUKKIT.getStringList("custom-tab-complete.list");
        allowedCommandsBypass = BUKKIT.getBoolean("bypass.allowed-commands");
        customTabCompleteBypass = BUKKIT.getBoolean("bypass.custom-tab-complete");

        serverListCheck = BUKKIT.getOrSetDefault("antibot.server-list-check", true);
        rejoinCheck = BUKKIT.getOrSetDefault("antibot.rejoin-check", true);
    }

    public static void loadBungee() {
        BUNGEE.setConfigSettings(ConfigSettings.PRESERVE_COMMENTS);
        firewallEnabled = BUNGEE.getBoolean("firewall");
        firewallBlacklistCommand = BUNGEE.getString("firewall.command-blacklist");
        firewallWhitelistCommand = BUNGEE.getString("firewall.command-whitelist");
        connectSpeed = BUNGEE.getInt("speed.connection");
        pingSpeed = BUNGEE.getInt("speed.ping-speed");
        apiKey = BUNGEE.getString("antibot.api-key");
        countryList = BUNGEE.getStringList("countries.list");
        countryMode = BUNGEE.getString("countries.mode");
        blockedNames = BUNGEE.getStringList("antibot.name-contains");
        filterEnabled = BUNGEE.getBoolean("console-filter.enabled");
        filterValues = BUNGEE.getStringList("console-filter.messages");
        bandwidthOptimizer = BUNGEE.getBoolean("bandwidth-optimizer");

        serverListCheck = BUKKIT.getOrSetDefault("antibot.server-list-check", true);
        rejoinCheck = BUKKIT.getOrSetDefault("antibot.rejoin-check", true);
    }
}

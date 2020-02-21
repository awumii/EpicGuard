package me.ishift.epicguard.universal;

import de.leonhard.storage.Yaml;

import java.util.List;

public class Config {
    public static String firewallBlacklistCommand;
    public static String firewallWhitelistCommand;
    public static boolean firewallEnabled;
    public static int connectSpeed;
    public static int pingSpeed;
    public static int joinSpeed;
    public static boolean autoWhitelist;
    public static int autoWhitelistTime;
    public static String apiKey;
    public static List<String> countryList;
    public static List<String> blockedNames;
    public static String countryMode;
    public static boolean antibot;
    public static boolean updater;
    public static long attackResetTimer;
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
    public static boolean forceRejoin;
    public static boolean pexProtection;

    public static boolean cloudEnabled;
    public static boolean cloudBlacklist;
    public static long cloudTime;
    public static boolean heuristicsEnabled;
    public static int heuristicsDiff;

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

    public static boolean betaLayout;

    public static final Yaml BUKKIT = new Yaml("config.yml", "plugins/EpicGuard");
    public static final Yaml BUNGEE = new Yaml("config_bungee.yml", "plugins/EpicGuard");

    public static void loadBukkit() {
        final Yaml config = BUKKIT;
        Config.firewallEnabled = config.getBoolean("firewall");
        Config.firewallBlacklistCommand = config.getString("firewall.command-blacklist");
        Config.firewallWhitelistCommand = config.getString("firewall.command-whitelist");
        Config.connectSpeed = config.getInt("speed.connection");
        Config.pingSpeed = config.getInt("speed.ping-speed");
        Config.autoWhitelist = config.getBoolean("auto-whitelist.enabled");
        Config.autoWhitelistTime = config.getInt("auto-whitelist.time");
        Config.apiKey = config.getString("antibot.api-key");
        Config.countryList = config.getStringList("countries.list");
        Config.countryMode = config.getString("countries.mode");
        Config.antibot = config.getBoolean("antibot.enabled");
        Config.updater = config.getBoolean("updater");
        Config.attackResetTimer = config.getLong("speed.attack-timer-reset");
        Config.joinSpeed = config.getInt("speed.join-speed");
        Config.tabCompleteBlock = config.getBoolean("fully-block-tab-complete");
        Config.blockedCommands = config.getStringList("command-protection.list");
        Config.allowedCommands = config.getStringList("allowed-commands.list");
        Config.opProtectionList = config.getStringList("op-protection.list");
        Config.opProtectionAlert = config.getString("op-protection.alert");
        Config.opProtectionCommand = config.getString("op-protection.command");
        Config.blockedCommandsEnable = config.getBoolean("command-protection.enabled");
        Config.allowedCommandsEnable = config.getBoolean("allowed-commands.enabled");
        Config.opProtectionEnable = config.getBoolean("op-protection.enabled");
        Config.ipHistoryEnable = config.getBoolean("ip-history.enabled");
        Config.forceRejoin = config.getBoolean("antibot.force-rejoin");
        Config.pexProtection = config.getBoolean("op-protection.pex-protection");
        Config.blockedNames = config.getStringList("antibot.name-contains");
        Config.cloudEnabled = config.getBoolean("cloud.enabled");
        Config.cloudBlacklist = config.getBoolean("cloud.features.blacklist");
        Config.cloudTime = config.getLong("cloud.sync-every-seconds");
        Config.heuristicsEnabled = config.getBoolean("heuristics.enabled");
        Config.heuristicsDiff = config.getInt("heuristics.min-difference");
        Config.filterEnabled = config.getBoolean("console-filter.enabled");
        Config.filterValues = config.getStringList("console-filter.messages");
        Config.bandwidthOptimizer = config.getBoolean("bandwidth-optimizer");
        Config.customTabComplete = config.getBoolean("custom-tab-complete.enabled");
        Config.customTabCompleteList = config.getStringList("custom-tab-complete.list");
        Config.betaLayout = config.getBoolean("beta-layout");
        Config.allowedCommandsBypass = config.getBoolean("bypass.allowed-commands");
        Config.customTabCompleteBypass = config.getBoolean("bypass.custom-tab-complete");
    }

    public static void loadBungee() {
        final Yaml config = BUNGEE;
        Config.firewallEnabled = config.getBoolean("firewall");
        Config.firewallBlacklistCommand = config.getString("firewall.command-blacklist");
        Config.firewallWhitelistCommand = config.getString("firewall.command-whitelist");
        Config.connectSpeed = config.getInt("speed.connection");
        Config.pingSpeed = config.getInt("speed.ping-speed");
        Config.apiKey = config.getString("antibot.api-key");
        Config.countryList = config.getStringList("countries.list");
        Config.countryMode = config.getString("countries.mode");
        Config.antibot = config.getBoolean("antibot.enabled");
        Config.attackResetTimer = config.getLong("speed.attack-timer-reset");
        Config.blockedNames = config.getStringList("antibot.name-contains");
        Config.cloudEnabled = config.getBoolean("cloud.enabled");
        Config.cloudBlacklist = config.getBoolean("cloud.features.blacklist");
        Config.cloudTime = config.getLong("cloud.sync-every-seconds");
        Config.filterEnabled = config.getBoolean("console-filter.enabled");
        Config.filterValues = config.getStringList("console-filter.messages");
        Config.bandwidthOptimizer = config.getBoolean("bandwidth-optimizer");
    }
}

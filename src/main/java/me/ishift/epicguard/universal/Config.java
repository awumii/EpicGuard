package me.ishift.epicguard.universal;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;

import java.util.Arrays;
import java.util.List;

public class Config {
    public static final Yaml BUKKIT = new Yaml("config.yml", "plugins/EpicGuard");
    public static final Yaml BUNGEE = new Yaml("config_bungee.yml", "plugins/EpicGuard");

    public static List<String> checks;
    public static String firewallBlacklistCommand;
    public static String firewallWhitelistCommand;
    public static boolean firewallEnabled;
    public static int connectSpeed;
    public static int pingSpeed;
    public static boolean autoWhitelist;
    public static int autoWhitelistTime;
    public static String apiKey;
    public static List<String> countryList;
    public static List<String> blockedNames;
    public static String countryMode;
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

    public static void loadBukkit() {
        BUKKIT.setConfigSettings(ConfigSettings.PRESERVE_COMMENTS);

        Config.firewallEnabled = BUKKIT.getBoolean("firewall");
        Config.firewallBlacklistCommand = BUKKIT.getString("firewall.command-blacklist");
        Config.firewallWhitelistCommand = BUKKIT.getString("firewall.command-whitelist");
        Config.connectSpeed = BUKKIT.getInt("speed.connection");
        Config.pingSpeed = BUKKIT.getInt("speed.ping-speed");
        Config.autoWhitelist = BUKKIT.getBoolean("auto-whitelist.enabled");
        Config.autoWhitelistTime = BUKKIT.getInt("auto-whitelist.time");
        Config.apiKey = BUKKIT.getString("antibot.api-key");
        checks = BUKKIT.getOrSetDefault("checks", Arrays.asList("Blacklist", "Attack", "NameContains", "Geo", "Verify", "Proxy"));
        Config.countryList = BUKKIT.getStringList("countries.list");
        Config.countryMode = BUKKIT.getString("countries.mode");
        Config.updater = BUKKIT.getBoolean("updater");
        Config.attackResetTimer = BUKKIT.getLong("speed.attack-timer-reset");
        Config.tabCompleteBlock = BUKKIT.getBoolean("fully-block-tab-complete");
        Config.blockedCommands = BUKKIT.getStringList("command-protection.list");
        Config.allowedCommands = BUKKIT.getStringList("allowed-commands.list");
        Config.opProtectionList = BUKKIT.getStringList("op-protection.list");
        Config.opProtectionAlert = BUKKIT.getString("op-protection.alert");
        Config.opProtectionCommand = BUKKIT.getString("op-protection.command");
        Config.blockedCommandsEnable = BUKKIT.getBoolean("command-protection.enabled");
        Config.allowedCommandsEnable = BUKKIT.getBoolean("allowed-commands.enabled");
        Config.opProtectionEnable = BUKKIT.getBoolean("op-protection.enabled");
        Config.ipHistoryEnable = BUKKIT.getBoolean("ip-history.enabled");
        Config.forceRejoin = BUKKIT.getBoolean("antibot.force-rejoin");
        Config.pexProtection = BUKKIT.getBoolean("op-protection.pex-protection");
        Config.blockedNames = BUKKIT.getStringList("antibot.name-contains");
        Config.cloudEnabled = BUKKIT.getBoolean("cloud.enabled");
        Config.cloudBlacklist = BUKKIT.getBoolean("cloud.features.blacklist");
        Config.cloudTime = BUKKIT.getLong("cloud.sync-every-seconds");
        Config.heuristicsEnabled = BUKKIT.getBoolean("heuristics.enabled");
        Config.heuristicsDiff = BUKKIT.getInt("heuristics.min-difference");
        Config.filterEnabled = BUKKIT.getBoolean("console-filter.enabled");
        Config.filterValues = BUKKIT.getStringList("console-filter.messages");
        Config.bandwidthOptimizer = BUKKIT.getBoolean("bandwidth-optimizer");
        Config.customTabComplete = BUKKIT.getBoolean("custom-tab-complete.enabled");
        Config.customTabCompleteList = BUKKIT.getStringList("custom-tab-complete.list");
        Config.allowedCommandsBypass = BUKKIT.getBoolean("bypass.allowed-commands");
        Config.customTabCompleteBypass = BUKKIT.getBoolean("bypass.custom-tab-complete");
    }

    public static void loadBungee() {
        BUNGEE.setConfigSettings(ConfigSettings.PRESERVE_COMMENTS);
        checks = BUNGEE.getOrSetDefault("checks", Arrays.asList("Blacklist", "Attack", "NameContains", "Geo", "Verify", "Proxy"));
        Config.firewallEnabled= BUNGEE.getBoolean("firewall");
        Config.firewallBlacklistCommand= BUNGEE.getString("firewall.command-blacklist");
        Config.firewallWhitelistCommand= BUNGEE.getString("firewall.command-whitelist");
        Config.connectSpeed= BUNGEE.getInt("speed.connection");
        Config.pingSpeed= BUNGEE.getInt("speed.ping-speed");
        Config.apiKey= BUNGEE.getString("antibot.api-key");
        Config.countryList= BUNGEE.getStringList("countries.list");
        Config.countryMode= BUNGEE.getString("countries.mode");
        Config.attackResetTimer= BUNGEE.getLong("speed.attack-timer-reset");
        Config.blockedNames= BUNGEE.getStringList("antibot.name-contains");
        Config.cloudEnabled= BUNGEE.getBoolean("cloud.enabled");
        Config.cloudBlacklist= BUNGEE.getBoolean("cloud.features.blacklist");
        Config.cloudTime= BUNGEE.getLong("cloud.sync-every-seconds");
        Config.filterEnabled= BUNGEE.getBoolean("console-filter.enabled");
        Config.filterValues= BUNGEE.getStringList("console-filter.messages");
        Config.bandwidthOptimizer= BUNGEE.getBoolean("bandwidth-optimizer");
    }
}

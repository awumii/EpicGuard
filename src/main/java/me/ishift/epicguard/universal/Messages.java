package me.ishift.epicguard.universal;

import de.leonhard.storage.Yaml;

import java.util.Collections;
import java.util.List;

public class Messages {
    public static final Yaml FILE = new Yaml("messages_en_US", "plugins/EpicGuard");
    public static List<String> messageKickProxy;
    public static List<String> messageKickCountry;
    public static List<String> messageKickAttack;
    public static List<String> messageKickBlacklist;
    public static List<String> messageKickVerify;
    public static List<String> messageKickNamecontains;
    public static String historyNew;
    public static String noPermission;
    public static String prefix;
    public static String notAllowedCommand;
    public static String blockedCommand;

    public static void load() {
        prefix = FILE.getOrSetDefault("prefix", "&6EpicGuard &8//&7 ");

        messageKickProxy = FILE.getOrSetDefault("kick-messages.proxy", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6Proxy/VPN."));
        messageKickCountry = FILE.getOrSetDefault("kick-messages.country", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6Country GeoDetection."));
        messageKickAttack = FILE.getOrSetDefault("kick-messages.attack", Collections.singletonList("&8[&6EpicGuard&8] &cOur server is &6under attack&c, please wait some minutes before joining."));
        messageKickBlacklist = FILE.getOrSetDefault("kick-messages.blacklist", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6IP Blacklist."));
        messageKickVerify = FILE.getOrSetDefault("kick-messages.rejoin", Collections.singletonList("&8[&6EpicGuard&8] &cPlease join our server &6again &cto verify that you are not a bot."));
        messageKickNamecontains = FILE.getOrSetDefault("kick-messages.namecontains", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6NameContains (Change nickname or contact server admin)"));

        historyNew = FILE.getOrSetDefault("other.history-new", "&cPlayer &6{NICK} &cconnected with new address &8(&6{IP}&8)&c.");
        noPermission = FILE.getOrSetDefault("other.no-permission", "&cYou don't have permission to access this command!");
        notAllowedCommand = FILE.getOrSetDefault("other.not-allowed-command", "&fUnknown command. Type '/help' for help.");
        blockedCommand = FILE.getOrSetDefault("other.blocked-command", "&cThis command is not allowed!");
    }
}

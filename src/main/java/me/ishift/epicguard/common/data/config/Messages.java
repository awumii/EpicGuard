/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.ishift.epicguard.common.data.config;

import de.leonhard.storage.Yaml;

import java.util.Collections;
import java.util.List;

public class Messages {
    public static List<String> messageKickProxy;
    public static List<String> messageKickCountry;
    public static List<String> messagesKickServerList;
    public static List<String> messageKickBlacklist;
    public static List<String> messageKickVerify;
    public static List<String> messageKickNamecontains;
    public static List<String> messageKickBotBehaviour;

    public static String noPermission;
    public static String prefix;
    public static String notAllowedCommand;
    public static String blockedCommand;
    public static String playerOnly;
    public static String configReload;
    public static String usage;
    public static String playerNotFound;
    public static String blacklisted;
    public static String whitelisted;
    public static String unknownCommand;
    public static String statusOn;
    public static String statusOff;

    public static String operatorDisabled;
    public static String namespacedDisabled;

    public static String monitorActionAttack;

    public static void load() {
        final Yaml config = new Yaml("messages_en_US", "plugins/EpicGuard");
        config.setHeader("Welcome to the messages configuration of EpicGuard.", "You can customize some of the plugin parts here.", "More options will be added in the future.");
        prefix = config.getOrSetDefault("prefix", "&8[&6&lEpicGuard&8] &7");

        messageKickProxy = config.getOrSetDefault("kick-messages.proxy", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6Proxy/VPN."));
        messageKickCountry = config.getOrSetDefault("kick-messages.country", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6Country GeoDetection."));
        messagesKickServerList = config.getOrSetDefault("kick-messages.server-list", Collections.singletonList("&8[&6EpicGuard&8] &cAdd our server to your &6server list&c, and then join again."));
        messageKickBlacklist = config.getOrSetDefault("kick-messages.blacklist", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6IP Blacklist."));
        messageKickVerify = config.getOrSetDefault("kick-messages.rejoin", Collections.singletonList("&8[&6EpicGuard&8] &cPlease join our server &6again &cto verify that you are not a bot."));
        messageKickNamecontains = config.getOrSetDefault("kick-messages.namecontains", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6NameContains (Change nickname or contact server admin)"));
        messageKickBotBehaviour = config.getOrSetDefault("kick-messages.bot-behaviour", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6BotBehaviour (Please join again)"));

        noPermission = config.getOrSetDefault("other.no-permission", "&cYou don't have permission to access this command!");
        notAllowedCommand = config.getOrSetDefault("other.not-allowed-command", "&fUnknown command. Type '/help' for help.");
        blockedCommand = config.getOrSetDefault("other.blocked-command", "&cThis command is not allowed!");
        playerOnly = config.getOrSetDefault("other.player-only", "&cThis command is player-only!");
        configReload = config.getOrSetDefault("other.config-reload", "&7Configuration has been &asuccesfully &7reloaded.");
        usage = config.getOrSetDefault("other.usage", "&7Correct usage: &f/{USAGE}&7.");
        playerNotFound = config.getOrSetDefault("other.player-not-found", "&cPlayer not found!");
        blacklisted = config.getOrSetDefault("other.address-blacklisted", "&7Succesfully &cblacklisted &7address: &c{ADDRESS}");
        whitelisted = config.getOrSetDefault("other.address-whitelisted", "&7Succesfully &awhitelisted &7address: &a{ADDRESS}");
        unknownCommand = config.getOrSetDefault("other.unknown-command", "&cCommand not found! Use &6/guard");
        statusOn = config.getOrSetDefault("other.status-on", "&7Attack status has been &aenabled.");
        statusOff = config.getOrSetDefault("other.status-off", "&7Attack status has been &cdisabled.");

        operatorDisabled = config.getOrSetDefault("other.operator-mechanics-disabled", "&cOperator mechanics has been disabled on this server.");
        namespacedDisabled = config.getOrSetDefault("other.namespaced-disabled", "&cNamespaced commands (with ':' symbol) has been disabled on this server!.");
        monitorActionAttack = config.getOrSetDefault("monitor.action-attack", "&cEpicGuard Monitor » &7Connections/s: &c{CPS}  &7Blocked: &e{BLOCKED}  &7Attack: {STATUS}");
    }
}

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

package me.ishift.epicguard.common;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import me.ishift.epicguard.common.types.GeoMode;

import java.util.List;

public class Config {
    public static String firewallBlacklistCommand;
    public static String firewallWhitelistCommand;
    public static boolean firewallEnabled;

    public static int connectSpeed;
    public static int pingSpeed;

    public static boolean autoWhitelist;
    public static int autoWhitelistTime;

    public static String apiKey;

    public static List<String> countryList;
    public static GeoMode countryMode;
    public static boolean geoCity;

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
        final Yaml config = new Yaml("config.yml", "plugins/EpicGuard");
        config.setConfigSettings(ConfigSettings.PRESERVE_COMMENTS);

        firewallEnabled = config.getBoolean("firewall");
        firewallBlacklistCommand = config.getString("firewall.command-blacklist");
        firewallWhitelistCommand = config.getString("firewall.command-whitelist");
        connectSpeed = config.getInt("speed.connection");
        pingSpeed = config.getInt("speed.ping-speed");
        autoWhitelist = config.getBoolean("auto-whitelist.enabled");
        autoWhitelistTime = config.getInt("auto-whitelist.time");
        apiKey = config.getString("antibot.api-key");
        countryList = config.getStringList("countries.list");
        updater = config.getBoolean("updater");
        tabCompleteBlock = config.getBoolean("fully-block-tab-complete");
        blockedCommands = config.getStringList("command-protection.list");
        allowedCommands = config.getStringList("allowed-commands.list");
        opProtectionList = config.getStringList("op-protection.list");
        opProtectionAlert = config.getString("op-protection.alert");
        opProtectionCommand = config.getString("op-protection.command");
        blockedCommandsEnable = config.getBoolean("command-protection.enabled");
        allowedCommandsEnable = config.getBoolean("allowed-commands.enabled");
        opProtectionEnable = config.getBoolean("op-protection.enabled");
        ipHistoryEnable = config.getBoolean("ip-history.enabled");
        pexProtection = config.getBoolean("op-protection.pex-protection");
        blockedNames = config.getStringList("antibot.name-contains");
        filterEnabled = config.getBoolean("console-filter.enabled");
        filterValues = config.getStringList("console-filter.messages");
        bandwidthOptimizer = config.getBoolean("bandwidth-optimizer");
        customTabComplete = config.getBoolean("custom-tab-complete.enabled");
        customTabCompleteList = config.getStringList("custom-tab-complete.list");
        allowedCommandsBypass = config.getBoolean("bypass.allowed-commands");
        customTabCompleteBypass = config.getBoolean("bypass.custom-tab-complete");

        serverListCheck = config.getOrSetDefault("antibot.server-list-check", true);
        rejoinCheck = config.getOrSetDefault("antibot.rejoin-check", true);
        geoCity = config.getOrSetDefault("countries.enable-cities", false);

        final String countryModeString = config.getString("countries.mode");
        countryMode = GeoMode.valueOf(countryModeString);
    }

    public static void loadBungee() {
        final Yaml config = new Yaml("config_bungee.yml", "plugins/EpicGuard");
        config.setConfigSettings(ConfigSettings.PRESERVE_COMMENTS);

        firewallEnabled = config.getBoolean("firewall");
        firewallBlacklistCommand = config.getString("firewall.command-blacklist");
        firewallWhitelistCommand = config.getString("firewall.command-whitelist");
        connectSpeed = config.getInt("speed.connection");
        pingSpeed = config.getInt("speed.ping-speed");
        apiKey = config.getString("antibot.api-key");
        countryList = config.getStringList("countries.list");
        blockedNames = config.getStringList("antibot.name-contains");
        filterEnabled = config.getBoolean("console-filter.enabled");
        filterValues = config.getStringList("console-filter.messages");
        bandwidthOptimizer = config.getBoolean("bandwidth-optimizer");

        serverListCheck = config.getOrSetDefault("antibot.server-list-check", true);
        rejoinCheck = config.getOrSetDefault("antibot.rejoin-check", true);

        final String countryModeString = config.getOrSetDefault("countries.mode", "DISABLED");
        countryMode = GeoMode.valueOf(countryModeString);
    }
}

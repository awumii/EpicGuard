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
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.detection.ProxyChecker;
import me.ishift.epicguard.common.types.GeoMode;

import java.util.List;

public class Configuration {
    public static int connectSpeed;
    public static int pingSpeed;

    public static boolean autoWhitelist;
    public static int autoWhitelistTime;

    public static boolean simpleProxyCheck;
    public static boolean whitelistedSkipProxyCheck;
    public static String apiKey;

    public static List<String> countryList;
    public static GeoMode countryMode;
    public static boolean cityEnabled;
    public static boolean countryEnabled;

    public static List<String> blockedNames;

    public static boolean rejoinCheck;
    public static boolean serverListCheck;

    public static boolean filterEnabled;
    public static List<String> filterValues;

    public static boolean advancedProxyChecker;

    public static void load() {
        final Yaml config = new Yaml("config.yml", "plugins/EpicGuard");

        connectSpeed = config.getInt("antibot.additional-protection.conditions.connections-per-second");
        pingSpeed = config.getInt("antibot.additional-protection.conditions.ping-per-second");

        serverListCheck = config.getBoolean("antibot.additional-protection.checks.server-list-check");
        rejoinCheck = config.getBoolean("antibot.additional-protection.checks.rejoin-check");

        autoWhitelist = config.getBoolean("auto-whitelist.enabled");
        autoWhitelistTime = config.getInt("auto-whitelist.time");

        simpleProxyCheck = config.getBoolean("antibot.simple-proxy-check.enabled");
        apiKey = config.getString("antibot.simple-proxy-check.api-key");

        blockedNames = config.getStringList("antibot.name-contains-check");
        whitelistedSkipProxyCheck = config.getBoolean("antibot.whitelisted-players-skip-proxy-check");

        filterEnabled = config.getBoolean("console-filter.enabled");
        filterValues = config.getStringList("console-filter.messages");

        countryList = config.getStringList("geographical.list");
        cityEnabled = config.getBoolean("geographical.download-databases.city");
        countryEnabled = config.getBoolean("geographical.download-databases.country");

        final String countryModeString = config.getString("geographical.mode");
        countryMode = GeoMode.valueOf(countryModeString);

        advancedProxyChecker = config.getBoolean("advanced-proxy-checker.enabled");

        if (advancedProxyChecker) {
            final String basePath = "advanced-proxy-checker.checkers";
            config.getSection(basePath).singleLayerKeySet().stream().map(num -> basePath + "." + num).forEachOrdered(path -> {
                final String url = config.getString(path + ".url");
                final List<String> contains = config.getStringList(path + ".contains");
                AttackManager.getCheckers().add(new ProxyChecker(url, contains));
            });
        }
    }
}

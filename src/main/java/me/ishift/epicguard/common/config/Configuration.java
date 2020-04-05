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

package me.ishift.epicguard.common.config;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.detection.ProxyChecker;
import me.ishift.epicguard.common.types.GeoMode;

import java.util.Arrays;
import java.util.List;

public class Configuration {
    public static int connectSpeed;
    public static int pingSpeed;

    public static boolean autoWhitelist;
    public static int autoWhitelistTime;

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
        connectSpeed = config.getInt("speed.connection");
        pingSpeed = config.getInt("speed.ping-speed");
        autoWhitelist = config.getBoolean("auto-whitelist.enabled");
        autoWhitelistTime = config.getInt("auto-whitelist.time");
        apiKey = config.getString("antibot.api-key");
        countryList = config.getStringList("countries.list");
        blockedNames = config.getStringList("antibot.name-contains");
        filterEnabled = config.getBoolean("console-filter.enabled");
        filterValues = config.getStringList("console-filter.messages");

        serverListCheck = config.getOrSetDefault("antibot.server-list-check", true);
        rejoinCheck = config.getOrSetDefault("antibot.rejoin-check", true);

        cityEnabled = config.getOrSetDefault("download-databases.city", true);
        countryEnabled = config.getOrSetDefault("download-databases.country", true);

        final String countryModeString = config.getString("countries.mode");
        countryMode = GeoMode.valueOf(countryModeString);

        advancedProxyChecker = config.getOrSetDefault("advanced-proxy-checker.enabled", false);
        // Setting example.
        config.setDefault("advanced-proxy-checker.checkers.1.url", "http://proxycheck.io/v2/{ADDRESS}");
        config.setDefault("advanced-proxy-checker.checkers.1.contains", Arrays.asList("yes", "VPN"));
        if (!advancedProxyChecker) {
            return;
        }

        final String basePath = "advanced-proxy-checker.checkers";
        config.getSection(basePath).singleLayerKeySet().stream().map(num -> basePath + "." + num).forEachOrdered(path -> {
            final String url = config.getString(path + ".url");
            final List<String> contains = config.getStringList(path + ".contains");
            AttackManager.getCheckers().add(new ProxyChecker(url, contains));
        });
    }
}

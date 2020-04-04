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

import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.detection.ProxyChecker;
import me.ishift.epicguard.common.types.GeoMode;

import java.util.Arrays;
import java.util.List;

public class Configuration {
    public static int CONNECTION_SPEED;

    public static boolean GEO_CHECK;
    public static GeoMode GEO_CHECK_MODE;
    public static List<String> GEO_CHECK_VALUES;
    public static boolean GEO_COUNTRY;
    public static boolean GEO_CITY;

    public static boolean SERVER_LIST_CHECK;
    public static boolean REJOIN_CHECK;

    public static boolean AUTO_WHITELIST;
    public static int AUTO_WHITELIST_TIME;

    public static boolean CONSOLE_FILTER;
    public static List<String> CONSOLE_FILTER_VALUES;

    public static List<String> BLOCKED_NAMES;

    public static void load() {
        final Config config = new Config("config", "plugins/EpicGuard");
        config.setConfigSettings(ConfigSettings.SKIP_COMMENTS);

        SERVER_LIST_CHECK = config.getOrSetDefault("General.ServerListCheck", true);
        REJOIN_CHECK = config.getOrSetDefault("General.ReJoinCheck", true);

        AUTO_WHITELIST = config.getOrSetDefault("General.AutoWhitelist.Enabled", true);
        AUTO_WHITELIST_TIME = config.getOrSetDefault("General.AutoWhitelist.SecondsOnline", 60);

        CONSOLE_FILTER = config.getOrSetDefault("ConsoleFilter.Enabled", true);
        CONSOLE_FILTER_VALUES = config.getOrSetDefault("ConsoleFilter.Messages", Arrays.asList("lost connection", "InitialHandler", "UUID of player", "logged in", "GameProfile"));

        CONNECTION_SPEED = config.getOrSetDefault("AttackConnectionSpeed", 8);
        GEO_CHECK = config.getOrSetDefault("Geographical.Enabled", true);
        GEO_COUNTRY = config.getOrSetDefault("Geographical.Download.Country", true);
        GEO_CITY = config.getOrSetDefault("Geographical.Download.City", true);
        GEO_CHECK_MODE = GeoMode.valueOf(config.getOrSetDefault("Geographical.Mode", "WHITELIST").toUpperCase());
        config.setDefault("Geographical.Countries", Arrays.asList("DE", "US", "PL"));

        config.setDefault("ProxyCheckers.1.address", "https://proxycheck.io/v2/{ADDRESS}?key=YOUR_KEY&vpn=1");
        config.setDefault("ProxyCheckers.1.response", "yes");

        config.setDefault("ProxyCheckers.2.address", "https://check.getipintel.net/check.php?ip={ADDRESS}&format=json&contact=hello@yourmail.com&flags=m");
        config.setDefault("ProxyCheckers.2.response", "1");

        config.setDefault("ProxyCheckers.3.address", "https://www.stopforumspam.com/api?ip={ADDRESS}");
        config.setDefault("ProxyCheckers.3.response", "yes");

        final String basePath = "ProxyCheckers";
        config.getSection(basePath).singleLayerKeySet().stream().map(num -> basePath + "." + num).forEachOrdered(path -> {
            final String url = config.getString(path + ".address");
            final List<String> contains = config.getStringList(path + ".response");
            AttackManager.getCheckers().add(new ProxyChecker(url, contains));
        });
    }
}

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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Configuration {
    public static int connectSpeed;
    public static int pingSpeed;

    public static long checkConditionsDelay;

    public static boolean autoWhitelist;
    public static int autoWhitelistTime;

    public static boolean simpleProxyCheck;
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
        try {
            final Yaml config = new Yaml("config.yml", "plugins/EpicGuard");

            connectSpeed = config.getInt("antibot.additional-protection.conditions.connections-per-second");
            pingSpeed = config.getInt("antibot.additional-protection.conditions.ping-per-second");

            checkConditionsDelay = config.getInt("antibot.additional-protection.check-conditions-delay");

            serverListCheck = config.getBoolean("antibot.additional-protection.checks.server-list-check");
            rejoinCheck = config.getBoolean("antibot.additional-protection.checks.rejoin-check");

            autoWhitelist = config.getBoolean("auto-whitelist.enabled");
            autoWhitelistTime = config.getInt("auto-whitelist.time");

            simpleProxyCheck = config.getBoolean("antibot.simple-proxy-check.enabled");
            apiKey = config.getString("antibot.simple-proxy-check.api-key");

            blockedNames = config.getStringList("antibot.name-contains-check");

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
        } catch (Exception e) {
            e.printStackTrace();
            final Logger logger = Logger.getLogger("ConfigurationLoader");
            logger.warning("==================================");
            logger.warning(" FAILED TO LOAD CONFIGURATION FILE!");
            logger.warning(" IF YOU HAVE UPDATED THE PLUGIN, RESTART THE SERVER.");
            logger.warning(" IF THIS ISN'T AN UPDATE, PLEASE CORRECT YOUR CONFIG.");
            logger.warning("==================================");
        }
    }

    /**
     * Very messy method to check and remove the old config, but i don't have any other idea.
     */
    public static void checkVersion() {
        final File cfg = new File("plugins/EpicGuard/config.yml");
        final File fileDir = new File("plugins/EpicGuard/data");
        final File file = new File("plugins/EpicGuard/data/update.temp");
        if (!cfg.exists()) {
            return;
        }
        if (!file.exists()) {
            fileDir.mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cfg.delete();
        }
    }
}

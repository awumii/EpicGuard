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
import me.ishift.epicguard.common.types.GeoMode;

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

    public static List<String> BLOCKED_NAMES;

    public static void load() {
        final Config config = new Config("config", "plugins/EpicGuard");
    }
}

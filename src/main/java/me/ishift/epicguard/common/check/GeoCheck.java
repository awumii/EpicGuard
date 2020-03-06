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

package me.ishift.epicguard.common.check;

import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.types.GeoMode;

public class GeoCheck {
    public static boolean perform(String address) {
        final String country = EpicGuardAPI.getGeoApi().getCountryCode(address);

        if (country == null || country.equals("Unknown?") || Config.countryMode == GeoMode.DISABLED) {
            return false;
        }
        if (Config.countryMode == GeoMode.WHITELIST) {
            return !Config.countryList.contains(country);
        }
        if (Config.countryMode == GeoMode.BLACKLIST) {
            return Config.countryList.contains(country);
        }
        return false;
    }
}

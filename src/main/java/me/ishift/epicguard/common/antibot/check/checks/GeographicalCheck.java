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

package me.ishift.epicguard.common.antibot.check.checks;

import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.antibot.check.Check;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.types.GeoMode;

public class GeographicalCheck implements Check {
    private final AttackManager attackManager;

    public GeographicalCheck(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    @Override
    public boolean execute(String address, String nickname) {
        if (this.attackManager.getGeoApi() == null) {
            return false;
        }

        final String country = this.attackManager.getGeoApi().getCountryCode(address);
        if (country.equals("Unknown?") || Configuration.countryMode == GeoMode.DISABLED) {
            return false;
        }
        if (Configuration.countryMode == GeoMode.WHITELIST) {
            return !Configuration.countryList.contains(country);
        }
        if (Configuration.countryMode == GeoMode.BLACKLIST) {
            return Configuration.countryList.contains(country);
        }
        return false;
    }
}

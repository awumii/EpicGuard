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

package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckMode;
import me.ishift.epicguard.core.user.BotUser;

import java.util.List;

public class GeographicalCheck extends Check {
    public GeographicalCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().countryCheck);

        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return this.geoCheck(user.getAddress());
            case ATTACK:
                if (this.isAttack()) {
                    return this.geoCheck(user.getAddress());
                }
        }
        return false;
    }

    private boolean geoCheck(String address) {
        String country = this.getEpicGuard().getGeoManager().getCountryCode(address);
        String city = this.getEpicGuard().getGeoManager().getCity(address);

        if (this.getConfig().cityBlacklist.contains(city)) {
            return true;
        }

        if (this.getConfig().countryCheckType.equals("WHITELIST")) {
            return !this.getConfig().countryCheckValues.contains(country);
        } else {
            return this.getConfig().countryCheckValues.contains(country);
        }
    }

    @Override
    public List<String> getKickMessage() {
        return this.getMessages().kickMessageGeo;
    }

    @Override
    public boolean shouldBlacklist() {
        return true;
    }
}

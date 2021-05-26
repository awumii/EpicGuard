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

package me.xneox.epicguard.core.check.impl;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.CheckMode;
import me.xneox.epicguard.core.user.PendingUser;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * This will check if the user's geographical location is allowed based on the configured behaviour.
 */
public class GeographicalCheck extends Check {
    public GeographicalCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@Nonnull PendingUser user) {
        CheckMode mode = CheckMode.valueOf(this.epicGuard.getConfig().checkMode);
        return this.assertCheck(mode, this.isRestricted(user.getAddress()));
    }

    private boolean isRestricted(String address) {
        String country = this.epicGuard.getGeoManager().getCountryCode(address);
        String city = this.epicGuard.getGeoManager().getCity(address);

        if (this.epicGuard.getConfig().cityBlacklist.contains(city)) {
            return true;
        }

        if (this.epicGuard.getConfig().checkType.equals("WHITELIST")) {
            return !this.epicGuard.getConfig().countries.contains(country);
        } else {
            return this.epicGuard.getConfig().countries.contains(country);
        }
    }

    @Override
    public @Nonnull List<String> getKickMessage() {
        return this.epicGuard.getMessages().kickMessageGeo;
    }
}

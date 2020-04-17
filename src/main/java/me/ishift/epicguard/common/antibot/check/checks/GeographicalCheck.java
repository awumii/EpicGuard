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

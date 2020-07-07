package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckMode;

public class GeographicalCheck extends Check {
    public GeographicalCheck(EpicGuard epicGuard) {
        super(epicGuard, true, epicGuard.getMessages().kickMessageGeo);
    }

    private enum Type {
        BLACKLIST,
        WHITELIST
    }

    @Override
    public boolean check(String address, String nickname) {
        Type type = Type.valueOf(this.getConfig().countryCheckType);
        CheckMode mode = CheckMode.valueOf(this.getConfig().countryCheck);

        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return this.geoCheck(address, type);
            case ATTACK:
                if (this.getEpicGuard().isAttack()) {
                    return this.geoCheck(address, type);
                }
        }
        return false;
    }

    private boolean geoCheck(String address, Type type) {
        String country = this.getEpicGuard().getGeoManager().getCountryCode(address);
        String city = this.getEpicGuard().getGeoManager().getCity(address);

        if (this.getConfig().cityBlacklist.contains(city)) {
            return true;
        }

        if (type == Type.WHITELIST) {
            return !this.getConfig().countryCheckValues.contains(country);
        } else {
            return this.getConfig().countryCheckValues.contains(country);
        }
    }
}

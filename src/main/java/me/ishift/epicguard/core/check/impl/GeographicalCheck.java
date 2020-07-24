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
    public List<String> reason() {
        return this.getMessages().kickMessageGeo;
    }

    @Override
    public boolean blacklist() {
        return true;
    }
}

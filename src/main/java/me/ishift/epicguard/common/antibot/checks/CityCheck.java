package me.ishift.epicguard.common.antibot.checks;

import lombok.AllArgsConstructor;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.antibot.Check;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.types.GeoMode;

@AllArgsConstructor
public class CityCheck implements Check {
    private final AttackManager manager;

    @Override
    public boolean execute(String address, String nickname) {
        if (this.manager.getGeoApi() == null) {
            return false;
        }

        final String city = this.manager.getGeoApi().getCity(address);
        if (city.equals("Unknown?") || Configuration.cityMode == GeoMode.DISABLED) {
            return false;
        }
        if (Configuration.cityMode == GeoMode.WHITELIST) {
            return !Configuration.cityList.contains(city);
        }
        if (Configuration.cityMode == GeoMode.BLACKLIST) {
            return Configuration.cityList.contains(city);
        }
        return false;
    }
}

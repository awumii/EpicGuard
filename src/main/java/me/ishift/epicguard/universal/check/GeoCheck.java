package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.types.Reason;
import me.ishift.epicguard.universal.GeoAPI;

public class GeoCheck {
    public static boolean perform(String address) {
        final String country = GeoAPI.getCountryCode(address);

        if (country == null || country.equals("Unknown?") || Config.countryMode.equals("DISABLED")) {
            return false;
        }
        if (Config.countryMode.equals("WHITELIST")) {
            return !Config.countryList.contains(country);
        }
        if (Config.countryMode.equals("BLACKLIST")) {
            return Config.countryList.contains(country);
        }
        return false;
    }
}

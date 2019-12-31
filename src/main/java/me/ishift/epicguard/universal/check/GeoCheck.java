package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;

public class GeoCheck {
    /**
     * @param country country ISO Code (String).
     * @return false if not detected, true if detected.
     */
    public static boolean check(String country) {
        if (Config.COUNTRY_MODE.equals("DISABLED")) {
            return false;
        }
        if (Config.COUNTRY_MODE.equals("WHITELIST")) {
            if (!Config.COUNTRIES.contains(country)) {
                return true;
            }
        }
        if (Config.COUNTRY_MODE.equals("BLACKLIST")) {
            return Config.COUNTRIES.contains(country);
        }
        return false;
    }
}

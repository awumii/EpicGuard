package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;

public class GeoCheck {
    /**
     * @param country country ISO Code (String).
     * @return false if not detected, true if detected.
     */
    public static boolean check(String country) {
        if (country.equalsIgnoreCase("Unknown?")) {
            return false;
        }
        if (Config.countryMode.equals("DISABLED")) {
            return false;
        }
        if (Config.countryMode.equals("WHITELIST")) {
            if (!Config.countryList.contains(country)) {
                return true;
            }
        }
        if (Config.countryMode.equals("BLACKLIST")) {
            return Config.countryList.contains(country);
        }
        return false;
    }
}

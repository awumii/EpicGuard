package io.github.polskistevek.epicguard.check;

import io.github.polskistevek.epicguard.universal.ConfigProvider;

public class GeoCheck {
    /**
     *
     * @param country country ISO Code (String).
     * @return false if not detected, true if detected.
     */
    public static boolean check(String country) {
        if (ConfigProvider.COUNTRY_MODE.equals("DISABLED")) {
            return false;
        }
        if (ConfigProvider.COUNTRY_MODE.equals("WHITELIST")) {
            if (!ConfigProvider.COUNTRIES.contains(country)) {
                return true;
            }
        }
        if (ConfigProvider.COUNTRY_MODE.equals("BLACKLIST")) {
            return ConfigProvider.COUNTRIES.contains(country);
        }
        return false;
    }
}

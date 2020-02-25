package me.ishift.epicguard.universal.check.detection;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.Check;
import me.ishift.epicguard.universal.types.Reason;
import me.ishift.epicguard.universal.util.GeoAPI;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GeoCheck extends Check {
    public GeoCheck() {
        super(Reason.GEO, true);
    }

    @Override
    public boolean perform(String address, String nickname) {
        String country = null;
        try {
            country = GeoAPI.getCountryCode(InetAddress.getByName(address));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

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

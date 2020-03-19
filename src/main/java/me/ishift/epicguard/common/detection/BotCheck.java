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

package me.ishift.epicguard.common.detection;

import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.StorageManager;
import me.ishift.epicguard.common.types.GeoMode;
import me.ishift.epicguard.common.types.Reason;
import me.ishift.epicguard.api.URLHelper;

import java.util.ArrayList;
import java.util.List;

public class BotCheck {
    private static final List<String> REJOIN = new ArrayList<>();
    private static final List<String> PING = new ArrayList<>();

    public static Detection getDetection(String address, String nickname) {
        final Detection detection = new Detection();
        if (blacklistCheck(address)) {
            detection.setReason(Reason.BLACKLIST);
        }
        else if (nameCheck(nickname)) {
            detection.setReason(Reason.NAMECONTAINS);
            detection.setBlacklist(true);
        }
        else if (geoCheck(address)) {
            detection.setReason(Reason.GEO);
            detection.setBlacklist(true);
        }
        else if (serverListCheck(address)) {
            detection.setReason(Reason.SERVERLIST);
        }
        else if (verifyCheck(nickname)) {
            detection.setReason(Reason.VERIFY);
        }
        else if (proxyCheck(address)) {
            detection.setReason(Reason.PROXY);
            detection.setBlacklist(true);
        } else {
            detection.setDetected(false);
        }
        return detection;
    }

    public static boolean proxyCheck(String address) {
        final String url = "http://proxycheck.io/v2/" + address + "?key=" + Config.apiKey;
        final List<String> response = URLHelper.readLines(url);

        if (response != null) {
            return response.contains("yes");
        }
        return false;
    }

    public static boolean blacklistCheck(String address) {
        return StorageManager.isBlacklisted(address);
    }

    public static boolean geoCheck(String address) {
        final String country = EpicGuardAPI.getGeoApi().getCountryCode(address);

        if (country == null || country.equals("Unknown?") || Config.countryMode == GeoMode.DISABLED) {
            return false;
        }
        if (Config.countryMode == GeoMode.WHITELIST) {
            return !Config.countryList.contains(country);
        }
        if (Config.countryMode == GeoMode.BLACKLIST) {
            return Config.countryList.contains(country);
        }
        return false;
    }

    public static boolean nameCheck(String nickname) {
        return Config.blockedNames.stream().anyMatch(string -> nickname.toLowerCase().contains(string.toLowerCase()));
    }

    public static boolean verifyCheck(String nickname) {
        if (Config.rejoinCheck && AttackSpeed.isUnderAttack() && !REJOIN.contains(nickname)) {
            REJOIN.add(nickname);
            return true;
        }
        return false;
    }

    public static boolean serverListCheck(String address) {
        if (Config.serverListCheck && AttackSpeed.isUnderAttack()) {
            return !PING.contains(address);
        }
        return false;
    }

    public static void addPing(String address) {
        if (!PING.contains(address)) PING.add(address);
    }
}

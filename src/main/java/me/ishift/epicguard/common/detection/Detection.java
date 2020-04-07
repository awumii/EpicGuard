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

import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.types.GeoMode;
import me.ishift.epicguard.common.types.Reason;
import me.ishift.epicguard.common.util.URLHelper;

import java.util.List;

public class Detection {
    private String address;
    private String nickname;

    private boolean detected;
    private boolean blacklist;
    private Reason reason;

    public Detection(String address, String nickname) {
        this.address = address;
        this.nickname = nickname;
        this.detected = true;
        this.blacklist = false;
        this.perform();
    }

    public void perform() {
        if (StorageManager.getStorage().getWhitelist().contains(address)) {
            this.detected = false;
            return;
        }

        AttackManager.increaseConnectPerSecond();
        if (AttackManager.getConnectPerSecond() > Configuration.connectSpeed) {
            AttackManager.setAttackMode(true);
        }

        if (blacklistCheck(address)) {
            this.reason = Reason.BLACKLIST;
        }
        else if (nameCheck(nickname)) {
            this.reason = Reason.NAME_CONTAINS;
            this.blacklist = true;
        }
        else if (serverListCheck(address)) {
            this.reason = Reason.SERVER_LIST;
        }
        else if (rejoinCheck(nickname)) {
            this.reason = Reason.REJOIN;
        }
        else if (geoCheck(address)) {
            this.reason = Reason.GEO;
            this.blacklist = true;
        }
        else if (proxyCheck(address)) {
            this.reason = Reason.PROXY;
            this.blacklist = true;
        } else {
            this.detected = false;
        }

        if (this.detected) {
            AttackManager.increaseBots();
        }

        if (this.blacklist) {
            StorageManager.getStorage().blacklist(this.address);
        }
    }

    private boolean proxyCheck(String address) {
        if (Configuration.simpleProxyCheck) {
            final String url = "https://proxycheck.io/v2/" + address + "?key=" + Configuration.apiKey;
            final List<String> response = URLHelper.readLines(url);
            return response != null && response.contains("yes");
        }

        if (Configuration.advancedProxyChecker) {
            for (ProxyChecker proxyChecker : AttackManager.getCheckers()) {
                final String url = proxyChecker.getUrl().replace("{ADDRESS}", address);
                final List<String> response = URLHelper.readLines(url);

                if (response == null) {
                    return false;
                }
                for (String responseString : response) {
                    for (String containsString : proxyChecker.getContains()) {
                        if (responseString.contains(containsString)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean blacklistCheck(String address) {
        return StorageManager.getStorage().getBlacklist().contains(address);
    }

    private boolean geoCheck(String address) {
        if (AttackManager.getGeoApi() == null) {
            return false;
        }

        final String country = AttackManager.getGeoApi().getCountryCode(address);
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

    private boolean nameCheck(String nickname) {
        return Configuration.blockedNames.stream().anyMatch(string -> nickname.toLowerCase().contains(string.toLowerCase()));
    }

    private boolean rejoinCheck(String nickname) {
        if (Configuration.rejoinCheck && AttackManager.isUnderAttack() && !StorageManager.getStorage().getRejoinData().contains(nickname)) {
            StorageManager.getStorage().getRejoinData().add(nickname);
            return true;
        }
        return false;
    }

    private boolean serverListCheck(String address) {
        if (Configuration.serverListCheck && AttackManager.isUnderAttack()) {
            return !StorageManager.getStorage().getPingData().contains(address);
        }
        return false;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public boolean isBlacklist() {
        return blacklist;
    }

    public void setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }

    public boolean isDetected() {
        return detected;
    }
}

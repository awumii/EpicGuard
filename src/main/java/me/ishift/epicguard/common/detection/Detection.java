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
import me.ishift.epicguard.api.URLHelper;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.types.GeoMode;
import me.ishift.epicguard.common.types.Reason;

import java.util.List;

public class Detection {
    private final String address;
    private final String nickname;

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
        StorageManager.getStorage().increaseCheckedConnections();
        if (StorageManager.getStorage().getWhitelist().contains(address)) {
            this.detected = false;
            return;
        }

        AttackSpeed.setConnectPerSecond(AttackSpeed.getConnectPerSecond() + 1);
        if (AttackSpeed.getConnectPerSecond() > Config.connectSpeed) {
            AttackSpeed.setAttackMode(true);
        }

        if (blacklistCheck(address)) {
            this.reason = Reason.BLACKLIST;
        } else if (nameCheck(nickname)) {
            this.reason = Reason.NAME_CONTAINS;
            this.blacklist = true;
        } else if (serverListCheck(address)) {
            this.reason = Reason.SERVER_LIST;
        } else if (verifyCheck(nickname)) {
            this.reason = Reason.VERIFY;
        } else if (geoCheck(address)) {
            this.reason = Reason.GEO;
            this.blacklist = true;
        } else if (proxyCheck(address)) {
            this.reason = Reason.PROXY;
            this.blacklist = true;
        } else {
            this.detected = false;
        }

        if (this.detected) {
            AttackSpeed.setTotalBots(AttackSpeed.getTotalBots() + 1);
        }

        if (this.blacklist) {
            StorageManager.getStorage().blacklist(this.address);
        }
    }

    private boolean proxyCheck(String address) {
        if (Config.advancedProxyChecker) {
            return ProxyManager.isProxyUser(address);
        }
        final String url = "https://proxycheck.io/v2/" + address + "?key=" + Config.apiKey;
        final List<String> response = URLHelper.readLines(url);

        if (response != null) {
            return response.contains("yes");
        }
        return false;
    }

    private boolean blacklistCheck(String address) {
        return StorageManager.getStorage().getBlacklist().contains(address);
    }

    private boolean geoCheck(String address) {
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

    private boolean nameCheck(String nickname) {
        return Config.blockedNames.stream().anyMatch(string -> nickname.toLowerCase().contains(string.toLowerCase()));
    }

    private boolean verifyCheck(String nickname) {
        if (Config.rejoinCheck && AttackSpeed.isUnderAttack() && !StorageManager.getStorage().getRejoinData().contains(nickname)) {
            StorageManager.getStorage().getRejoinData().add(nickname);
            return true;
        }
        return false;
    }

    private boolean serverListCheck(String address) {
        if (Config.serverListCheck && AttackSpeed.isUnderAttack()) {
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

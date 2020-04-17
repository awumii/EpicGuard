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

package me.ishift.epicguard.common.antibot;

import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.types.Reason;

public class Detection {
    private final AttackManager attackManager;
    private final String address;
    private final String nickname;

    private boolean detected;
    private boolean blacklist;
    private Reason reason;

    public Detection(String address, String nickname, AttackManager attackManager) {
        this.attackManager = attackManager;
        this.address = address;
        this.nickname = nickname;
        this.detected = true;
        this.blacklist = false;
        this.perform();
    }

    public void perform() {
        // Checking if player is whitelisted.
        if (StorageManager.getStorage().getWhitelist().contains(address)) {
            this.detected = false;
            return;
        }

        // Increasing CPS/activating attack mode.
        this.attackManager.increaseConnectPerSecond();
        if (this.attackManager.getConnectPerSecond() > Configuration.connectSpeed) {
            this.attackManager.setAttackMode(true);
        }

        // Performing checks.
        if (this.attackManager.getBlacklistCheck().execute(address, nickname, this.attackManager)) {
            this.reason = Reason.BLACKLIST;
        }
        else if (this.attackManager.getNicknameCheck().execute(address, nickname, this.attackManager)) {
            this.reason = Reason.NAME_CONTAINS;
            this.blacklist = true;
        }
        else if (this.attackManager.getServerListCheck().execute(address, nickname, this.attackManager)) {
            this.reason = Reason.SERVER_LIST;
        }
        else if (this.attackManager.getReJoinCheck().execute(address, nickname, this.attackManager)) {
            this.reason = Reason.REJOIN;
        }
        else if (this.attackManager.getGeographicalCheck().execute(address, nickname, this.attackManager)) {
            this.reason = Reason.GEO;
            this.blacklist = true;
        }
        else if (this.attackManager.getProxyCheck().execute(address, nickname, this.attackManager)) {
            this.reason = Reason.PROXY;
            this.blacklist = true;
        }
        else {
            this.detected = false;
        }

        // Increasing bots if detection is positive.
        if (this.detected) {
            this.attackManager.increaseBots();
        }

        // Blacklisting addres if it should be.
        if (this.blacklist) {
            StorageManager.getStorage().blacklist(this.address);
        }
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

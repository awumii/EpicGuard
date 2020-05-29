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

import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.GuardLogger;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.types.Reason;

public class Detection {
    private final AttackManager manager;
    private final String address;
    private final String nickname;

    private boolean detected;
    private boolean blacklist;
    private Reason reason;

    /**
     * @param address Address of the user.
     * @param nickname Nickname of the user.
     * @param manager Instance of the AttackManager.
     */
    public Detection(String address, String nickname, AttackManager manager) {
        this.manager = manager;
        this.address = address;
        this.nickname = nickname;
        this.detected = true;
        this.perform();
    }

    /**
     * @return Reason of the detection.
     * Can be null if user is not detected.
     */
    public Reason getReason() {
        return this.reason;
    }

    /**
     * @return Should address be blacklisted.
     */
    public boolean isBlacklist() {
        return this.blacklist;
    }

    /**
     * @return True if detection is positive, or false if not.
     */
    public boolean isDetected() {
        return this.detected;
    }

    /**
     * Performing the checks.
     */
    public void perform() {
        this.manager.increaseConnectPerSecond();
        this.detected = false;
    }
}

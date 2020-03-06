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

package me.ishift.epicguard.common;

import me.ishift.epicguard.common.types.CounterType;
import me.ishift.epicguard.common.types.Reason;

public class AttackSpeed {
    private static int connectPerSecond = 0;
    private static int pingPerSecond = 0;
    private static int totalBots = 0;
    private static boolean attackMode = false;
    private static String lastBot = "None";
    private static Reason lastReason = Reason.GEO;

    public static void reset() {
        setAttackMode(false);
        setTotalBots(0);
    }

    public static void increase(CounterType type) {
        if (type == CounterType.CONNECT) {
            AttackSpeed.setConnectPerSecond(AttackSpeed.getConnectPerSecond() + 1);
        }
        if (type == CounterType.PING) {
            AttackSpeed.setPingPerSecond(AttackSpeed.getPingPerSecond() + 1);
        }
    }

    public static void decrease(CounterType type) {
        if (type == CounterType.CONNECT && AttackSpeed.getConnectPerSecond() != 0) {
            AttackSpeed.setConnectPerSecond(AttackSpeed.getConnectPerSecond() - 1);
        }
        if (type == CounterType.PING && AttackSpeed.getPingPerSecond() != 0) {
            AttackSpeed.setPingPerSecond(AttackSpeed.getPingPerSecond() - 1);
        }
    }

    public static int getTotalBots() {
        return totalBots;
    }

    public static void setTotalBots(int i) {
        totalBots = i;
    }

    public static boolean isUnderAttack() {
        return attackMode;
    }

    public static int getPingPerSecond() {
        return pingPerSecond;
    }

    public static void setPingPerSecond(int i) {
        pingPerSecond = i;
    }

    public static int getConnectPerSecond() {
        return connectPerSecond;
    }

    public static void setConnectPerSecond(int i) {
        connectPerSecond = i;
    }

    public static void setAttackMode(boolean bol) {
        attackMode = bol;
    }

    public static Reason getLastReason() {
        return lastReason;
    }

    public static void setLastReason(Reason lastReason) {
        AttackSpeed.lastReason = lastReason;
    }

    public static String getLastBot() {
        return lastBot;
    }

    public static void setLastBot(String lastBot) {
        AttackSpeed.lastBot = lastBot;
    }
}

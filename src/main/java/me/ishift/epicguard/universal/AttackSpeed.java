package me.ishift.epicguard.universal;

import me.ishift.epicguard.universal.types.AttackType;
import me.ishift.epicguard.universal.types.Reason;

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

    public static void increase(AttackType type) {
        if (type == AttackType.CONNECT) {
            AttackSpeed.setConnectPerSecond(AttackSpeed.getConnectPerSecond() + 1);
        }
        if (type == AttackType.PING) {
            AttackSpeed.setPingPerSecond(AttackSpeed.getPingPerSecond() + 1);
        }
    }

    public static void decrease(AttackType type) {
        if (type == AttackType.CONNECT && AttackSpeed.getConnectPerSecond() != 0) {
            AttackSpeed.setConnectPerSecond(AttackSpeed.getConnectPerSecond() - 1);
        }
        if (type == AttackType.PING && AttackSpeed.getPingPerSecond() != 0) {
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

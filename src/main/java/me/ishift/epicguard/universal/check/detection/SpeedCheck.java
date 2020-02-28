package me.ishift.epicguard.universal.check.detection;

import me.ishift.epicguard.bukkit.task.SecondTask;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.Check;
import me.ishift.epicguard.universal.types.Reason;

public class SpeedCheck extends Check {
    private static int connectPerSecond = 0;
    private static int pingPerSecond = 0;
    private static int totalBots = 0;
    private static boolean attackMode = false;

    public SpeedCheck() {
        super(Reason.ATTACK, false);
    }

    @Override
    public boolean perform(String address, String nickname) {
        if (SpeedCheck.getConnectPerSecond() > Config.connectSpeed || SpeedCheck.getPingPerSecond() > Config.pingSpeed) {
            SpeedCheck.setAttackMode(true);
        }

        return isUnderAttack();
    }

    public static void reset() {
        setAttackMode(false);
        SecondTask.setTime(0);
        setTotalBots(0);
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
}

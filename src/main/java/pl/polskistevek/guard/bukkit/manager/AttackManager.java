package pl.polskistevek.guard.bukkit.manager;

import pl.polskistevek.guard.bukkit.BukkitMain;

public class AttackManager {
    public static int joinPerSecond = 0;
    public static int connectPerSecond = 0;
    public static int pingPerSecond = 0;
    public static boolean attackMode = false;

    public enum AttackType {
        PING,
        CONNECT,
        JOIN
    }

    public static boolean check(AttackType type) {
        if (type == AttackType.PING) {
            if (pingPerSecond > BukkitMain.PING_SPEED) {
                attackMode = true;
                return true;
            }
            return false;
        }
        if (type == AttackType.CONNECT) {
            if (pingPerSecond > BukkitMain.JOIN_SPEED) {
                attackMode = true;
                return true;
            }
            return false;
        }
        if (type == AttackType.JOIN) {
            if (pingPerSecond > BukkitMain.CONNECT_SPEED) {
                attackMode = true;
                return true;
            }
            return false;
        }
        return false;
    }
}

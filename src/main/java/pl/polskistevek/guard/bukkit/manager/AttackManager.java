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

    public static boolean check(AttackType type){
        if (type == AttackType.PING){
            attackMode = true;
            return pingPerSecond > BukkitMain.PING_SPEED;
        }
        if (type == AttackType.CONNECT){
            attackMode = true;
            return connectPerSecond > BukkitMain.JOIN_SPEED;
        }
        if (type == AttackType.JOIN){
            attackMode = true;
            return joinPerSecond > BukkitMain.CONNECT_SPEED;
        }
        return false;
    }
}

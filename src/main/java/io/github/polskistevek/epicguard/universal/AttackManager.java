package io.github.polskistevek.epicguard.universal;

import io.github.polskistevek.epicguard.universal.util.ServerType;

import java.util.ArrayList;
import java.util.List;

public class AttackManager {
    public static int joinPerSecond = 0;
    public static int connectPerSecond = 0;
    public static int pingPerSecond = 0;
    public static boolean attackMode = false;
    public static List<String> rejoinData = new ArrayList<>();

    public static ServerType serverType;

    public AttackManager(ServerType serverType) {
        AttackManager.serverType = serverType;
    }

    public static boolean isUnderAttack(){
        return attackMode;
    }

    public static void handleAttack(AttackType type) {
        if (type == AttackType.CONNECT){
            connectPerSecond++;
            if (connectPerSecond > ConfigProvider.CONNECT_SPEED) {
                attackMode = true;
            }
        }
        if (type == AttackType.PING){
            pingPerSecond++;
            if (pingPerSecond > ConfigProvider.PING_SPEED) {
                attackMode = true;
            }
        }
        if (type == AttackType.JOIN){
            joinPerSecond++;
            if (joinPerSecond > ConfigProvider.JOIN_SPEED) {
                attackMode = true;
            }
        }
    }
}

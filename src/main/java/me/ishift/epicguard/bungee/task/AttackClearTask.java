package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.universal.Config;

public class AttackClearTask implements Runnable {

    @Override
    public void run() {
        if (BungeeAttack.getConnectionPerSecond() < Config.CONNECT_SPEED || BungeeAttack.getPingPerSecond() < Config.PING_SPEED) {
            BungeeAttack.setAttack(false);
        }
    }
}

package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.universal.Config;

public class AttackClearTask implements Runnable {

    @Override
    public void run() {
        if (BungeeAttack.getConnectionPerSecond() < Config.connectSpeed || BungeeAttack.getPingPerSecond() < Config.pingSpeed) {
            BungeeAttack.setAttack(false);
            BungeeAttack.blockedBots = 0;
        }
    }
}

package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.util.BungeeAttack;

public class DisplayTask implements Runnable {
    @Override
    public void run() {
        if (BungeeAttack.getPingPerSecond() < 0) {
            BungeeAttack.pingPerSecond = 0;
        }
        if (BungeeAttack.getConnectionPerSecond() < 0) {
            BungeeAttack.connectionPerSecond = 0;
        }
    }
}

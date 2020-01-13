package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.universal.Config;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (AttackManager.getJoinPerSecond() < Config.JOIN_SPEED && AttackManager.getPingPerSecond() < Config.PING_SPEED && AttackManager.getConnectPerSecond() < Config.CONNECT_SPEED) {
            AttackManager.setAttackMode(false);
            DataFileManager.setBlockedBots(0);
        }
    }
}

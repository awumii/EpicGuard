package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.AttackManager;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (AttackManager.joinPerSecond < Config.JOIN_SPEED && AttackManager.pingPerSecond < Config.PING_SPEED && AttackManager.connectPerSecond < Config.CONNECT_SPEED) {
            AttackManager.attackMode = false;
        }
    }
}

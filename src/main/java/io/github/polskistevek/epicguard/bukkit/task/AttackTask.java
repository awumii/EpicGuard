package io.github.polskistevek.epicguard.bukkit.task;

import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.bukkit.manager.AttackManager;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (AttackManager.joinPerSecond < GuardBukkit.JOIN_SPEED && AttackManager.pingPerSecond < GuardBukkit.PING_SPEED && AttackManager.connectPerSecond < GuardBukkit.CONNECT_SPEED) {
            AttackManager.attackMode = false;
        }
    }
}

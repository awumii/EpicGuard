package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.manager.AttackManager;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (AttackManager.joinPerSecond < GuardBukkit.JOIN_SPEED && AttackManager.pingPerSecond < GuardBukkit.PING_SPEED && AttackManager.connectPerSecond < GuardBukkit.CONNECT_SPEED) {
            AttackManager.attackMode = false;
        }
    }
}

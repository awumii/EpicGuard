package io.github.polskistevek.epicguard.bukkit.task;

import io.github.polskistevek.epicguard.bukkit.manager.AttackManager;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (!AttackManager.checkAttackStatus(AttackManager.AttackType.CONNECT) && !AttackManager.checkAttackStatus(AttackManager.AttackType.JOIN) && !AttackManager.checkAttackStatus(AttackManager.AttackType.PING)) {
            AttackManager.attackMode = false;
        }
    }
}

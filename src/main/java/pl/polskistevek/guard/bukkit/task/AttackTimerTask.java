package pl.polskistevek.guard.bukkit.task;

import pl.polskistevek.guard.bukkit.manager.AttackManager;

public class AttackTimerTask implements Runnable {

    @Override
    public void run() {
        if (!AttackManager.checkAttackStatus(AttackManager.AttackType.CONNECT) && !AttackManager.checkAttackStatus(AttackManager.AttackType.JOIN) && !AttackManager.checkAttackStatus(AttackManager.AttackType.PING)) {
            AttackManager.attackMode = false;
        }
    }
}

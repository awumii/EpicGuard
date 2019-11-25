package pl.polskistevek.guard.bukkit.task;

import pl.polskistevek.guard.bukkit.manager.AttackManager;

public class AttackTimerTask implements Runnable {

    @Override
    public void run() {
        if (!AttackManager.check(AttackManager.AttackType.CONNECT) && !AttackManager.check(AttackManager.AttackType.JOIN) && !AttackManager.check(AttackManager.AttackType.PING)) {
            AttackManager.attackMode = false;
        }
    }
}

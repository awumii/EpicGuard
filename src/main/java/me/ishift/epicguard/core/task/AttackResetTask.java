package me.ishift.epicguard.core.task;

import me.ishift.epicguard.core.EpicGuard;

public class AttackResetTask implements Runnable {
    private final EpicGuard epicGuard;

    public AttackResetTask(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public void run() {
        if (this.epicGuard.getConnectionPerSecond() < this.epicGuard.getConfig().maxCps) {
            this.epicGuard.setAttack(false);
        }
    }
}

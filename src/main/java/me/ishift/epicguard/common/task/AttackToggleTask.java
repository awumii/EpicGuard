package me.ishift.epicguard.common.task;

import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.antibot.AttackManager;

public class AttackToggleTask implements Runnable {
    private final AttackManager attackManager;

    public AttackToggleTask(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    @Override
    public void run() {
        if (this.attackManager.getConnectPerSecond() < Configuration.connectSpeed) {
            this.attackManager.reset();
        }
    }
}

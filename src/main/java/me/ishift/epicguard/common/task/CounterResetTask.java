package me.ishift.epicguard.common.task;

import me.ishift.epicguard.common.antibot.AttackManager;

public class CounterResetTask implements Runnable {
    private final AttackManager attackManager;

    public CounterResetTask(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    @Override
    public void run() {
        this.attackManager.setConnectPerSecond(0);
    }
}

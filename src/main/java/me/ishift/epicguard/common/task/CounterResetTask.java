package me.ishift.epicguard.common.task;

import me.ishift.epicguard.common.detection.AttackManager;

public class CounterResetTask implements Runnable {
    @Override
    public void run() {
        AttackManager.setConnectPerSecond(0);
    }
}

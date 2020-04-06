package me.ishift.epicguard.common.task;

import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.detection.AttackManager;

public class AttackToggleTask implements Runnable {
    @Override
    public void run() {
        if (AttackManager.getConnectPerSecond() < Configuration.connectSpeed) {
            AttackManager.reset();
        }
    }
}

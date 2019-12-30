package io.github.polskistevek.epicguard.task;

import io.github.polskistevek.epicguard.universal.AttackManager;
import io.github.polskistevek.epicguard.universal.ConfigProvider;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (AttackManager.joinPerSecond < ConfigProvider.JOIN_SPEED && AttackManager.pingPerSecond < ConfigProvider.PING_SPEED && AttackManager.connectPerSecond < ConfigProvider.CONNECT_SPEED) {
            AttackManager.attackMode = false;
        }
    }
}

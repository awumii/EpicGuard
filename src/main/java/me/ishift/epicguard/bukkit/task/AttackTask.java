package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.AttackSpeed;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (AttackSpeed.getPingPerSecond() < Config.pingSpeed && AttackSpeed.getConnectPerSecond() < Config.connectSpeed) {
            if (AttackSpeed.isUnderAttack()) AttackSpeed.reset();
        }
    }
}

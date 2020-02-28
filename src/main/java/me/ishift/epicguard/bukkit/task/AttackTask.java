package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (SpeedCheck.getPingPerSecond() < Config.pingSpeed && SpeedCheck.getConnectPerSecond() < Config.connectSpeed) {
            if (SpeedCheck.isUnderAttack()) SpeedCheck.reset();
        }
    }
}

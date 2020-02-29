package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;

public class AttackClearTask implements Runnable {

    @Override
    public void run() {
        if (SpeedCheck.getConnectPerSecond() < Config.connectSpeed || SpeedCheck.getPingPerSecond() < Config.pingSpeed) {
            SpeedCheck.setAttackMode(false);
            SpeedCheck.setTotalBots(0);
        }

        GuardBungee.getInstance().getProxy().getPlayers().forEach(proxiedPlayer -> StorageManager.whitelist(proxiedPlayer.getAddress().getAddress().getHostAddress()));
    }
}

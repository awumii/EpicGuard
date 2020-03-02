package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.AttackSpeed;

public class AttackClearTask implements Runnable {

    @Override
    public void run() {
        if (AttackSpeed.getConnectPerSecond() < Config.connectSpeed || AttackSpeed.getPingPerSecond() < Config.pingSpeed) {
            AttackSpeed.setAttackMode(false);
            AttackSpeed.setTotalBots(0);
        }

        GuardBungee.getInstance().getProxy().getPlayers().forEach(proxiedPlayer -> StorageManager.whitelist(proxiedPlayer.getAddress().getAddress().getHostAddress()));
    }
}

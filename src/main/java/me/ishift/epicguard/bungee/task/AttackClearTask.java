package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;

public class AttackClearTask implements Runnable {

    @Override
    public void run() {
        if (BungeeAttack.getConnectionPerSecond() < Config.connectSpeed || BungeeAttack.getPingPerSecond() < Config.pingSpeed) {
            BungeeAttack.setAttack(false);
            BungeeAttack.setBlockedBots(0);
        }

        GuardBungee.getInstance().getProxy().getPlayers().forEach(proxiedPlayer -> StorageManager.whitelist(proxiedPlayer.getAddress().getAddress().getHostAddress()));
    }
}

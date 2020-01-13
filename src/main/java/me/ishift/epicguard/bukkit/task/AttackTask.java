package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.util.Notificator;
import me.ishift.epicguard.universal.Config;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (AttackManager.getConnectPerSecond() < 0) {
            AttackManager.setConnectPerSecond(0);
        }
        if (AttackManager.getJoinPerSecond() < Config.JOIN_SPEED && AttackManager.getPingPerSecond() < Config.PING_SPEED && AttackManager.getConnectPerSecond() < Config.CONNECT_SPEED) {
            if (AttackManager.isUnderAttack()) {
                AttackManager.setAttackMode(false);
                Notificator.title("&cATTACK HAS BEEN FINISHED", "&7Bots blocked during last attack: &4" + AttackManager.getTotalBots());
                HeuristicsTask.setRecord(0);
                AttackManager.setTotalBots(0);
            }
        }
    }
}

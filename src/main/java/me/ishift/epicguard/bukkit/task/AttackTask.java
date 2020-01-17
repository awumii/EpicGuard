package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.manager.BlacklistManager;
import me.ishift.epicguard.bukkit.util.Notificator;
import me.ishift.epicguard.universal.Config;

public class AttackTask implements Runnable {

    @Override
    public void run() {
        if (AttackManager.getConnectPerSecond() < 0) {
            AttackManager.setConnectPerSecond(0);
        }
        if (AttackManager.getJoinPerSecond() < Config.joinSpeed && AttackManager.getPingPerSecond() < Config.pingSpeed && AttackManager.getConnectPerSecond() < Config.connectSpeed) {
            if (AttackManager.isUnderAttack()) {
                AttackManager.setAttackMode(false);
                Notificator.title("&cATTACK HAS BEEN FINISHED", "&7Bots blocked during last attack: &4" + AttackManager.getTotalBots());
                Notificator.broadcast("&7DONE: Blocked &e" + AttackManager.getTotalBots() + " &7bots during last &e" + HeuristicsTask.getTime() + " seconds&7.");
                Notificator.broadcast("&8(&4Blacklist&8) &7Changed: &6" + (BlacklistManager.blacklist.size() - HeuristicsTask.getBlacklistInc()) + " &8-> &6" + BlacklistManager.blacklist.size() + " &8(&c+" + HeuristicsTask.getBlacklistInc() + "&8).");
                HeuristicsTask.setRecord(0);
                HeuristicsTask.setTime(0);
                HeuristicsTask.setBlacklistInc(0);
                AttackManager.setTotalBots(0);
            }
        }
    }
}

package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.util.misc.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.misc.Notificator;

public class BetaTask implements Runnable {
    @Override
    public void run() {
        String c = "";
        String p = "";
        if (AttackManager.getConnectPerSecond() < 50) c = "&a";
        if (AttackManager.getConnectPerSecond() > 50) c = "&e";
        if (AttackManager.getConnectPerSecond() > 200) c = "&c";

        if (AttackManager.getPingPerSecond() < 50) p = "&a";
        if (AttackManager.getPingPerSecond() > 50) p = "&e";
        if (AttackManager.getPingPerSecond() > 200) p = "&c";
        Notificator.action(MessagesBukkit.PREFIX + c + AttackManager.getConnectPerSecond() + "&7/" + c + "cps &8| " + p + AttackManager.getPingPerSecond() + "&7/" + p + "pps &8| &7Blocked: " + c + AttackManager.getTotalBots() + " &8| &7Duration: &e" + HeuristicsTask.getTime() + "sec &8| " + (AttackManager.isUnderAttack() ? "&cAttack Detected" : "&aNo Attack"));
    }
}

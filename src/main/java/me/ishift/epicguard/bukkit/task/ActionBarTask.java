package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.util.misc.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.misc.Notificator;

public class ActionBarTask implements Runnable {
    @Override
    public void run() {
        String color1 = "";
        String color2 = "";
        if (AttackManager.getConnectPerSecond() < 80) color1 = "&a";
        if (AttackManager.getConnectPerSecond() > 80) color1 = "&e";
        if (AttackManager.getConnectPerSecond() > 500) color1 = "&c";

        if (AttackManager.getPingPerSecond() < 80) color2 = "&a";
        if (AttackManager.getPingPerSecond() > 80) color2 = "&e";
        if (AttackManager.getPingPerSecond() > 500) color2 = "&c";
        Notificator.action(MessagesBukkit.PREFIX + color1 + AttackManager.getConnectPerSecond() + "&7/" + color1 + "cps &8| " + color2 + AttackManager.getPingPerSecond() + "&7/" + color2 + "pps &8| &7Blocked: " + color1 + AttackManager.getTotalBots() + " &8| &7Duration: &e" + HeuristicsTask.getTime() + "sec &8| " + (AttackManager.isUnderAttack() ? "&cAttack Detected" : "&aNo Attack"));
    }
}

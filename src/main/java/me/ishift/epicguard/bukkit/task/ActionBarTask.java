package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.util.misc.Notificator;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.AttackSpeed;

public class ActionBarTask implements Runnable {
    @Override
    public void run() {
        String color1 = "";
        String color2 = "";
        if (AttackSpeed.getConnectPerSecond() < 80) color1 = "&a";
        if (AttackSpeed.getConnectPerSecond() > 80) color1 = "&e";
        if (AttackSpeed.getConnectPerSecond() > 500) color1 = "&c";

        if (AttackSpeed.getPingPerSecond() < 80) color2 = "&a";
        if (AttackSpeed.getPingPerSecond() > 80) color2 = "&e";
        if (AttackSpeed.getPingPerSecond() > 500) color2 = "&c";
        Notificator.action(Messages.prefix + color1 + AttackSpeed.getConnectPerSecond() + "&7/" + color1 + "cps &8| " + color2 + AttackSpeed.getPingPerSecond() + "&7/" + color2 + "pps &8| &7Blocked: " + color1 + AttackSpeed.getTotalBots() + " &8| " + (AttackSpeed.isUnderAttack() ? "&cAttack Detected" : "&aNo Attack"));
    }
}

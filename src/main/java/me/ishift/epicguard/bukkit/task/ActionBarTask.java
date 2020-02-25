package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.bukkit.util.misc.Notificator;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;

public class ActionBarTask implements Runnable {
    @Override
    public void run() {
        String color1 = "";
        String color2 = "";
        if (SpeedCheck.getConnectPerSecond() < 80) color1 = "&a";
        if (SpeedCheck.getConnectPerSecond() > 80) color1 = "&e";
        if (SpeedCheck.getConnectPerSecond() > 500) color1 = "&c";

        if (SpeedCheck.getPingPerSecond() < 80) color2 = "&a";
        if (SpeedCheck.getPingPerSecond() > 80) color2 = "&e";
        if (SpeedCheck.getPingPerSecond() > 500) color2 = "&c";
        Notificator.action(Messages.prefix + color1 + SpeedCheck.getConnectPerSecond() + "&7/" + color1 + "cps &8| " + color2 + SpeedCheck.getPingPerSecond() + "&7/" + color2 + "pps &8| &7Blocked: " + color1 + SpeedCheck.getTotalBots() + " &8| &7Duration: &e" + HeuristicsTask.getTime() + "sec &8| " + (SpeedCheck.isUnderAttack() ? "&cAttack Detected" : "&aNo Attack"));
    }
}

package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DisplayTask implements Runnable {
    private static int time = 0;

    @Override
    public void run() {
        if (SpeedCheck.isUnderAttack()) {
            time++;
        } else {
            time = 0;
        }

        String color1 = "";
        String color2 = "";
        if (SpeedCheck.getConnectPerSecond() < 80) color1 = "&a";
        if (SpeedCheck.getConnectPerSecond() > 80) color1 = "&e";
        if (SpeedCheck.getConnectPerSecond() > 500) color1 = "&c";

        if (SpeedCheck.getPingPerSecond() < 80) color2 = "&a";
        if (SpeedCheck.getPingPerSecond() > 80) color2 = "&e";
        if (SpeedCheck.getPingPerSecond() > 500) color2 = "&c";

        if (GuardBungee.status) {
            for (ProxiedPlayer player : GuardBungee.getInstance().getProxy().getPlayers()) {
                if (player.getPermissions().contains("epicguard.admin")) {
                    BungeeUtil.sendActionBar(player, Messages.prefix + color1 + SpeedCheck.getConnectPerSecond() + "&7/" + color1 + "cps &8| " + color2 + SpeedCheck.getPingPerSecond() + "&7/" + color2 + "pps &8| &7Blocked: " + color1 + SpeedCheck.getTotalBots() + " &8| &7Duration: &e" + time + "sec &8| " + (SpeedCheck.isUnderAttack() ? "&cAttack Detected" : "&aNo Attack"));
                }
            }
        }
    }
}

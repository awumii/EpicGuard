package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.AttackSpeed;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DisplayTask implements Runnable {
    private static int time = 0;

    @Override
    public void run() {
        if (AttackSpeed.isUnderAttack()) {
            time++;
        } else {
            time = 0;
        }

        String color1 = "";
        String color2 = "";
        if (AttackSpeed.getConnectPerSecond() < 80) color1 = "&a";
        if (AttackSpeed.getConnectPerSecond() > 80) color1 = "&e";
        if (AttackSpeed.getConnectPerSecond() > 500) color1 = "&c";

        if (AttackSpeed.getPingPerSecond() < 80) color2 = "&a";
        if (AttackSpeed.getPingPerSecond() > 80) color2 = "&e";
        if (AttackSpeed.getPingPerSecond() > 500) color2 = "&c";

        if (GuardBungee.status) {
            for (ProxiedPlayer player : GuardBungee.getInstance().getProxy().getPlayers()) {
                if (player.getPermissions().contains("epicguard.admin")) {
                    BungeeUtil.sendActionBar(player, Messages.prefix + color1 + AttackSpeed.getConnectPerSecond() + "&7/" + color1 + "cps &8| " + color2 + AttackSpeed.getPingPerSecond() + "&7/" + color2 + "pps &8| &7Blocked: " + color1 + AttackSpeed.getTotalBots() + " &8| &7Duration: &e" + time + "sec &8| " + (AttackSpeed.isUnderAttack() ? "&cAttack Detected" : "&aNo Attack"));
                }
            }
        }
    }
}

package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.bungee.util.MessagesBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DisplayTask implements Runnable {
    private static int time = 0;

    @Override
    public void run() {
        if (BungeeAttack.isAttack()) {
            time++;
        } else {
            time = 0;
        }

        String color1 = "";
        String color2 = "";
        if (BungeeAttack.getConnectionPerSecond() < 80) color1 = "&a";
        if (BungeeAttack.getConnectionPerSecond() > 80) color1 = "&e";
        if (BungeeAttack.getConnectionPerSecond() > 500) color1 = "&c";

        if (BungeeAttack.getPingPerSecond() < 80) color2 = "&a";
        if (BungeeAttack.getPingPerSecond() > 80) color2 = "&e";
        if (BungeeAttack.getPingPerSecond() > 500) color2 = "&c";

        if (GuardBungee.status) {
            for (ProxiedPlayer player : GuardBungee.getInstance().getProxy().getPlayers()) {
                if (player.getPermissions().contains("epicguard.admin")) {
                    BungeeUtil.sendActionBar(player, MessagesBungee.prefix + color1 + BungeeAttack.getConnectionPerSecond() + "&7/" + color1 + "cps &8| " + color2 + BungeeAttack.getPingPerSecond() + "&7/" + color2 + "pps &8| &7Blocked: " + color1 + BungeeAttack.getBlockedBots() + " &8| &7Duration: &e" + time + "sec &8| " + (BungeeAttack.isAttack() ? "&cAttack Detected" : "&aNo Attack"));
                }
            }
        }
    }
}

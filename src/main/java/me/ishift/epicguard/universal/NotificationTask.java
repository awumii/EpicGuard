package me.ishift.epicguard.universal;

import me.ishift.epicguard.bukkit.util.misc.Notificator;
import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeUtil;

public class NotificationTask implements Runnable {
    private Server server;

    public NotificationTask(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        final String message = "&a" + AttackSpeed.getConnectPerSecond() + "&7/&acps &8| &c" + AttackSpeed.getTotalBots() + " &7blocked &8» &f" + AttackSpeed.getLastBot() + " &8[&c" +  AttackSpeed.getLastReason().name() + "&8]";

        if (this.server == Server.SPIGOT) {
            Notificator.action(message);
        }

        if (this.server == Server.BUNGEE) {
            if (GuardBungee.status) {
                GuardBungee.getInstance().getProxy().getPlayers().stream().filter(player -> player.getPermissions().contains("epicguard.admin")).forEach(player -> BungeeUtil.sendActionBar(player, message));
            }
        }
    }

    public enum Server {
        SPIGOT,
        BUNGEE
    }
}

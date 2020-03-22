/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.ishift.epicguard.common.task;

import me.ishift.epicguard.bukkit.util.Notificator;
import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.common.Messages;
import me.ishift.epicguard.common.detection.AttackSpeed;

public class NotificationTask implements Runnable {
    private final Server server;

    public NotificationTask(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        final String message = Messages.prefix + "&cConnections per second: &6" + AttackSpeed.getConnectPerSecond();
        final String title = "&c" + AttackSpeed.getTotalBots() + " blocked connections";
        final String subtitle = "&7Server is under &aattack...";

        if (this.server == Server.SPIGOT) {
            Notificator.action(message);
            Notificator.title(title, subtitle);
            return;
        }

        if (this.server == Server.BUNGEE && GuardBungee.status) {
            GuardBungee.getInstance().getProxy().getPlayers()
                    .stream()
                    .filter(player -> player.getPermissions().contains("epicguard.status"))
                    .forEach(player -> {
                        BungeeUtil.sendActionBar(player, message);
                        if (AttackSpeed.isUnderAttack()) {
                            BungeeUtil.sendTitle(player, title, subtitle);
                        }
                    });
        }
    }

    public enum Server {
        SPIGOT,
        BUNGEE
    }
}

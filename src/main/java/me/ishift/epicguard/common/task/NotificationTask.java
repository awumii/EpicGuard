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
import me.ishift.epicguard.common.detection.AttackSpeed;

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

        if (this.server == Server.BUNGEE && GuardBungee.status) {
            GuardBungee.getInstance().getProxy().getPlayers()
                    .stream()
                    .filter(player -> player.getPermissions().contains("epicguard.admin"))
                    .forEach(player -> BungeeUtil.sendActionBar(player, message));
        }
    }

    public enum Server {
        SPIGOT,
        BUNGEE
    }
}

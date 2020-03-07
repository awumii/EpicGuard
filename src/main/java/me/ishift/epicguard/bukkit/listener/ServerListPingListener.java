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

package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.common.AttackSpeed;
import me.ishift.epicguard.common.BotCheck;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.types.CounterType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        BotCheck.addPing(event.getAddress().getHostAddress());
        AttackSpeed.increase(CounterType.PING);
        Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> AttackSpeed.decrease(CounterType.PING), 20L);

        if (AttackSpeed.getPingPerSecond() > Config.pingSpeed) {
            if (Config.bandwidthOptimizer) {
                event.setMotd("");
                event.setMaxPlayers(0);
            }
        }
    }
}

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

package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.common.detection.AttackSpeed;
import me.ishift.epicguard.common.detection.BotCheck;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.types.CounterType;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PingListener implements Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        BotCheck.addPing(event.getConnection().getAddress().getAddress().getHostAddress());
        AttackSpeed.increase(CounterType.PING);

        if (AttackSpeed.isUnderAttack()) {
            if (Config.bandwidthOptimizer) {
                event.setResponse(null);
            }
        }
    }
}

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

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.AttackSpeed;
import me.ishift.epicguard.common.check.ServerListCheck;
import me.ishift.epicguard.common.types.CounterType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class ProxyPingListener implements Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        ServerListCheck.addAddress(event.getConnection().getAddress().getAddress().getHostAddress());
        AttackSpeed.increase(CounterType.PING);
        ProxyServer.getInstance().getScheduler().schedule(GuardBungee.getInstance(), () -> AttackSpeed.decrease(CounterType.PING), 1, TimeUnit.SECONDS);

        if (AttackSpeed.isUnderAttack()) {
            if (Config.bandwidthOptimizer) {
                final ServerPing response = event.getResponse();
                response.setDescriptionComponent(new TextComponent(""));
                event.setResponse(response);
            }
        }
    }
}

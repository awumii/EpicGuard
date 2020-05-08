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

import me.ishift.epicguard.bungee.EpicGuardBungee;
import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.antibot.Detection;
import me.ishift.epicguard.common.data.config.Messages;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLoginListener implements Listener {
    private final AttackManager attackManager;

    public PreLoginListener(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        final PendingConnection connection = event.getConnection();
        final String address = connection.getAddress().getAddress().getHostAddress();
        final String name = connection.getName();

        final Detection detection = this.attackManager.check(address, name);
        if (detection.isDetected()) {
            event.setCancelled(true);
            event.setCancelReason(TextComponent.fromLegacyText(detection.getReason().getMessage()));

            EpicGuardBungee.getInstance().getProxy().getPlayers().stream()
                    .filter(player -> EpicGuardBungee.getInstance().getStatusPlayers().contains(player.getUniqueId()))
                    .forEach(player -> BungeeUtil.sendActionBar(player, Messages.prefix + " &7CPS: &c" + this.attackManager.getConnectPerSecond() + "/s &8| &6" + name + " &8[&e" + address + "&8] - &7" + detection.getReason()));
        }
    }
}

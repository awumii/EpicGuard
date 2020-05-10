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

import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.util.ActionBarAPI;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.antibot.Detection;
import me.ishift.epicguard.common.data.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {
    private final AttackManager attackManager;

    public PlayerPreLoginListener(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        final String address = event.getAddress().getHostAddress();
        final String name = event.getName();

        final Detection detection = this.attackManager.check(address, name);
        if (detection.isDetected()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, detection.getReason().getMessage());

            final String message = Messages.prefix + " &7CPS: &c" + this.attackManager.getConnectPerSecond() + "/s &8| &6" + name + " &8[&e" + address + "&8] - &7" + detection.getReason();
            for (Player player : Bukkit.getOnlinePlayers()) {
                final User user = new User(name, this.attackManager);
                if (user.exists() && user.isNotifications()) {
                    ActionBarAPI.sendActionBar(player, message);
                }
            }
        }
    }
}

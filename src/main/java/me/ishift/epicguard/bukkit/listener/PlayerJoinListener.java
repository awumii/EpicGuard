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

import lombok.AllArgsConstructor;
import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

@AllArgsConstructor
public class PlayerJoinListener implements Listener {
    private final AttackManager manager;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        // Hiding join message from bots that may have bypassed.
        if (this.manager.isAttackMode()) {
            event.setJoinMessage(null);
        }

        final Player player = event.getPlayer();
        EpicGuardBukkit.getInstance().getUserManager().createUser(player);
        final User user = EpicGuardBukkit.getInstance().getUserManager().getUser(player);
        final String address = user.getAddress();

        final List<String> history = user.getAddressHistory();
        if (!history.contains(address)) {
            history.add(address);
        }
        user.setAddressHistory(history);

        if (Configuration.autoWhitelist) {
            Bukkit.getScheduler().runTaskLater(EpicGuardBukkit.getInstance(), () -> {
                if (player.isOnline()) {
                    this.manager.getStorageManager().getStorage().whitelist(address);
                }
            }, Configuration.autoWhitelistTime);
        }
    }
}

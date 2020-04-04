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

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.common.Configuration;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.StorageType;
import me.ishift.epicguard.common.data.storage.Flat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        EpicGuardBukkit.getInstance().getUserManager().addUser(player);
        final User user = EpicGuardBukkit.getInstance().getUserManager().getUser(player);
        final String address = user.getAddress();

        if (Configuration.autoWhitelist) {
            Bukkit.getScheduler().runTaskLater(EpicGuardBukkit.getInstance(), () -> {
                if (player.isOnline()) {
                    StorageManager.getStorage().whitelist(address);
                }
            }, Configuration.autoWhitelistTime * 20L);
        }

        if (StorageManager.getStorageType() == StorageType.FLAT) {
            final Flat flat = (Flat) StorageManager.getStorage();
            final List<String> history = flat.getFile().getStringList("address-history." + player.getName());
            if (!history.contains(address)) {
                history.add(address);
            }
            flat.getFile().set("address-history." + player.getName(), history);
            user.setAddressHistory(history);
        }
    }
}

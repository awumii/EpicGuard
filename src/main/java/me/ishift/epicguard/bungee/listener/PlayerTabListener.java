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

import me.ishift.epicguard.common.data.config.BungeeSettings;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerTabListener implements Listener {
    @EventHandler
    public void onTab(TabCompleteEvent event) {
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        final String message = event.getCursor();

        // Blocking TabComplete
        if (BungeeSettings.tabCompleteBlock) {
            event.setCancelled(true);
            return;
        }

        if (BungeeSettings.customTabComplete) {
            if (BungeeSettings.customTabCompleteBypass && player.hasPermission("epicguard.bypass.custom-tab-complete")) {
                return;
            }

            event.getSuggestions().clear();
            event.getSuggestions().addAll(BungeeSettings.customTabCompleteList);
        }
    }
}

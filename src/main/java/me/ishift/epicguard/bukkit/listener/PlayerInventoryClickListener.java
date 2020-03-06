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

import me.ishift.epicguard.bukkit.command.GuardGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        final Player p = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("EpicGuard Management Menu")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                return;
            }
            final ItemMeta im = event.getCurrentItem().getItemMeta();
            if (im == null) {
                return;
            }
            if (im.getDisplayName().contains("Player Manager Menu")) {
                GuardGui.showPlayers(p);
            }
        }
        if (event.getView().getTitle().equals("EpicGuard Player Manager")) {
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Back to main menu")) {
                GuardGui.showMain(p);
            }
            event.setCancelled(true);
        }
    }
}

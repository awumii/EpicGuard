/*
 * InventoryAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * InventoryAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.ishift.inventory.api;

import me.ishift.inventory.api.inventories.UnmodifiableInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import me.ishift.inventory.api.basic.Clickable;
import me.ishift.inventory.api.basic.Closeable;
import me.ishift.inventory.api.basic.BaseInventory;

public class InventoryListener implements Listener {
    private final InventoryManager manager;

    public InventoryListener(InventoryManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (event.getClickedInventory() == null || event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) {
            return;
        }

        BaseInventory inventory = this.manager.findByName(title);
        if (inventory instanceof Clickable) {
            ((Clickable) inventory).onClick(event);
        }
        if (inventory instanceof UnmodifiableInventory) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String title = event.getView().getTitle();
        BaseInventory inventory = this.manager.findByName(title);
        if (inventory instanceof Closeable) {
            ((Closeable) inventory).onClose(event);
        }
    }
}

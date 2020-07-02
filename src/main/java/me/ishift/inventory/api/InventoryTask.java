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

import me.ishift.inventory.api.basic.BaseInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InventoryTask implements Runnable {
    private final InventoryManager manager;

    public InventoryTask(InventoryManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            BaseInventory inventory = this.manager.findByName(player.getOpenInventory().getTitle());
            if (inventory != null) {
                if (inventory.isRefreshable()) {
                    this.manager.open(inventory.getId(), player);
                }
            }
        }
    }
}

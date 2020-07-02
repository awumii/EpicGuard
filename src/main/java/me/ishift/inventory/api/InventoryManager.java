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
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private final List<BaseInventory> inventories;

    public InventoryManager(JavaPlugin plugin) {
        this.inventories = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), plugin);
        Bukkit.getScheduler().runTaskTimer(plugin, new InventoryTask(this), 20L, 40L);
    }

    /**
     * Registering a new Inventory.
     *
     * @param inventory Implementation of the {@link BaseInventory}.
     */
    public void addInventory(BaseInventory inventory) {
        this.inventories.add(inventory);
    }

    /**
     * Opens the specified inventory.
     * Throws {@link UnknownIdException} if the inventory with the provided ID does not exist.
     *
     * @param id String ID of the inventory.
     * @param player The player to open the GUI.
     */
    public void open(String id, Player player) {
        BaseInventory inventory = this.findById(id);
        if (inventory == null) {
            throw new UnknownIdException("Can't find an Inventory with the provided ID '" + id + "'");
        }

        Inventory bukkitInventory = Bukkit.createInventory(player, inventory.getSize(), inventory.getTitle());
        player.openInventory(bukkitInventory);
        inventory.onOpen(player, bukkitInventory);
        player.updateInventory();
    }

    /**
     * Searches for the {@link BaseInventory} from the specified ID.
     *
     * @param id ID of the searched GUI.
     * @return The searched {@link BaseInventory}, or null if not found.
     */
    public BaseInventory findById(String id) {
        return this.inventories.stream()
                .filter(inventory -> inventory.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Searches for the {@link BaseInventory} from the item name.
     *
     * @param name Inventory title of the searched GUI.
     * @return The searched {@link BaseInventory}, or null if not found.
     */
    public BaseInventory findByName(String name) {
        return this.inventories.stream()
                .filter(inventory -> inventory.getTitle().equals(name))
                .findFirst()
                .orElse(null);
    }
}

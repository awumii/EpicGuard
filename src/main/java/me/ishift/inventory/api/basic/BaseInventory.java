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

package me.ishift.inventory.api.basic;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface BaseInventory {
    /**
     * @return Custom ID of the inventory.
     */
    String getId();

    /**
     * @return Slot size of the inventory.
     */
    int getSize();

    /**
     * @return Title of the inventory.
     */
    String getTitle();

    /**
     * If this is set to true, the inventory will
     * refresh every 2 seconds for all the players
     * who have this inventory opened.
     *
     * @return should inventory be refreshable?
     */
    default boolean isRefreshable() {
        return false;
    }

    /**
     * Method called when the inventory is opened.
     * Used to fill inventory with contents, etc.
     *
     * @param player Player who opened the inventory.
     * @param inventory The opened inventory.
     */
    void onOpen(Player player, Inventory inventory);
}
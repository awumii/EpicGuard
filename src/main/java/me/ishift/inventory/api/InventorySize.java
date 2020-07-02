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

public enum InventorySize {
    SMALLEST(9),
    SMALL(18),
    MEDIUM(27),
    BIG(36),
    HUGE(45),
    BIGGEST(54);

    private final int size;

    /**
     * @param size Size of the Inventory, must be from 9 to 54, and a multiple of 9.
     */
    InventorySize(int size) {
        this.size = size;
    }

    /**
     * @return Integer value of the specified {@link InventorySize}
     */
    public int getSize() {
        return size;
    }
}

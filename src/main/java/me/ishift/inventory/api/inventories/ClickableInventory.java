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

package me.ishift.inventory.api.inventories;

import me.ishift.inventory.api.InventorySize;
import me.ishift.inventory.api.basic.BaseInventory;
import me.ishift.inventory.api.basic.Clickable;
import me.ishift.inventory.api.util.MessageUtil;

public abstract class ClickableInventory implements BaseInventory, Clickable {
    private final String title;
    private final int size;
    private final String id;

    public ClickableInventory(String title, String id, InventorySize size) {
        this.title = MessageUtil.fixColor(title);
        this.size = size.getSize();
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getId() {
        return this.id;
    }
}

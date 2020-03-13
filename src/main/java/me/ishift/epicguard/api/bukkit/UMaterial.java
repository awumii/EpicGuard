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

package me.ishift.epicguard.api.bukkit;

import org.bukkit.Material;

public enum UMaterial {
    CHEST_MINECART("STORAGE_MINECART", "CHEST_MINECART"),
    FIREWORK("FIREWORK", "FIREWORK_ROCKET"),
    ENCHANTING_TABLE("ENCHANTMENT_TABLE", "ENCHANTING_TABLE"),
    SKULL("SKULL_ITEM", "PLAYER_HEAD"),
    SIGN("SIGN", "OAK_SIGN"),
    COMMAND_BLOCK("COMMAND", "COMMAND_BLOCK"),
    CLOCK("WATCH", "CLOCK"),
    EXP_BOTTLE("EXP_BOTTLE", "EXPERIENCE_BOTTLE"),
    BOOK_AND_QUILL("BOOK_AND_QUILL", "WRITABLE_BOOK"),
    CRAFTING("WORKBENCH", "CRAFTING_TABLE"),
    NETHER_BRICK("NETHER_BRICK_ITEM", "NETHER_BRICK"),
    FENCE_GATE("FENCE_GATE", "OAK_FENCE_GATE");

    private Material material;

    /**
     * @param legacy Legacy enum material name.
     * @param newest New enum material name.
     */
    UMaterial(String legacy, String newest) {
        if (Reflection.isOldVersion()) {
            this.material = Material.getMaterial(legacy);
            return;
        }
        this.material = Material.getMaterial(newest);
    }

    /**
     * @return Material for current server version.
     */
    public Material getMaterial() {
        return this.material;
    }
}

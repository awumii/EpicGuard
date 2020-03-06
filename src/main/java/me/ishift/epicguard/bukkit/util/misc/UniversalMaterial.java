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

package me.ishift.epicguard.bukkit.util.misc;

import me.ishift.epicguard.bukkit.util.server.Reflection;
import org.bukkit.Material;

public enum UniversalMaterial {
    CLOCK("WATCH", "CLOCK"),
    EXP_BOTTLE("EXP_BOTTLE", "EXPERIENCE_BOTTLE"),
    BOOK_AND_QUILL("BOOK_AND_QUILL", "WRITABLE_BOOK"),
    CRAFTING("WORKBENCH", "CRAFTING_TABLE"),
    NETHER_BRICK("NETHER_BRICK_ITEM", "NETHER_BRICK"),
    FENCE_GATE("FENCE_GATE", "OAK_FENCE_GATE");

    private String legacy;
    private String current;

    UniversalMaterial(String legacy, String current) {
        this.legacy = legacy;
        this.current = current;
    }

    public static Material get(UniversalMaterial material) {
        if (Reflection.isOldVersion()) {
            return Material.getMaterial(material.getLegacy());
        }
        return Material.getMaterial(material.getCurrent());
    }

    public String getCurrent() {
        return this.current;
    }

    public String getLegacy() {
        return this.legacy;
    }
}

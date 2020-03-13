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

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SkullUtil {
    /**
     * @param player Skull owner.
     * @param name Name of the ItemStack.
     * @param lore Lore of the ItemStack.
     * @return Player's skull ItemStack.
     */
    public static ItemStack getSkull(Player player, String name, List<String> lore) {
        short data = 0;
        if (Reflection.isOldVersion()) {
            data = 3;
        }

        return new ItemBuilder(UMaterial.SKULL.getMaterial(), data)
                .setTitle(name)
                .setLore(lore)
                .setSkullOwner(player.getName())
                .build();
    }
}

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

import me.ishift.epicguard.api.ChatUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemBuilder {
    private final short data;
    private final List<String> lore;
    private final HashMap<Enchantment, Integer> enchants;
    private Material material;
    private int amount;
    private String title;
    private Color color;
    private String skullOwner;

    /**
     * Creating ItemBuilder only with Material type.
     *
     * @param material Item type.
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    /**
     * Creating ItemBuilder with type and specified amount.
     *
     * @param material Item type.
     * @param amount Item amount.
     */
    public ItemBuilder(Material material, int amount) {
        this(material, amount, (short) 0);
    }

    /**
     * Creating ItemBuilder with type and data.
     *
     * @param material Item type.
     * @param data Item data
     */
    public ItemBuilder(Material material, short data) {
        this(material, 1, data);
    }

    /**
     * Creating ItemBuilder with type, amount, and data.
     *
     * @param material Item type.
     * @param amount Item amount.
     * @param data Item data.
     */
    public ItemBuilder(Material material, int amount, short data) {
        this.title = null;
        this.lore = new ArrayList<>();
        this.enchants = new HashMap<>();
        this.material = material;
        this.amount = amount;
        this.data = data;
    }

    /**
     * Setting item title.
     *
     * @param title Item title.
     * @return Current ItemBuilder object.
     */
    public ItemBuilder setTitle(String title) {
        this.title = ChatUtil.fix(title);
        return this;
    }

    /**
     * @param lore String list to set as the lore.
     * @return Current ItemBuilder object.
     */
    public ItemBuilder setLore(List<String> lore) {
        this.lore.addAll(lore);
        return this;
    }

    /**
     * @param lore Adding single string to item lore.
     * @return Current ItemBuilder object.
     */
    public ItemBuilder addLore(String lore) {
        this.lore.add(ChatUtil.fix(lore));
        return this;
    }

    /**
     * @param enchant Enchantment object
     * @param level Level of enchantment
     * @return Current ItemBuilder object.
     */
    public ItemBuilder addEnchantment(Enchantment enchant, int level) {
        this.enchants.remove(enchant);
        this.enchants.put(enchant, level);
        return this;
    }

    /**
     * Can be used only on LEATHER armor.
     *
     * @param color Color for the armor.
     * @return Current ItemBuilder object.
     */
    public ItemBuilder setColor(Color color) {
        if (!this.material.name().contains("LEATHER_")) {
            throw new IllegalArgumentException("Can't set color for NON-LEATHER material.");
        }
        this.color = color;
        return this;
    }

    public ItemBuilder setSkullOwner(String skullOwner) {
        if (this.material != UMaterial.SKULL.get()) {
            throw new IllegalArgumentException("Can't set skull owner for material that is not a skull!");
        }
        this.skullOwner = skullOwner;
        return this;
    }

    /**
     * Building ItemBuilder to an ItemStack.
     *
     * @return Complete ItemStack with all data and meta.
     */
    public ItemStack build() {
        final ItemStack item = new ItemStack(this.material, this.amount, this.data);
        final ItemMeta meta = item.getItemMeta();
        if (this.title != null && meta != null) {
            meta.setDisplayName(this.title);
        }
        if (!this.lore.isEmpty() && meta != null) {
            meta.setLore(this.lore);
        }
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(this.color);
        }
        if (this.skullOwner != null) {
            ((SkullMeta) meta).setOwner(this.skullOwner);
        }
        item.setItemMeta(meta);
        item.addUnsafeEnchantments(this.enchants);
        return item;
    }
}
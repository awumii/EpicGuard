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

package me.ishift.epicguard.bukkit.command;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.util.ItemBuilder;
import me.ishift.epicguard.bukkit.util.SkullUtil;
import me.ishift.epicguard.bukkit.util.UMaterial;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.util.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuardGui {
    public static final Inventory INVENTORY_MANAGEMENT = Bukkit.createInventory(null, 27, "EpicGuard | Management Menu");
    public static final Inventory INVENTORY_PLAYER = Bukkit.createInventory(null, 54, "EpicGuard | Player Manager");

    /**
     * Opens main EpicGuard management GUI.
     *
     * @param player Target player.
     */
    public static void showMain(Player player) {
        final ItemStack i1 = new ItemBuilder(UMaterial.CHEST_MINECART.getMaterial())
                .setTitle("&cServer status.")
                .addLore("&7See status of your server.")
                .addLore("")
                .addLore("&6Status:")
                .addLore("  &7Attack&8: " + (AttackManager.isUnderAttack() ? "&cDetected!" : "&aNot detected."))
                .addLore("  &7Connections&8: &c" + AttackManager.getConnectPerSecond() + "/s")
                .addLore("")
                .addLore("&6Storage manager:")
                .addLore("  &7Blacklisted IPs&8: &c" + StorageManager.getStorage().getBlacklist().size())
                .addLore("  &7Whitelisted IPs&8: &a" + StorageManager.getStorage().getWhitelist().size())
                .build();

        final ItemStack i2 = new ItemBuilder(UMaterial.BOOK_AND_QUILL.getMaterial())
                .setTitle("&cPlugin Information")
                .addLore("&7Plugin author, version etc.")
                .addLore("")
                .addLore("&6Plugin:")
                .addLore("  &7Name&8: &aEpicGuard")
                .addLore("  &7Version&8: &e" + EpicGuardBukkit.getInstance().getDescription().getVersion())
                .addLore("  &7Authors&8: &eiShift, ruzekh")
                .addLore("")
                .addLore("&6Support:")
                .addLore("  &7Dev's Discord&8: &ciShift#0524")
                .addLore("  &7Support Server&8: &cdiscord.gg/VkfhFCv")
                .build();

        final ItemStack i3 = new ItemBuilder(Material.COMPASS)
                .setTitle("&cPlayer management menu.")
                .addLore("&7Get to know about your players.")
                .addLore("")
                .addLore("&7You can see &6IP + history, country, city &7etc.")
                .addLore("&fLeft Click &7to show GUI with all players.")
                .build();

        final ItemStack i4 = new ItemBuilder(Material.BARRIER)
                .setTitle("&cComing soon...")
                .addLore("")
                .addLore("&7GUI will be reworked in the upcoming updates!")
                .build();

        INVENTORY_MANAGEMENT.setItem(10, i1);
        INVENTORY_MANAGEMENT.setItem(12, i2);
        INVENTORY_MANAGEMENT.setItem(14, i3);
        INVENTORY_MANAGEMENT.setItem(16, i4);
        player.openInventory(INVENTORY_MANAGEMENT);
    }

    /**
     * Opens EpicGuard player management GUI
     *
     * @param player Target player.
     */
    public static void showPlayers(Player player) {
        int i = 0;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            final List<String> lore = new ArrayList<>();
            final User user = EpicGuardBukkit.getInstance().getUserManager().getUser(player1);

            lore.add("");
            lore.add(MessageHelper.color("&6Basic Information:"));
            lore.add(MessageHelper.color("  &7Name&8: &f" + player1.getName()));
            lore.add(MessageHelper.color("  &7UUID&8: &f" + player1.getUniqueId()));
            lore.add(MessageHelper.color("  &7OP&8: " + (player1.isOp() ? "&aYes" : "&cNo")));
            lore.add(MessageHelper.color("  &7Country&8: &f" + AttackManager.getGeoApi().getCountryCode(user.getAddress())));
            lore.add(MessageHelper.color("  &7City&8: &f" + AttackManager.getGeoApi().getCity(user.getAddress())));

            if (!user.getAddressHistory().isEmpty()) {
                lore.add("");
                lore.add(MessageHelper.color("&6IP History:"));
                user.getAddressHistory().forEach(address -> lore.add(MessageHelper.color("  &7- &f" + address + (user.getAddress().equals(address) ? " &8(&aCurrent&8)" : ""))));
            }

            final ItemStack itemStack = SkullUtil.getSkull(player1, (player1.isOp() ? "&c[OP] " : "&a") + player1.getName(), lore);
            INVENTORY_PLAYER.setItem(i, itemStack);
            i++;
        }

        final ItemStack back = new ItemBuilder(UMaterial.FENCE_GATE.getMaterial()).setTitle("&cBack to main menu").addLore("&7Click to go back.").build();
        INVENTORY_PLAYER.setItem(53, back);
        player.openInventory(INVENTORY_PLAYER);
    }
}

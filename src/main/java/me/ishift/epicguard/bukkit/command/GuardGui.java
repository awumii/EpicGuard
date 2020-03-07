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

import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.api.bukkit.UMaterial;
import me.ishift.epicguard.api.bukkit.ItemBuilder;
import me.ishift.epicguard.common.AttackSpeed;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.StorageManager;
import me.ishift.epicguard.api.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuardGui {
    public static final Inventory INVENTORY_MANAGEMENT = Bukkit.createInventory(null, 27, "EpicGuard Management Menu");
    public static final Inventory INVENTORY_PLAYER = Bukkit.createInventory(null, 36, "EpicGuard Player Manager");

    /**
     * Opens main EpicGuard management GUI.
     *
     * @param player Target player.
     */
    public static void showMain(Player player) {
        final ItemStack i1 = new ItemBuilder(UMaterial.COMMAND_BLOCK.getMaterial())
                .setTitle("&cServer status.")
                .addLore("&7See status of your server.")
                .addLore("")
                .addLore("&6Status:")
                .addLore("  &7Attack&8: " + (AttackSpeed.isUnderAttack() ? "&cDetected!" : "&aNot detected."))
                .addLore("  &7Connections&8: &c" + AttackSpeed.getConnectPerSecond() + "/s")
                .addLore("  &7Pings&8: &c" + AttackSpeed.getPingPerSecond() + "/s")
                .build();

        final ItemStack i2 = new ItemBuilder(Material.COMPASS)
                .setTitle("&cPlayer management menu.")
                .addLore("")
                .addLore("&7Get to know about your players.")
                .addLore("&fLeft Click &7to show GUI with all players.")
                .build();

        final ItemStack i3 = new ItemBuilder(UMaterial.SIGN.getMaterial())
                .setTitle("&cAntibot Statistics")
                .addLore("&7See statistics about collected data.")
                .addLore("")
                .addLore("&6Connections:")
                .addLore("  &7Checked connections&8: &e" + StorageManager.getCheckedConnections())
                .addLore("  &7Blocked bots&8: &c" + StorageManager.getBlockedBots())
                .addLore("")
                .addLore("&6Storage manager:")
                .addLore("  &7Blacklisted IPs&8: &c" + StorageManager.getBlacklist().size())
                .addLore("  &7Whitelisted IPs&8: &a" + StorageManager.getWhitelist().size())
                .build();

        final ItemStack i4 = new ItemBuilder(UMaterial.BOOK_AND_QUILL.getMaterial())
                .setTitle("&cOpped Players")
                .addLore("&7See who have operator permission.")
                .addLore("")
                .addLore("&7Amount: &c" + Bukkit.getOperators().size())
                .setLore(Bukkit.getOperators().stream().map(operator -> ChatUtil.fix(" &8- &7" + operator.getName() + " &8[" + (operator.isOnline() ? "&aOnline" : "&4Offline") + "&8]")).collect(Collectors.toList()))
                .build();

        /* This will be re-added when we expand the gui with more 'pages'.

        final ItemStack i7 = new ItemBuilder(Material.GOLD_NUGGET)
                .setTitle("&cPlugin Information")
                .addLore("&7Plugin author, version etc.")
                .addLore("")
                .addLore("&6Plugin:")
                .addLore("  &7Name&8: &aEpicGuard")
                .addLore("  &7Version&8: &e" + Updater.getCurrentVersion())
                .addLore("  &7Authors&8: &eiShift, ruzekh")
                .addLore("")
                .addLore("&6Support:")
                .addLore("  &7Dev's Discord&8: &ciShift#0524")
                .addLore("  &7Support Server&8: &cdiscord.gg/VkfhFCv")
                .build();

        final ItemStack i8 = new ItemBuilder(Material.NETHER_BRICK)
                .setTitle("&cServer Information")
                .addLore("&7Some information about your server.")
                .addLore("")
                .addLore("&6Performance:")
                .addLore("  &7TPS&8: &a" + ServerTPS.getTPS())
                .addLore("  &7RAM&8: &c" + Memory.getUsage() + "MB&7/&c" + Memory.getTotal() + "MB &7(&c" + Memory.getFree() + "MB &7free)")
                .addLore("  &7CPU&8: &c" + Runtime.getRuntime().availableProcessors() + " processors.")
                .addLore("")
                .addLore("&6Server:")
                .addLore("  &7Online&8: &a" + Bukkit.getOnlinePlayers().size() + "&7/&a" + Bukkit.getMaxPlayers())
                .addLore("  &7Version&8: &c" + Bukkit.getBukkitVersion())
                .build();

         */

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
            final User user = UserManager.getUser(player1);

            lore.add("");
            lore.add(ChatUtil.fix("&6Basic Information:"));
            lore.add(ChatUtil.fix("  &7Name&8: &f" + player1.getName()));
            lore.add(ChatUtil.fix("  &7UUID&8: &f" + player1.getUniqueId()));
            lore.add(ChatUtil.fix("  &7OP&8: " + (player1.isOp() ? "&aYes" : "&cNo")));
            lore.add(ChatUtil.fix("  &7Country&8: &f" + EpicGuardAPI.getGeoApi().getCountryCode(user.getAddress())));
            lore.add(ChatUtil.fix("  &7City&8: &f" + EpicGuardAPI.getGeoApi().getCity(user.getAddress())));

            if (Config.ipHistoryEnable && user.getAddresses() != null) {
                lore.add("");
                lore.add(ChatUtil.fix("&6IP History:"));
                user.getAddresses().forEach(address -> lore.add(ChatUtil.fix("  &7- &f" + address + (user.getAddress().equals(address) ? " &8(&aCurrent&8)" : ""))));
            }

            final ItemStack itemStack = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                    .setTitle((player1.isOp() ? "&c[OP] " : "&a") + player1.getName())
                    .setLore(lore)
                    .build();

            INVENTORY_PLAYER.setItem(i, itemStack);
            i++;
        }
        final ItemStack back = new ItemBuilder(UMaterial.FENCE_GATE.getMaterial()).setTitle("&cBack to main menu").addLore("&7Click to go back.").build();
        INVENTORY_PLAYER.setItem(35, back);
        player.openInventory(INVENTORY_PLAYER);
    }
}

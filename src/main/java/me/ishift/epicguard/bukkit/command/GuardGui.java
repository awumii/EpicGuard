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
import me.ishift.epicguard.api.bukkit.SkullUtil;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.api.bukkit.UMaterial;
import me.ishift.epicguard.api.bukkit.ItemBuilder;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.common.data.DataStorage;
import me.ishift.epicguard.common.detection.AttackSpeed;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.data.StorageManager;
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
    public static final Inventory INVENTORY_MANAGEMENT = Bukkit.createInventory(null, 27, "EpicGuard | Management Menu");
    public static final Inventory INVENTORY_PLAYER = Bukkit.createInventory(null, 54, "EpicGuard | Player Manager");
    public static final Inventory INVENTORY_MODULES = Bukkit.createInventory(null, 27, "EpicGuard | Module Manager");

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
                .addLore("  &7Attack&8: " + (AttackSpeed.isUnderAttack() ? "&cDetected!" : "&aNot detected."))
                .addLore("  &7Connections&8: &c" + AttackSpeed.getConnectPerSecond() + "/s")
                .addLore("")
                .addLore("&6Connections:")
                .addLore("  &7Checked connections&8: &e" + StorageManager.getStorage().getCheckedConnections())
                .addLore("  &7Blocked bots&8: &c" + StorageManager.getStorage().getBlockedBots())
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
                .addLore("  &7Version&8: &e" + Updater.getCurrentVersion())
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

        final ItemStack i4 = new ItemBuilder(Material.CHEST)
                .setTitle("&cModule management menu.")
                .addLore("")
                .addLore("&7See information about")
                .addLore("&7currently enabled plugin modules.")
                .addLore("&fLeft Click &7to show GUI with modules.")
                .build();

        INVENTORY_MANAGEMENT.setItem(10, i1);
        INVENTORY_MANAGEMENT.setItem(12, i2);
        INVENTORY_MANAGEMENT.setItem(14, i3);
        INVENTORY_MANAGEMENT.setItem(16, i4);
        player.openInventory(INVENTORY_MANAGEMENT);
    }

    /**
     * Opens main EpicGuard modules GUI.
     *
     * @param player Target player.
     */
    public static void showModules(Player player) {
        final ItemStack i1 = new ItemBuilder(Material.ARMOR_STAND)
                .setTitle("&cOP Protection")
                .addLore("&7See who have operator permission.")
                .addLore("")
                .addLore("&6Allowed operators:")
                .setLore(Config.opProtectionList.stream().map(operator -> ChatUtil.fix(" &8- &7" + operator)).collect(Collectors.toList()))
                .addLore("")
                .addLore("&6Opped players:")
                .setLore(Bukkit.getOperators().stream().map(operator -> ChatUtil.fix(" &8- &7" + operator.getName() + " &8[" + (operator.isOnline() ? "&aOnline" : "&4Offline") + "&8]")).collect(Collectors.toList()))
                .build();

        final ItemStack i2 = new ItemBuilder(UMaterial.ENCHANTING_TABLE.getMaterial())
                .setTitle("&cGeolocation")
                .addLore("&7Control your player's location.")
                .addLore("")
                .addLore("&6Status:")
                .addLore("&7City database: " + ChatUtil.formatBol(Config.cityEnabled))
                .addLore("&7Country database: " + ChatUtil.formatBol(Config.countryEnabled))
                .addLore("&7Country filter: &6" + Config.countryMode.name())
                .addLore("")
                .addLore("&6Filtered Countries (" + Config.countryList.size() + "):")
                .setLore(Config.countryList.stream().map(country -> ChatUtil.fix(" &8- &f" + country)).collect(Collectors.toList()))
                .build();

        final ItemStack i3 = new ItemBuilder(Material.ITEM_FRAME)
                .setTitle("&cTab Complete")
                .addLore("&7Control your server's tab complete.")
                .addLore("")
                .addLore("&6Status:")
                .addLore("&7Fully blocking: " + ChatUtil.formatBol(Config.tabCompleteBlock))
                .addLore("&7Custom completion: " + ChatUtil.formatBol(Config.customTabComplete))
                .addLore("&7Bypass permission: " + ChatUtil.formatBol(Config.customTabCompleteBypass))
                .build();

        final ItemStack i4 = new ItemBuilder(Material.NAME_TAG)
                .setTitle("&cCommand Protection")
                .addLore("&7Block or allow specific commands.")
                .addLore("")
                .addLore("&6Blocked commands:")
                .addLore("&7Status: " + ChatUtil.formatBol(Config.blockedCommandsEnable))
                .addLore("&7Values: &6" + Config.blockedCommands.toString())
                .addLore("")
                .addLore("&6Allowed commands:")
                .addLore("&7Status: " + ChatUtil.formatBol(Config.allowedCommandsEnable))
                .addLore("&7Bypass permission: " + ChatUtil.formatBol(Config.allowedCommandsBypass))
                .build();

        final ItemStack i6 = new ItemBuilder(Material.TNT)
                .setTitle("&cAntiBot settings.")
                .addLore("&7Some misc antibot settings.")
                .addLore("")
                .addLore("&7Rejoin check: " + ChatUtil.formatBol(Config.rejoinCheck))
                .addLore("&7ServerList check: " + ChatUtil.formatBol(Config.serverListCheck))
                .addLore("&7Console filter: " + ChatUtil.formatBol(Config.filterEnabled))
                .addLore("&7Bandwidth optimizer: " + ChatUtil.formatBol(Config.bandwidthOptimizer))
                .addLore("")
                .addLore("&7Connection detect speed: &6" + Config.connectSpeed)
                .addLore("&7Ping detect speed: &6" + Config.pingSpeed)
                .addLore("")
                .addLore("&7API Key: &e" + Config.apiKey)
                .addLore("&7Firewall: " + ChatUtil.formatBol(Config.firewallEnabled))
                .build();

        final ItemStack info = new ItemBuilder(UMaterial.BOOK_AND_QUILL.getMaterial())
                .setTitle("&cNOTE!")
                .addLore("&7Some of the EpicGuard settings are")
                .addLore("&7not displayed here, you should see")
                .addLore("&7the configuration to configure every module!")
                .build();

        INVENTORY_MODULES.setItem(11, i1);
        INVENTORY_MODULES.setItem(12, i2);
        INVENTORY_MODULES.setItem(13, i3);
        INVENTORY_MODULES.setItem(14, i4);
        INVENTORY_MODULES.setItem(15, i6);
        INVENTORY_MODULES.setItem(18, info);

        final ItemStack back = new ItemBuilder(UMaterial.FENCE_GATE.getMaterial()).setTitle("&cBack to main menu").addLore("&7Click to go back.").build();
        INVENTORY_MODULES.setItem(26, back);

        player.openInventory(INVENTORY_MODULES);
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

            if (!user.getAddressHistory().isEmpty()) {
                lore.add("");
                lore.add(ChatUtil.fix("&6IP History:"));
                user.getAddressHistory().forEach(address -> lore.add(ChatUtil.fix("  &7- &f" + address + (user.getAddress().equals(address) ? " &8(&aCurrent&8)" : ""))));
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

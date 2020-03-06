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
import me.ishift.epicguard.bukkit.util.misc.UniversalMaterial;
import me.ishift.epicguard.bukkit.util.player.ItemBuilder;
import me.ishift.epicguard.bukkit.util.server.Memory;
import me.ishift.epicguard.bukkit.util.server.ServerTPS;
import me.ishift.epicguard.bukkit.util.server.Updater;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.api.GeoAPI;
import me.ishift.epicguard.common.StorageManager;
import me.ishift.epicguard.common.AttackSpeed;
import me.ishift.epicguard.common.util.ChatUtil;
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

    public static void showMain(Player p) {
        final ItemStack i1 = new ItemBuilder(UniversalMaterial.get(UniversalMaterial.CLOCK))
                .setTitle("&cServer real-time status")
                .addLore("&7See status of your server, in real time.")
                .addLore("")
                .addLore("&6AntiBot Mode:")
                .addLore("  &7Server is&8: " + (AttackSpeed.isUnderAttack() ? "&cUnder Attack" : "&aListening..."))
                .addLore("")
                .addLore("&6Current Connections:")
                .addLore("  &7Connect Per Second&8: &c" + AttackSpeed.getConnectPerSecond())
                .addLore("  &7Ping Per Second&8: &c" + AttackSpeed.getPingPerSecond())
                .build();

        final ItemStack i2 = new ItemBuilder(Material.COMPASS)
                .setTitle("&cPlayer Manager Menu")
                .addLore("&7Get to know about your players.")
                .addLore("")
                .addLore("&6Players:")
                .addLore("  &7Online players: &a" + Bukkit.getOnlinePlayers().size())
                .addLore("")
                .addLore("&6Description:")
                .addLore("  &7Click to show all online players in one GUI.")
                .addLore("  &7GUI will show information you can get")
                .addLore("  &7by command &6/guard player <nickname>&e.")
                .build();

        final ItemStack i3 = new ItemBuilder(UniversalMaterial.get(UniversalMaterial.EXP_BOTTLE))
                .setTitle("&cAntibot Statistics")
                .addLore("&7See statistics about collected data.")
                .addLore("")
                .addLore("&6Connections:")
                .addLore("  &7Checked connections&8: &e" + StorageManager.getCheckedConnections())
                .addLore("  &7Blocked bots&8: &c" + StorageManager.getBlockedBots())
                .addLore("")
                .addLore("&6IP Manager:")
                .addLore("  &7Blacklisted IPs&8: &c" + StorageManager.getBlacklist().size())
                .addLore("  &7Whitelisted IPs&8: &a" + StorageManager.getWhitelist().size())
                .build();

        final ItemStack i4 = new ItemBuilder(Material.DIAMOND_BLOCK)
                .setTitle("&cOpped Players")
                .addLore("&7See who have operator permission.")
                .addLore("")
                .addLore("&6Operators:")
                .addLore("  &7Amount: &c" + Bukkit.getOperators().size())
                .addLores(Bukkit.getOperators().stream().map(player -> ChatUtil.fix("  &8- &7" + player.getName() + " &8[" + (player.isOnline() ? "&aOnline" : "&4Offline") + "&8]")).collect(Collectors.toList()))
                .build();

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

        final ItemStack i8 = new ItemBuilder(UniversalMaterial.get(UniversalMaterial.NETHER_BRICK))
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

        INVENTORY_MANAGEMENT.setItem(10, i1);
        INVENTORY_MANAGEMENT.setItem(12, i2);
        INVENTORY_MANAGEMENT.setItem(14, i3);
        INVENTORY_MANAGEMENT.setItem(16, i4);
        //inventory.setItem(20, i7);
        //eq.setItem(4, i8);
        p.openInventory(INVENTORY_MANAGEMENT);
    }

    public static void showPlayers(Player p) {
        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            final List<String> lore = new ArrayList<>();
            final User user = UserManager.getUser(player);

            lore.add("");
            lore.add(ChatUtil.fix("&6Basic Information:"));
            lore.add(ChatUtil.fix("  &7Name&8: &f" + player.getName()));
            lore.add(ChatUtil.fix("  &7UUID&8: &f" + player.getUniqueId()));
            lore.add(ChatUtil.fix("  &7OP&8: " + (player.isOp() ? "&aYes" : "&cNo")));
            lore.add(ChatUtil.fix("  &7Country&8: &f" + EpicGuardAPI.getGeoApi().getCountryCode(user.getAddress())));
            lore.add(ChatUtil.fix("  &7City&8: &f" + EpicGuardAPI.getGeoApi().getCity(user.getAddress())));
            lore.add(ChatUtil.fix("  &7Client Brand&8: &f" + user.getBrand()));

            if (Config.ipHistoryEnable && user.getAddresses() != null) {
                lore.add("");
                lore.add(ChatUtil.fix("&6IP History:"));
                user.getAddresses().forEach(address -> lore.add(ChatUtil.fix("  &7- &f" + address + (user.getAddress().equals(address) ? " &8(&aCurrent&8)" : ""))));
            }

            final ItemStack itemStack = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                    .setTitle((player.isOp() ? "&c[OP] " + player.getName() : "&a") + player.getName())
                    .addLores(lore)
                    .build();

            INVENTORY_PLAYER.setItem(i, itemStack);
            i++;
        }
        final ItemStack back = new ItemBuilder(UniversalMaterial.get(UniversalMaterial.FENCE_GATE)).setTitle("&cBack to main menu").addLore("&7Click to go back.").build();
        INVENTORY_PLAYER.setItem(35, back);
        p.openInventory(INVENTORY_PLAYER);
    }
}

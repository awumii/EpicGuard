package me.ishift.epicguard.bukkit.gui;

import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.bukkit.util.misc.UniversalMaterial;
import me.ishift.epicguard.bukkit.util.player.ItemBuilder;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.GeoAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiPlayers {
    public static Inventory inv;

    public static void show(Player p) {
        try {
            int i = 0;
            for (Player player : Bukkit.getOnlinePlayers()) {
                final ItemStack itemStack;
                final List<String> lore = new ArrayList<>();
                final User user = UserManager.getUser(player);
                lore.add("");
                lore.add(ChatUtil.fix("&6Basic Information:"));
                lore.add(ChatUtil.fix("  &7Name&8: &f" + player.getName()));
                lore.add(ChatUtil.fix("  &7UUID&8: &f" + player.getUniqueId()));
                lore.add(ChatUtil.fix("  &7OP&8: " + (player.isOp() ? "&aYes" : "&cNo")));
                lore.add(ChatUtil.fix("  &7Country&8: &f" + GeoAPI.getCountryCode(user.getAddress())));
                lore.add(ChatUtil.fix("  &7Client Brand&8: &f" + user.getBrand()));
                if (Config.ipHistoryEnable && user.getAddresses() != null) {
                    lore.add("");
                    lore.add(ChatUtil.fix("&6IP History:"));
                    user.getAddresses().forEach(adress -> {
                        if (player.getAddress().getAddress().getHostAddress().equals(adress)) {
                            lore.add(ChatUtil.fix("  &7- &f" + adress + " &8(&aCurrent&8)"));
                        } else {
                            lore.add(ChatUtil.fix("  &7- &f" + adress));
                        }
                    });
                }
                if (player.isOp()) {
                    itemStack = new ItemBuilder(Material.DIAMOND_HELMET).setTitle("&c[OP] " + player.getName()).addLores(lore).build();
                } else {
                    itemStack = new ItemBuilder(Material.IRON_HELMET).setTitle("&a" + player.getName()).addLores(lore).build();
                }
                ItemStack back = new ItemBuilder(UniversalMaterial.get(UniversalMaterial.FENCE_GATE)).setTitle("&cBack to main menu").addLore("&7Click to go back.").build();
                inv.setItem(i, itemStack);
                inv.setItem(35, back);
                i++;
            }
            p.openInventory(inv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

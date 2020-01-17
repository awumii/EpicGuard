package me.ishift.epicguard.bukkit.gui;

import me.ishift.epicguard.bukkit.manager.UserManager;
import me.ishift.epicguard.bukkit.object.User;
import me.ishift.epicguard.bukkit.util.ItemBuilder;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;
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
                lore.add(ChatUtil.fix("&7Name&8: &f" + player.getName()));
                lore.add(ChatUtil.fix("&7UUID&8: &f" + player.getUniqueId()));
                lore.add(ChatUtil.fix("&7Country&8: &f" + GeoAPI.getCountryCode(player.getAddress().getAddress())));
                lore.add(ChatUtil.fix("&7OP&8: " + (player.isOp() ? "&aYes" : "&cNo")));
                lore.add(ChatUtil.fix("&7Client Brand&8: &f" + user.getBrand()));
                if (Config.ipHistoryEnable && user.getAdresses() != null) {
                    lore.add("");
                    lore.add(ChatUtil.fix("&6[IP History]"));
                    user.getAdresses().forEach(adress -> {
                        if (player.getAddress().getAddress().getHostAddress().equals(adress)) {
                            lore.add(ChatUtil.fix(" &7- &f" + adress + " &8(&aCurrent&8)"));
                        } else {
                            lore.add(ChatUtil.fix(" &7- &f" + adress));
                        }
                    });
                }
                if (player.isOp()) {
                    itemStack = new ItemBuilder(Material.DIAMOND_HELMET).setTitle("&c[OP] " + player.getName()).addLores(lore).build();
                } else {
                    itemStack = new ItemBuilder(Material.IRON_HELMET).setTitle("&a" + player.getName()).addLores(lore).build();
                }
                ItemStack back = new ItemBuilder(Material.ARROW).setTitle("&cBack to main menu").addLore("").addLore("&7Click to go back.").build();
                inv.setItem(i, itemStack);
                inv.setItem(35, back);
                i++;
            }
            p.openInventory(inv);
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}

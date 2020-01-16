package me.ishift.epicguard.bukkit.gui;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.manager.BlacklistManager;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.bukkit.util.ItemBuilder;
import me.ishift.epicguard.bukkit.util.ServerUtils;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiMain {
    public static Inventory eq;

    public static void show(Player p) {
        List<String> l1 = new ArrayList<>();
        l1.add("");
        l1.add(ChatUtil.fix("&8>> &7Plugin version&8: &a" + Updater.currentVersion + (Updater.updateAvaible ? " &4&l[Outdated!]" : "")));
        l1.add(ChatUtil.fix("&8>> &7Server&8: &f" + Bukkit.getServer().getBukkitVersion()));
        l1.add(ChatUtil.fix("&8>> &7RAM Usage&8: &a" + ServerUtils.getMemoryUsage() + "&8/&6" + ServerUtils.getTotalMemory()));
        ItemStack i1 = new ItemBuilder(Material.BEDROCK).setTitle("&8>> &aBasic Information &8«").addLores(l1).build();

        List<String> l2 = new ArrayList<>();
        l2.add("");
        l2.add(ChatUtil.fix("&8>> &7Server is&8: " + (AttackManager.isUnderAttack() ? "&4&lUnder Attack" : "&a&lListening...")));
        l2.add("");
        l2.add(ChatUtil.fix("&8>> &7Login Per Second&8: &c" + AttackManager.getConnectPerSecond()));
        l2.add(ChatUtil.fix("&8>> &7Ping Per Second&8: &c" + AttackManager.getPingPerSecond()));
        l2.add(ChatUtil.fix("&8>> &7Join Per Second&8: &c" + AttackManager.getJoinPerSecond()));
        ItemStack i2 = new ItemBuilder(Material.BOW).setTitle("&8>> &6Server Attack Status &8«").addLores(l2).build();

        List<String> l3 = new ArrayList<>();
        l3.add("");
        l3.add(ChatUtil.fix("&8>> &7Show all online players in one GUI."));
        l3.add(ChatUtil.fix("&8>> &7You can see some basic information about them."));
        l3.add(ChatUtil.fix("&8>> &7GUI will show same information that can be found,"));
        l3.add(ChatUtil.fix("&8>> &7when you use command &6/guard player <nickname>&e."));
        ItemStack i3 = new ItemBuilder(Material.ARMOR_STAND).setTitle("&8>> &cPlayer Information &8<<").addLores(l3).build();

        List<String> l4 = new ArrayList<>();
        l4.add("");
        l2.add(ChatUtil.fix("&8>> &7Some statistics about your antibot protection."));
        l4.add(ChatUtil.fix("&8>> &7Blocked bots&8: &a" + DataFileManager.blockedBots));
        l4.add(ChatUtil.fix("&8>> &7Checked connections&8: &a" + DataFileManager.checkedConnections));
        ItemStack i4 = new ItemBuilder(Material.BOOK).setTitle("&8>> &aGlobal Statistics &8«").addLores(l4).build();

        List<String> l5 = new ArrayList<>();
        l5.add("&7GUI will be redesigned in the next update, stay tuned!");
        ItemStack i5 = new ItemBuilder(Material.GOLD_BLOCK).setTitle("&8>> &6Coming soon &8«").addLores(l5).build();

        List<String> l6 = new ArrayList<>();
        l6.add("");
        l6.add(ChatUtil.fix("&8>> &7Click to reload config."));
        l6.add(ChatUtil.fix("&8>> &7After click GUI will close."));
        ItemStack i6 = new ItemBuilder(Material.ANVIL).setTitle("&8>> &cReload Config &8«").addLores(l6).build();

        List<String> l7 = new ArrayList<>();
        l7.add("");
        for (OfflinePlayer player : Bukkit.getOperators()) {
            l7.add(ChatUtil.fix("&8>> &7" + player.getName() + " &8[" + (player.isOnline() ? "&aOnline" : "&4Offline") + "&8]"));
        }
        ItemStack i7 = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setTitle("&8>> &cOP List &8«").addLores(l7).build();

        List<String> l8 = new ArrayList<>();
        l8.add("");
        l8.add(ChatUtil.fix("&8>> &7Antibot modules&8: " + (Config.antibot ? "&aOn" : "&4Off")));
        l8.add(ChatUtil.fix("&8>> &7Firewall&8: " + (Config.firewallEnabled ? "&aOn" : "&4Off")));
        l8.add(ChatUtil.fix("&8>> &7Blacklisted IPs&8: &c" + BlacklistManager.IP_BL.size()));
        l8.add(ChatUtil.fix("&8>> &7Whitelisted IPs&8: &a" + BlacklistManager.IP_WL.size()));
        ItemStack i8 = new ItemBuilder(Material.LEATHER_CHESTPLATE).setTitle("&8>> &6Antibot Information &8«").addLores(l8).build();

        List<String> l9 = new ArrayList<>();
        l9.add("");
        l9.add(ChatUtil.fix("&aAntibot&8: &6" + Config.antibot));
        l9.add(ChatUtil.fix("&7Updater&8: &6" + Config.updater));
        l9.add(ChatUtil.fix("&7Anti-TAB&8: &6" + Config.tabCompleteBlock));
        l9.add(ChatUtil.fix("&7Blocked Commands&8: &6" + Config.blockedCommandsEnable));
        l9.add(ChatUtil.fix("&7Allowed Commands&8: &6" + Config.allowedCommandsEnable));
        l9.add(ChatUtil.fix("&7OP Protection&8: &6" + Config.opProtectionEnable));
        l9.add(ChatUtil.fix("&7PEX Protection&8: &6" + Config.pexProtection));
        l9.add(ChatUtil.fix("&7Auto Whitelist&8: &6" + Config.autoWhitelist));
        l9.add(ChatUtil.fix("&7Firewall&8: &6" + Config.firewallEnabled));
        l9.add(ChatUtil.fix("&7Force Rejoin&8: &6" + Config.forceRejoin));
        l9.add(ChatUtil.fix("&7IP History&8: &6" + Config.ipHistoryEnable));
        l9.add(ChatUtil.fix("&7GEO Option&8: &6" + Config.countryMode));
        ItemStack i9 = new ItemBuilder(Material.REDSTONE).setTitle("&8>> &aToggled Modules &8«").addLores(l9).build();

        eq.setItem(10, i1);
        eq.setItem(12, i2);
        eq.setItem(14, i3);
        eq.setItem(16, i4);
        eq.setItem(28, i5);
        eq.setItem(30, i6);
        eq.setItem(32, i7);
        eq.setItem(34, i8);
        eq.setItem(40, i9);
        p.openInventory(eq);
    }
}

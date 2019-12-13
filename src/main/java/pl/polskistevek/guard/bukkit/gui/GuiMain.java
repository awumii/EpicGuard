package pl.polskistevek.guard.bukkit.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.manager.AttackManager;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.bukkit.util.ExactTPS;
import pl.polskistevek.guard.bukkit.util.ItemBuilder;
import pl.polskistevek.guard.bukkit.util.ServerUtils;
import pl.polskistevek.guard.bukkit.util.Updater;
import pl.polskistevek.guard.utils.ChatUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuiMain {
    public static Inventory i;

    public static void show(Player p) {
        List<String> l1 = new ArrayList<>();
        l1.add("");
        l1.add(ChatUtil.fix("&7Plugin version&8: &a" + Updater.currentVersion + (Updater.updateAvaible ? " &c[Outdated!]" : "")));
        l1.add(ChatUtil.fix("&7Server&8: &a" + Bukkit.getServer().getBukkitVersion()));
        l1.add(ChatUtil.fix("&7RAM Usage&8: &6" + ServerUtils.getMemoryUsage() + "&8/&6" + ServerUtils.getTotalMemory()));
        l1.add(ChatUtil.fix("&7TPS&8: &6" + ExactTPS.getTPS()));
        ItemStack i1 = new ItemBuilder(Material.BEDROCK).setTitle("&aBasic Information").addLores(l1).build();

        List<String> l2 = new ArrayList<>();
        l2.add("");
        l2.add(ChatUtil.fix("&7Server is&8: " + (AttackManager.attackMode ? "&cUnder Attack" : "&aListening...")));
        l2.add("");
        l2.add(ChatUtil.fix("&7Login Per Second&8: &6" + AttackManager.connectPerSecond));
        l2.add(ChatUtil.fix("&7Ping Per Second&8: &6" + AttackManager.pingPerSecond));
        l2.add(ChatUtil.fix("&7Join Per Second&8: &6" + AttackManager.joinPerSecond));
        ItemStack i2 = new ItemBuilder(Material.BOW).setTitle("&aServer Attack Status").addLores(l2).build();

        List<String> l3 = new ArrayList<>();
        l3.add("");
        l3.add(ChatUtil.fix("&7Show all online players in one GUI."));
        l3.add(ChatUtil.fix("&7You can see some basic information about them."));
        l3.add(ChatUtil.fix("&7GUI will show same information that can be found&7,"));
        l3.add(ChatUtil.fix("&7when you use command &6/guard player <nickname>&7."));
        l3.add("");
        l3.add(ChatUtil.fix("&cWARNING: If you have server with many players, server"));
        l3.add(ChatUtil.fix("&ccan freeze for some seconds while loading a gui."));
        ItemStack i3 = new ItemBuilder(Material.ARMOR_STAND).setTitle("&aPlayer Information").addLores(l3).build();

        List<String> l4 = new ArrayList<>();
        l4.add("");
        l2.add(ChatUtil.fix("&7Some statistics about your antibot protection."));
        l4.add(ChatUtil.fix("&7Blocked bots&8: &6" + DataFileManager.blockedBots));
        l4.add(ChatUtil.fix("&7Checked connections&8: &6" + DataFileManager.checkedConnections));
        ItemStack i4 = new ItemBuilder(Material.BOOK).setTitle("&aGlobal Statistics").addLores(l4).build();

        List<String> l5 = new ArrayList<>();
        l5.add("");
        l5.add(ChatUtil.fix("&7/guard menu &8- &7Opens this GUI."));
        l5.add(ChatUtil.fix("&7/guard reload &8- &7Reload config."));
        l5.add(ChatUtil.fix("&7/guard antibot &8- &7Antibot information."));
        l5.add(ChatUtil.fix("&7/guard op &8- &7See opped players."));
        l5.add(ChatUtil.fix("&7/guard status &8- &7Toggle notifications."));
        l5.add(ChatUtil.fix("&7/guard player <nick> &8- &7Informations about player."));
        l5.add(ChatUtil.fix("&7/guard whitelist <adress> &8- &7Whitelist specified adress."));
        l5.add(ChatUtil.fix("&7/guard blacklist <adress> &8- &7Blacklist specified adress."));
        ItemStack i5 = new ItemBuilder(Material.PAPER).setTitle("&aCommand List").addLores(l5).build();

        List<String> l6 = new ArrayList<>();
        l6.add("");
        l6.add(ChatUtil.fix("&7Click to reload config."));
        l6.add(ChatUtil.fix("&7After click GUI will close."));
        ItemStack i6 = new ItemBuilder(Material.ANVIL).setTitle("&aReload Config").addLores(l6).build();

        List<String> l7 = new ArrayList<>();
        l7.add("");
        for (OfflinePlayer player : Bukkit.getOperators()) {
            l7.add(ChatUtil.fix("&8-> &7" + player.getName() + " &8[" + (player.isOnline() ? "&aOnline" : "&cOffline") + "&8]"));
        }
        ItemStack i7 = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setTitle("&3OP List").addLores(l7).build();

        List<String> l8 = new ArrayList<>();
        l8.add("");
        l8.add(ChatUtil.fix("&7Antibot modules&8: " + (BukkitMain.ANTIBOT ? "&aOn" : "&cOff")));
        l8.add(ChatUtil.fix("&7Firewall&8: " + (BukkitMain.FIREWALL ? "&aOn" : "&cOff")));
        l8.add(ChatUtil.fix("&7Blacklisted IPs&8: &c" + BlacklistManager.IP_BL.size()));
        l8.add(ChatUtil.fix("&7Whitelisted IPs&8: &a" + BlacklistManager.IP_WL.size()));
        ItemStack i8 = new ItemBuilder(Material.LEATHER_CHESTPLATE).setTitle("&aAntibot Information").addLores(l8).build();

        i.setItem(10, i1);
        i.setItem(12, i2);
        i.setItem(14, i3);
        i.setItem(16, i4);
        i.setItem(28, i5);
        i.setItem(30, i6);
        i.setItem(32, i7);
        i.setItem(34, i8);
        p.openInventory(i);
    }
}

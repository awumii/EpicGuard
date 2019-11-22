package pl.polskistevek.guard.bukkit.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.listener.PlayerJoinListener;
import pl.polskistevek.guard.bukkit.listener.ServerListPingListener;
import pl.polskistevek.guard.bukkit.listener.PreLoginListener;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.utils.ServerUtils;
import pl.polskistevek.guard.bukkit.utils.ExactTPS;
import pl.polskistevek.guard.bukkit.utils.ItemBuilder;
import pl.polskistevek.guard.bukkit.utils.Updater;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuiMain {
    public static Inventory i;

    public static void show(Player p){
        List<String> l1 = new ArrayList<>();
        l1.add("");
        l1.add(ChatUtil.fix("&7Basic plugin and server informaton."));
        l1.add(ChatUtil.fix("&7Author: &ePolskiStevek"));
        l1.add(ChatUtil.fix("&7Version: &a" + Updater.currentVersion + (Updater.updateAvaible ? " &c&lOUTDATED &8(&7Latest: &6" + Updater.lastestVersion + "&8)" : " &8(&aLatest&8)")));
        l1.add("");
        l1.add(ChatUtil.fix("&7Server Build: &c" + Bukkit.getVersion()));
        l1.add(ChatUtil.fix("&7Bukkit Version: &c" + Bukkit.getServer().getBukkitVersion()));
        l1.add(ChatUtil.fix("&7RAM Usage: &6" + ServerUtils.getRamUsage() + "&8/&6" + ServerUtils.getRam()));
        l1.add(ChatUtil.fix("&7TPS: &a" + ExactTPS.getTPS2()));
        l1.add("");
        ItemStack i1 = new ItemBuilder(Material.PAPER).setTitle("&aBasic Information").addLores(l1).build();

        List<String> l2 = new ArrayList<>();
        l2.add("");
        l2.add(ChatUtil.fix("&7Some statistics about your antibot protection."));
        l2.add(ChatUtil.fix("&7Antibot modules: " + (BukkitMain.ANTIBOT ? "&aON" : "&cOFF")));
        l2.add(ChatUtil.fix("&7Firewall: " + (BukkitMain.FIREWALL ? "&aON" : "&cOFF")));
        l2.add(ChatUtil.fix("&7Attack Protection: " + (PreLoginListener.attack ? "&aActive" : "&cInactive")));
        l2.add(ChatUtil.fix("&7Conn. Per Second: &c" + PreLoginListener.cps));
        l2.add(ChatUtil.fix("&7Pings Per Second: &6" + ServerListPingListener.cps_ping));
        l2.add(ChatUtil.fix("&7Joins Per Second: &e" + PlayerJoinListener.jps));
        l2.add(ChatUtil.fix(""));
        l2.add(ChatUtil.fix("&7Blacklisted IPs: &c" + BlacklistManager.IP_BL.size()));
        l2.add(ChatUtil.fix("&7Whitelisted IPs: &a" + BlacklistManager.IP_WL.size()));
        ItemStack i2 = new ItemBuilder(Material.EXPERIENCE_BOTTLE).setTitle("&6AntiBot Status").addLores(l2).build();

        List<String> l3 = new ArrayList<>();
        l3.add("");
        l3.add(ChatUtil.fix("&7Show all online players in one GUI."));
        l3.add(ChatUtil.fix("&7You can see some basic information about them."));
        l3.add(ChatUtil.fix("&7GUI will show same information that can be found&7,"));
        l3.add(ChatUtil.fix("&7when you use command &6/guard player <nickname>&7."));
        l3.add("");
        l3.add(ChatUtil.fix("&cIf you have server with many players, server"));
        l3.add(ChatUtil.fix("&ccan freeze for some seconds when loading a gui."));
        l3.add("");
        l3.add(ChatUtil.fix("&7Use &6Right Click&7 to show list."));
        ItemStack i3 = new ItemBuilder(Material.ARMOR_STAND).setTitle("&6Player Manager").addLores(l3).build();

        List<String> l4 = new ArrayList<>();
        l4.add("");
        l4.add(ChatUtil.fix("&7Buy additional features."));
        l4.add(ChatUtil.fix("&7More information coming soon!"));
        l4.add("");
        l4.add(ChatUtil.fix("&7ID: &6" + Piracy.getServerId()));
        l4.add(ChatUtil.fix("&7License: &6" + Piracy.b));
        ItemStack i4 = new ItemBuilder(Material.GOLD_BLOCK).setTitle("&6Premium Features").addLores(l4).build();

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
        l5.add("");
        ItemStack i5 = new ItemBuilder(Material.NAME_TAG).setTitle("&9Command List").addLores(l5).build();

        List<String> l6 = new ArrayList<>();
        l6.add("");
        l6.add(ChatUtil.fix("&7Click to reload config."));
        l6.add(ChatUtil.fix("&7After click GUI will close."));
        ItemStack i6 = new ItemBuilder(Material.ANVIL).setTitle("&cReload Config").addLores(l6).build();

        List<String> l7 = new ArrayList<>();
        l7.add("");
        for (OfflinePlayer player : Bukkit.getOperators()){
            Date currentDate = new Date(player.getLastPlayed());
            l7.add(ChatUtil.fix("&8* &7" + player.getName() + " &8[" + (player.isOnline() ? "&aONLINE" : "&cOFFLINE") + "&8, &7" + currentDate + "&8]"));
        }
        l7.add("");
        ItemStack i7 = new ItemBuilder(Material.LEATHER_CHESTPLATE).setTitle("&3OP List").addLores(l7).build();

        i.setItem(10, i1);
        i.setItem(12, i2);
        i.setItem(14, i3);
        i.setItem(16, i7);
        i.setItem(18, i5);
        i.setItem(22, i6);
        i.setItem(26, i4);
        p.openInventory(i);
    }
}

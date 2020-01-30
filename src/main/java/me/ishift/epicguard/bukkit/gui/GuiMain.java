package me.ishift.epicguard.bukkit.gui;

import me.ishift.epicguard.bukkit.gui.material.MaterialUtil;
import me.ishift.epicguard.bukkit.gui.material.UniversalMaterial;
import me.ishift.epicguard.bukkit.listener.player.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.manager.BlacklistManager;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.bukkit.util.player.ItemBuilder;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.cloud.CloudManager;
import me.ishift.epicguard.universal.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class GuiMain {
    public static Inventory eq;

    public static void show(Player p) {
        final ItemStack i1 = new ItemBuilder(MaterialUtil.get(UniversalMaterial.CLOCK))
                .setTitle("&aServer Real-Time Status")
                .addLore("")
                .addLore("&7Server is&8: " + (AttackManager.isUnderAttack() ? "&cUnder Attack" : "&aListening..."))
                .addLore("&7Connect Per Second&8: &c" + AttackManager.getConnectPerSecond())
                .addLore("&7Ping Per Second&8: &c" + AttackManager.getPingPerSecond())
                .addLore("&7Join Per Second&8: &c" + AttackManager.getJoinPerSecond())
                .build();

        final ItemStack i2 = new ItemBuilder(Material.COMPASS)
                .setTitle("&aOnline Player Information")
                .addLore("")
                .addLore("&7Show all online players in one GUI.")
                .addLore("&7GUI will show information you can get")
                .addLore("&7by command &6/guard player <nickname>&e.")
                .build();

        final ItemStack i3 = new ItemBuilder(MaterialUtil.get(UniversalMaterial.EXP_BOTTLE))
                .setTitle("&aGlobal Statistics")
                .addLore("")
                .addLore("&7Checked connections&8: &c" + DataFileManager.checkedConnections)
                .addLore("&7Blocked bots&8: &c" + DataFileManager.blockedBots)
                .addLore("&7Blacklisted IPs&8: &c" + BlacklistManager.getBlacklist().size())
                .addLore("&7Whitelisted IPs&8: &c" + BlacklistManager.getWhitelist().size())
                .build();

        final ItemStack i4 = new ItemBuilder(Material.DIAMOND_BLOCK)
                .setTitle("&aOpped Players")
                .addLore("")
                .addLores(Bukkit.getOperators().stream().map(player -> ChatUtil.fix("&7Player&8: &7" + player.getName() + " &8[" + (player.isOnline() ? "&aOnline" : "&4Offline") + "&8]")).collect(Collectors.toList()))
                .build();

        final ItemStack i5 = new ItemBuilder(MaterialUtil.get(UniversalMaterial.BOOK_AND_QUILL))
                .setTitle("&aDetection Log (Latest)")
                .addLore("")
                .addLore("&7Player&8: &c" + PlayerPreLoginListener.getLastPlayer())
                .addLore("&7Adress&8: &c" + PlayerPreLoginListener.getLastAdress())
                .addLore("&7Country&8: &c" + PlayerPreLoginListener.getLastCountry())
                .addLore("&7Detection&8: &c" + PlayerPreLoginListener.getLastDetection())
                .addLore("&7Blacklisted&8: &c" + PlayerPreLoginListener.isBlacklisted())
                .build();

        final ItemStack i6 = new ItemBuilder(MaterialUtil.get(UniversalMaterial.CRAFTING))
                .setTitle("&aCloud Status")
                .addLore("")
                .addLore("&7Status&8: " + (CloudManager.isOnline() ? "&aConnected" : "&cDisconnected"))
                .addLore("&7Blacklist size&8: &c" + CloudManager.getCloudBlacklist().size())
                .addLore("&7Last sync&8: &c" + CloudManager.getLastCheck())
                .addLore("&7Sync time&8: &c" + Config.cloudTime + "sec")
                .build();

        final ItemStack i7 = new ItemBuilder(Material.GOLD_NUGGET)
                .setTitle("&aPlugin Information")
                .addLore("")
                .addLore("&7Name&8: &cEpicGuard")
                .addLore("&7Version&8: &c" + Updater.currentVersion)
                .addLore("&7Authors&8: &ciShift, ruzekh")
                .addLore("&7Discord&8: &ciShift#0001")
                .build();

        eq.setItem(10, i1);
        eq.setItem(12, i2);
        eq.setItem(14, i3);
        eq.setItem(16, i4);
        eq.setItem(22, i5);
        eq.setItem(24, i6);
        eq.setItem(20, i7);
        p.openInventory(eq);
    }
}

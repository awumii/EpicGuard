package me.ishift.epicguard.bukkit.gui;

import me.ishift.epicguard.bukkit.listener.player.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.util.misc.UniversalMaterial;
import me.ishift.epicguard.bukkit.util.player.ItemBuilder;
import me.ishift.epicguard.bukkit.util.server.Memory;
import me.ishift.epicguard.bukkit.util.server.ServerTPS;
import me.ishift.epicguard.bukkit.util.server.Updater;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
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
        final ItemStack i1 = new ItemBuilder(UniversalMaterial.get(UniversalMaterial.CLOCK))
                .setTitle("&cServer real-time status")
                .addLore("&7See status of your server, in real time.")
                .addLore("")
                .addLore("&6AntiBot Mode:")
                .addLore("  &7Server is&8: " + (SpeedCheck.isUnderAttack() ? "&cUnder Attack" : "&aListening..."))
                .addLore("")
                .addLore("&6Current Connections:")
                .addLore("  &7Connect Per Second&8: &c" + SpeedCheck.getConnectPerSecond())
                .addLore("  &7Ping Per Second&8: &c" + SpeedCheck.getPingPerSecond())
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

        final ItemStack i5 = new ItemBuilder(UniversalMaterial.get(UniversalMaterial.BOOK_AND_QUILL))
                .setTitle("&cDetection Log (Latest)")
                .addLore("&7See information about latest detection.")
                .addLore("")
                .addLore("&6Information:")
                .addLore("  &7Player&8: &c" + PlayerPreLoginListener.getLastPlayer())
                .addLore("  &7Adress&8: &c" + PlayerPreLoginListener.getLastAddress())
                .addLore("  &7Country&8: &c" + PlayerPreLoginListener.getLastCountry())
                .addLore("  &7Detection&8: &c" + PlayerPreLoginListener.getLastDetection())
                .addLore("  &7Blacklisted&8: &c" + PlayerPreLoginListener.isBlacklisted())
                .build();

        final ItemStack i6 = new ItemBuilder(UniversalMaterial.get(UniversalMaterial.CRAFTING))
                .setTitle("&cCloud Status")
                .addLore("&7Status of EpicCloud system.")
                .addLore("")
                .addLore("&6Information:")
                .addLore("  &7Status&8: " + (CloudManager.isOnline() ? "&aConnected" : "&cDisconnected"))
                .addLore("  &7Blacklist size&8: &e" + CloudManager.getCloudBlacklist().size())
                .addLore("")
                .addLore("&6Time:")
                .addLore("  &7Last sync&8: &c" + CloudManager.getLastCheck())
                .addLore("  &7Sync time&8: &c" + Config.cloudTime + "sec")
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

        eq.setItem(10, i1);
        eq.setItem(12, i2);
        eq.setItem(14, i3);
        eq.setItem(16, i4);
        eq.setItem(22, i5);
        eq.setItem(24, i6);
        eq.setItem(20, i7);
        eq.setItem(4, i8);
        p.openInventory(eq);
    }
}

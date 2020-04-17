package me.ishift.epicguard.bukkit.inventory;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.bukkit.util.ItemBuilder;
import me.ishift.epicguard.bukkit.util.UMaterial;
import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.inventory.api.InventorySize;
import me.ishift.inventory.api.inventories.ClickableInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainInventory extends ClickableInventory {
    private final AttackManager attackManager;

    public MainInventory(AttackManager attackManager) {
        super("EpicGuard v" + EpicGuardBukkit.getInstance().getDescription().getVersion() + " (Main GUI)", "MAIN", InventorySize.NORMAL);
        this.attackManager = attackManager;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        final ItemStack i1 = new ItemBuilder(UMaterial.CHEST_MINECART.getMaterial())
                .setTitle("&cServer status.")
                .addLore("&7See status of your server.")
                .addLore("")
                .addLore("&6Status:")
                .addLore("  &7Attack&8: " + (attackManager.isUnderAttack() ? "&cDetected!" : "&aNot detected."))
                .addLore("  &7Connections&8: &c" + attackManager.getConnectPerSecond() + "/s")
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
                .addLore("  &7Version&8: &e" + EpicGuardBukkit.getInstance().getDescription().getVersion())
                .addLore("  &7Authors&8: &eiShift, rusekh")
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

        final ItemStack i4 = new ItemBuilder(Material.BARRIER)
                .setTitle("&cComing soon...")
                .addLore("")
                .addLore("&7GUI will be reworked in the upcoming updates!")
                .build();

        inventory.setItem(10, i1);
        inventory.setItem(12, i2);
        inventory.setItem(14, i3);
        inventory.setItem(16, i4);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if (event.getSlot() == 14) {
            player.closeInventory();
            EpicGuardBukkit.getInstance().getInventoryManager().open("PLAYERS", player);
        }
    }
}

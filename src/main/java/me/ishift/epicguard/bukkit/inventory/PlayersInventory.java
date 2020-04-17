package me.ishift.epicguard.bukkit.inventory;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.util.ItemBuilder;
import me.ishift.epicguard.bukkit.util.SkullUtil;
import me.ishift.epicguard.bukkit.util.UMaterial;
import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.util.MessageHelper;
import me.ishift.inventory.api.InventorySize;
import me.ishift.inventory.api.inventories.ClickableInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayersInventory extends ClickableInventory {
    private final AttackManager attackManager;

    public PlayersInventory(AttackManager attackManager) {
        super("EpicGuard v" + EpicGuardBukkit.getInstance().getDescription().getVersion() + " (Player Management)", "PLAYERS", InventorySize.BIGGEST);
        this.attackManager = attackManager;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        int i = 0;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            final List<String> lore = new ArrayList<>();
            final User user = EpicGuardBukkit.getInstance().getUserManager().getUser(player1);

            lore.add("");
            lore.add(MessageHelper.color("&6Basic Information:"));
            lore.add(MessageHelper.color("  &7Name&8: &f" + player1.getName()));
            lore.add(MessageHelper.color("  &7UUID&8: &f" + player1.getUniqueId()));
            lore.add(MessageHelper.color("  &7OP&8: " + (player1.isOp() ? "&aYes" : "&cNo")));
            lore.add(MessageHelper.color("  &7Country&8: &f" + attackManager.getGeoApi().getCountryCode(user.getAddress())));
            lore.add(MessageHelper.color("  &7City&8: &f" + attackManager.getGeoApi().getCity(user.getAddress())));

            final ItemStack itemStack = SkullUtil.getSkull(player1, (player1.isOp() ? "&c[OP] " : "&a") + player1.getName(), lore);
            inventory.setItem(i, itemStack);
            i++;
        }

        final ItemStack back = new ItemBuilder(UMaterial.FENCE_GATE.getMaterial()).setTitle("&cBack to main menu").addLore("&7Click to go back.").build();
        inventory.setItem(53, back);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if (event.getSlot() == 53) {
            player.closeInventory();
            EpicGuardBukkit.getInstance().getInventoryManager().open("MAIN", player);
        }
    }
}

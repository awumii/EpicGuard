package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.command.GuardGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        final Player p = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("EpicGuard Management Menu")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                return;
            }
            final ItemMeta im = event.getCurrentItem().getItemMeta();
            if (im == null) {
                return;
            }
            if (im.getDisplayName().contains("Player Manager Menu")) {
                GuardGui.showPlayers(p);
            }
        }
        if (event.getView().getTitle().equals("EpicGuard Player Manager")) {
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Back to main menu")) {
                GuardGui.showMain(p);
            }
            event.setCancelled(true);
        }
    }
}

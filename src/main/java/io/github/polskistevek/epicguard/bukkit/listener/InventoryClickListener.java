package io.github.polskistevek.epicguard.bukkit.listener;

import io.github.polskistevek.epicguard.bukkit.gui.GuiPlayers;
import io.github.polskistevek.epicguard.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import io.github.polskistevek.epicguard.bukkit.gui.GuiMain;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            Player p = (Player) event.getWhoClicked();
            if (event.getView().getTitle().equals("EpicGuard Management Menu")) {
                event.setCancelled(true);
                if (event.getCurrentItem() == null) {
                    return;
                }
                ItemMeta im = event.getCurrentItem().getItemMeta();
                if (im == null) {
                    return;
                }
                if (im.getDisplayName().contains("Player Information")) {
                    GuiPlayers.show(p);
                }
                if (im.getDisplayName().contains("Reload Config")) {
                    p.chat("/guard reload");
                    p.getOpenInventory().close();
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
                    GuiMain.show(p);
                }
                event.setCancelled(true);
            }
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}

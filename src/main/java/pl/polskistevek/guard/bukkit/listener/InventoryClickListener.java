package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.gui.GuiPlayers;
import pl.polskistevek.guard.bukkit.util.ActionBarAPI;
import pl.polskistevek.guard.bukkit.util.MessagesBukkit;
import pl.polskistevek.guard.utils.ChatUtil;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("EpicGuard Management Menu")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            ItemMeta im = e.getCurrentItem().getItemMeta();
            if (im == null) {
                return;
            }
            if (im.getDisplayName().contains("Player Information")) {
                GuiPlayers.show(p);
            }
            if (im.getDisplayName().contains("Reload Config")) {
                p.getOpenInventory().close();
            }
        }
        if (e.getView().getTitle().equals("EpicGuard Player Manager")) {
            e.setCancelled(true);
        }
    }
}

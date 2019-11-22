package pl.polskistevek.guard.bukkit.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("EpicGuard Menu")){
            e.setCancelled(true);
            if (e.getCurrentItem() == null){
                return;
            }
            ItemMeta im = e.getCurrentItem().getItemMeta();
            if (im == null){
                return;
            }
            if (im.getDisplayName().contains("Player Manager")){
                GuiPlayers.show(p);
            }
            if (im.getDisplayName().contains("Reload Config")){
                p.getOpenInventory().close();
            }
        }
        if (e.getView().getTitle().equals("EpicGuard Player Manager")){
            e.setCancelled(true);
        }
    }
}

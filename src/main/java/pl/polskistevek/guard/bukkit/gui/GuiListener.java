package pl.polskistevek.guard.bukkit.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class GuiListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("EpicGuard Menu")){
            e.setCancelled(true);
            if (e.getCurrentItem() != null && Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName().contains("Player Manager")){
                //p.getOpenInventory().close();
                GuiPlayers.show(p);
            }
        }
        if (e.getView().getTitle().equals("EpicGuard Player Manager")){
            e.setCancelled(true);
        }
    }
}

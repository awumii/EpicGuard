package pl.polskistevek.guard.bukkit.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import pl.polskistevek.guard.bukkit.util.ActionBarAPI;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.bukkit.util.MessagesBukkit;

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
                if (!ActionBarAPI.nmsver.startsWith("v1_14")){
                    p.closeInventory();
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Your server version &8(&c" + ActionBarAPI.nmsver + "&8) &7has no support for this feature!"));
                    return;
                }
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

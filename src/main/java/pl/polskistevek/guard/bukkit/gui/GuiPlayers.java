package pl.polskistevek.guard.bukkit.gui;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pl.polskistevek.guard.bukkit.manager.PlayerManager;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.utils.GEO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuiPlayers {
    public static void show(Player p){
        Inventory inv = Bukkit.createInventory(p, 45, "EpicGuard Player Manager");
        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()){
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwningPlayer(player);
            meta.setDisplayName(ChatUtil.fix("&a" + player.getName()));
            List<String> l = new ArrayList<>();
            l.add("");
            l.add(ChatUtil.fix("&7UUID: &6" + player.getUniqueId()));
            l.add(ChatUtil.fix("&7First Join: &a" + new Date(player.getFirstPlayed())));
            try {
                l.add(ChatUtil.fix("&7Country: &6" + GEO.dbReader.country(player.getAddress().getAddress()).getCountry().getIsoCode()));
            } catch (IOException | GeoIp2Exception e) {
                e.printStackTrace();
            }
            l.add(ChatUtil.fix("&7OP: " + (player.isOp() ? "&aYES" : "&cNO")));
            l.add("");
            l.add(ChatUtil.fix("&7IP History:"));
            for (String adress : PlayerManager.getUser(player).getAdresses()){
                if (player.getAddress().getAddress().getHostAddress().equals(adress)){
                    l.add(ChatUtil.fix("&8- &c" + adress + " &8(&aCurrent&8)"));
                } else {
                    l.add(ChatUtil.fix("&8- &c" + adress));
                }
            }
            meta.setLore(l);
            skull.setItemMeta(meta);
            inv.setItem(i, skull);
            i++;
        }
        p.openInventory(inv);
    }
}

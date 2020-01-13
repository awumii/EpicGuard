package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.gui.GuiMain;
import me.ishift.epicguard.bukkit.gui.GuiPlayers;
import org.bukkit.Bukkit;

public class InventoryTask implements Runnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getOpenInventory().getTitle().equals("EpicGuard Management Menu")) {
                GuiMain.show(player);
            }
            if (player.getOpenInventory().getTitle().equals("EpicGuard Player Manager")) {
                GuiPlayers.show(player);
            }
        });
    }
}

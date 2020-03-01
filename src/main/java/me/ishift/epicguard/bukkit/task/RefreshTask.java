package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.gui.GuiMain;
import me.ishift.epicguard.bukkit.gui.GuiPlayers;
import me.ishift.epicguard.universal.AttackSpeed;
import org.bukkit.Bukkit;

public class RefreshTask implements Runnable {
    private static int time = 0;

    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        RefreshTask.time = time;
    }

    @Override
    public void run() {
        if (AttackSpeed.isUnderAttack()) {
            time++;
        }

        // Inventory Refreshing
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

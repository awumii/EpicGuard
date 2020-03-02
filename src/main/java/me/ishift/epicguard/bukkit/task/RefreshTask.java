package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.command.GuardGui;
import me.ishift.epicguard.universal.AttackSpeed;
import org.bukkit.Bukkit;

public class RefreshTask implements Runnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getOpenInventory().getTitle().equals("EpicGuard Management Menu")) {
                GuardGui.showMain(player);
                return;
            }
            if (player.getOpenInventory().getTitle().equals("EpicGuard Player Manager")) {
                GuardGui.showPlayers(player);
            }
        });
    }
}

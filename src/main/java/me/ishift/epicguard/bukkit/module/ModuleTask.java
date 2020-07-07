package me.ishift.epicguard.bukkit.module;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ModuleTask implements Runnable {
    private final EpicGuardBukkit plugin;

    public ModuleTask(EpicGuardBukkit plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // This will check if player has operator, even before he executes any command.
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.plugin.getModuleManager().check(player, null, null);
        }
    }
}

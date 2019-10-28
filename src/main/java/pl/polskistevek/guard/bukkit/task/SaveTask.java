package pl.polskistevek.guard.bukkit.task;

import org.bukkit.Bukkit;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.manager.ConfigManager;
import pl.polskistevek.guard.utils.Updater;

public class SaveTask {
    public static void start(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BukkitMain.getPlugin(BukkitMain.class), () -> {
            ConfigManager.save();
            Updater.checkForUpdates();
        }, 0L, 5000L);
    }
}

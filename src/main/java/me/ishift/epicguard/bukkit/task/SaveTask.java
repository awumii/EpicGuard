package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.universal.util.Logger;
import me.ishift.epicguard.universal.util.ServerType;

public class SaveTask implements Runnable {
    @Override
    public void run() {
        DataFileManager.save();
        Updater.checkForUpdates();
        new Logger(ServerType.SPIGOT);
    }
}

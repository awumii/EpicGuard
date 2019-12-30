package io.github.polskistevek.epicguard.bukkit.task;

import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import io.github.polskistevek.epicguard.bukkit.util.Updater;
import io.github.polskistevek.epicguard.universal.util.Logger;
import io.github.polskistevek.epicguard.universal.util.ServerType;

public class SaveTask implements Runnable {
    @Override
    public void run() {
        DataFileManager.save();
        Updater.checkForUpdates();
        new Logger(ServerType.SPIGOT);
    }
}

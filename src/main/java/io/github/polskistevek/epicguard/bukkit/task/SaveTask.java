package io.github.polskistevek.epicguard.bukkit.task;

import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import io.github.polskistevek.epicguard.bukkit.util.Updater;
import io.github.polskistevek.epicguard.utils.Logger;
import io.github.polskistevek.epicguard.utils.ServerType;

public class SaveTask implements Runnable {

    @Override
    public void run() {
        DataFileManager.save();
        Updater.checkForUpdates();
        Logger.create(ServerType.SPIGOT);
    }
}

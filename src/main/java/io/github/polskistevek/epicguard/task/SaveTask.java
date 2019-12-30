package io.github.polskistevek.epicguard.task;

import io.github.polskistevek.epicguard.manager.DataFileManager;
import io.github.polskistevek.epicguard.util.Updater;
import io.github.polskistevek.epicguard.util.Logger;
import io.github.polskistevek.epicguard.util.ServerType;

public class SaveTask implements Runnable {
    @Override
    public void run() {
        DataFileManager.save();
        Updater.checkForUpdates();
        new Logger(ServerType.SPIGOT);
    }
}

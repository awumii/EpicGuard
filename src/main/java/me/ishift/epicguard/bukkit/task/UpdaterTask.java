package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.util.server.Updater;
import me.ishift.epicguard.universal.Logger;

public class UpdaterTask implements Runnable {
    @Override
    public void run() {
        Updater.checkForUpdates();
        Logger.init();
    }
}

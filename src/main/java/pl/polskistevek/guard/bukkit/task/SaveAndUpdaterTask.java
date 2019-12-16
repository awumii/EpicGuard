package pl.polskistevek.guard.bukkit.task;

import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.bukkit.util.Updater;
import pl.polskistevek.guard.utils.Logger;
import pl.polskistevek.guard.utils.ServerType;

public class SaveAndUpdaterTask implements Runnable {

    @Override
    public void run() {
        DataFileManager.save();
        Updater.checkForUpdates();
        new Logger(ServerType.SPIGOT);
        Logger.info("Saved data and checked for updates!", false);
    }
}

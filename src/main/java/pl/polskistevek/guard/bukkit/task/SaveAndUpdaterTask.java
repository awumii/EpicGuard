package pl.polskistevek.guard.bukkit.task;

import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.bukkit.util.Updater;
import pl.polskistevek.guard.utils.Logger;

public class SaveAndUpdaterTask implements Runnable {

    @Override
    public void run() {
        DataFileManager.save();
        Updater.checkForUpdates();
        Logger.info("Saved data and checked for updates!", false);
    }
}

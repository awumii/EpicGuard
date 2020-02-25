package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.cloud.CloudManager;
import me.ishift.epicguard.universal.Logger;

public class CloudTask implements Runnable {
    @Override
    public void run() {
        if (Config.cloudEnabled && Config.cloudBlacklist) {
            CloudManager.connect();
            CloudManager.getCloudBlacklist().forEach(StorageManager::blacklist);
        }
        Logger.init();
    }
}

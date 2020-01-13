package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.BlacklistManager;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.universal.CloudGet;
import me.ishift.epicguard.universal.CloudManager;
import me.ishift.epicguard.universal.Config;

public class CloudTask implements Runnable {
    @Override
    public void run() {
        if (Config.CLOUD_ENABLED) {
            if (Config.CLOUD_BLACKLIST) {
                CloudManager.connect(CloudGet.BLACKLIST);
                CloudManager.getCloudBlacklist().forEach(BlacklistManager::add);
                DataFileManager.save();
            }
        }
    }
}

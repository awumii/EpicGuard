package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.BlacklistManager;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.cloud.CloudGet;
import me.ishift.epicguard.universal.cloud.CloudManager;

public class CloudTask implements Runnable {
    @Override
    public void run() {
        if (Config.cloudEnabled) {
            if (Config.cloudBlacklist) {
                CloudManager.connect(CloudGet.BLACKLIST);
                CloudManager.getCloudBlacklist().forEach(BlacklistManager::add);
                DataFileManager.save();
            }
        }
    }
}

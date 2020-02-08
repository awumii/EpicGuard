package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.BlacklistManager;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.cloud.CloudManager;
import me.ishift.epicguard.universal.types.Platform;
import me.ishift.epicguard.universal.util.Logger;

public class CloudTask implements Runnable {
    @Override
    public void run() {
        if (Config.cloudEnabled && Config.cloudBlacklist) {
            CloudManager.connect();
            CloudManager.getCloudBlacklist().forEach(BlacklistManager::blacklist);
        }
        Logger.create(Platform.SPIGOT);
    }
}

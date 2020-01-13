package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.util.FirewallManager;
import me.ishift.epicguard.universal.CloudGet;
import me.ishift.epicguard.universal.CloudManager;
import me.ishift.epicguard.universal.Config;

public class CloudTask implements Runnable {
    @Override
    public void run() {
        if (Config.CLOUD_ENABLED) {
            CloudManager.connect(CloudGet.BLACKLIST);
            CloudManager.getCloudBlacklist().forEach(FirewallManager::blacklist);
        }
    }
}

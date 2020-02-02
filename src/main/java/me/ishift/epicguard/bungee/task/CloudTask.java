package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.util.FirewallManager;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.cloud.CloudManager;

public class CloudTask implements Runnable {
    @Override
    public void run() {
        if (Config.cloudEnabled && Config.cloudBlacklist) {
            CloudManager.connect();
            CloudManager.getCloudBlacklist().forEach(FirewallManager::blacklist);
        }
    }
}

package me.ishift.epicguard.core.manager;

import de.leonhard.storage.Json;

import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private final Json data;
    private final List<String> blacklist;

    public StorageManager() {
        this.data = new Json("data", "plugins/EpicGuard/data");
        this.blacklist = this.data.getOrSetDefault("blacklist", new ArrayList<>());
    }

    public Json getData() {
        return this.data;
    }

    public void blacklist(String address) {
        if (!this.blacklist.contains(address)) {
            this.blacklist.add(address);
        }
    }

    public boolean isBlacklisted(String address) {
        return this.blacklist.contains(address);
    }
}

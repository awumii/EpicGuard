package me.ishift.epicguard.core.manager;

import de.leonhard.storage.Json;

public class StorageManager {
    private final Json data;

    public StorageManager() {
        this.data = new Json("data", "plugins/EpicGuard/data");
    }

    public Json getData() {
        return this.data;
    }
}

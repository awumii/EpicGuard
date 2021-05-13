package me.xneox.epicguard.core.storage.impl;

import de.leonhard.storage.Json;
import me.xneox.epicguard.core.storage.StorageProvider;

import java.util.HashMap;
import java.util.HashSet;

public class JsonStorageProvider extends StorageProvider {
    private Json storageFile;

    @Override
    public void load() {
        this.storageFile = new Json("storage", "plugins/EpicGuard/data");

        this.addressBlacklist = this.storageFile.getOrSetDefault("blacklist", new HashSet<>());
        this.addressWhitelist = this.storageFile.getOrSetDefault("whitelist", new HashSet<>());
        this.nameBlacklist = this.storageFile.getOrSetDefault("name-blacklist", new HashSet<>());
        this.nameWhitelist = this.storageFile.getOrSetDefault("name-whitelist", new HashSet<>());
        this.accountMap = this.storageFile.getOrSetDefault("account-data", new HashMap<>());
    }

    @Override
    public void save() {
        this.storageFile.set("blacklist", this.addressBlacklist);
        this.storageFile.set("whitelist", this.addressWhitelist);
        this.storageFile.set("name-blacklist", this.nameBlacklist);
        this.storageFile.set("name-whitelist", this.nameWhitelist);
        this.storageFile.set("account-data", this.accountMap);
    }
}

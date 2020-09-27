package me.xneox.epicguard.core.storage.impl;

import de.leonhard.storage.Json;
import me.xneox.epicguard.core.storage.StorageSystem;

import java.util.HashMap;
import java.util.HashSet;

public class Flat extends StorageSystem {
    private Json data;

    @Override
    public void load() {
        this.data = new Json("storage", "plugins/EpicGuard/data");
        this.accountMap = this.data.getOrSetDefault("account-data", new HashMap<>());
        this.blacklist = this.data.getOrSetDefault("blacklist", new HashSet<>());
        this.whitelist = this.data.getOrSetDefault("whitelist", new HashSet<>());
    }

    @Override
    public void save() {
        this.data.set("blacklist", this.blacklist);
        this.data.set("whitelist", this.whitelist);
        this.data.set("account-data", this.accountMap);
    }
}

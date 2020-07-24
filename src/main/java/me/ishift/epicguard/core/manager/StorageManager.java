package me.ishift.epicguard.core.manager;

import de.leonhard.storage.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class StorageManager {
    private final Json data;
    private final List<String> blacklist;
    private final List<String> whitelist;

    private final Collection<String> pingCache = new HashSet<>();

    public StorageManager() {
        this.data = new Json("storage", "plugins/EpicGuard/data");

        this.blacklist = this.data.getOrSetDefault("blacklist", new ArrayList<>());
        this.whitelist = this.data.getOrSetDefault("whitelist", new ArrayList<>());
    }

    public void save() {
        this.data.set("blacklist", this.blacklist);
        this.data.set("whitelist", this.whitelist);
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

    public void whitelist(String address) {
        this.blacklist.remove(address);
        if (!this.whitelist.contains(address)) {
            this.whitelist.add(address);
        }
    }

    public boolean isWhitelisted(String address) {
        return this.whitelist.contains(address);
    }

    public List<String> getBlacklist() {
        return this.blacklist;
    }

    public List<String> getWhitelist() {
        return this.whitelist;
    }

    public Collection<String> getPingCache() {
        return this.pingCache;
    }
}

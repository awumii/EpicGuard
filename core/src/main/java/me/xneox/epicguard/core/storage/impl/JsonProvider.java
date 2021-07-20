package me.xneox.epicguard.core.storage.impl;

import de.leonhard.storage.Json;
import me.xneox.epicguard.core.storage.StorageManager;
import me.xneox.epicguard.core.storage.StorageProvider;
import me.xneox.epicguard.core.util.FileUtils;

import java.util.*;

@Deprecated
public class JsonProvider implements StorageProvider {
    private final StorageManager storageManager;
    private Json storageFile;

    public JsonProvider(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Override
    public void load() {
        this.storageFile = new Json("storage", FileUtils.EPICGUARD_DIR + "/data");

        // This still uses the awful old data storage format.
        // TODO: Auto convert to sqlite.
        // TODO: this does nothing currently lol
        Collection<String> addressBlacklist = this.storageFile.getOrSetDefault("blacklist", new HashSet<>());
        Collection<String> addressWhitelist = this.storageFile.getOrSetDefault("whitelist", new HashSet<>());
        Collection<String> nameBlacklist = this.storageFile.getOrSetDefault("name-blacklist", new HashSet<>());
        Collection<String> nameWhitelist = this.storageFile.getOrSetDefault("name-whitelist", new HashSet<>());
        Map<String, List<String>> accountMap = this.storageFile.getOrSetDefault("account-data", new HashMap<>());
    }

    @Override
    public void save() {
        this.storageFile.set("blacklist", this.addressBlacklist);
        this.storageFile.set("whitelist", this.addressWhitelist);
        this.storageFile.set("name-blacklist", this.nameBlacklist);
        this.storageFile.set("name-whitelist", this.nameWhitelist);
        this.storageFile.set("account-data", this.accountMap);

        this.storageManager.addresses().forEach((address, meta) -> {
            // TODO: i'm too tired for this shit
        });
    }
}

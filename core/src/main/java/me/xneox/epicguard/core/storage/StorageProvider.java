package me.xneox.epicguard.core.storage;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class StorageProvider {
    protected Collection<String> addressBlacklist;
    protected Collection<String> addressWhitelist;

    protected Collection<String> nameBlacklist;
    protected Collection<String> nameWhitelist;

    protected Map<String, List<String>> accountMap;

    public Collection<String> addressBlacklist() {
        return this.addressBlacklist;
    }

    public Collection<String> addressWhitelist() {
        return this.addressWhitelist;
    }

    public Collection<String> nameBlacklist() {
        return this.nameBlacklist;
    }

    public Collection<String> nameWhitelist() {
        return this.nameWhitelist;
    }

    public Map<String, List<String>> accountMap() {
        return this.accountMap;
    }

    public abstract void load();

    public abstract void save();
}

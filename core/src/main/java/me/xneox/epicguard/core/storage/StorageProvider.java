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

    public Collection<String> getAddressWhitelist() {
        return addressWhitelist;
    }

    public Collection<String> getAddressBlacklist() {
        return addressBlacklist;
    }

    public Collection<String> getNameBlacklist() {
        return nameBlacklist;
    }

    public Collection<String> getNameWhitelist() {
        return nameWhitelist;
    }

    public Map<String, List<String>> getAccountMap() {
        return accountMap;
    }

    public abstract void load();

    public abstract void save();
}

package me.xneox.epicguard.core.storage;

public interface StorageProvider {
    void load() throws Exception;

    void save() throws Exception;
}

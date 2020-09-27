package me.ishift.epicguard.core.storage;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.config.PluginConfiguration;
import me.ishift.epicguard.core.storage.impl.Flat;
import me.ishift.epicguard.core.storage.impl.MySQL;

public class StorageFactory {
    private final EpicGuard epicGuard;

    public StorageFactory(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public StorageSystem createStorage(String type) {
        if (type.equalsIgnoreCase("flat")) {
            return new Flat();
        } else if (type.equalsIgnoreCase("mysql")) {
            PluginConfiguration.MySQLSettings sql = this.epicGuard.getConfig().mysql;
            return new MySQL(sql.address, sql.port, sql.database, sql.user, sql.password, sql.useSSL);
        } else {
            throw new IllegalArgumentException("Invalid configuration! Storage type '" + type + "' is not supported. Use MYSQL or FLAT.");
        }
    }
}

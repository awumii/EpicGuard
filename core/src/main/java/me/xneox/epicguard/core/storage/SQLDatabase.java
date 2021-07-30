package me.xneox.epicguard.core.storage;

import co.aikar.idb.*;
import me.xneox.epicguard.core.config.PluginConfiguration;
import me.xneox.epicguard.core.util.FileUtils;

import java.sql.*;
import java.util.Arrays;
import java.util.Map;

public class SQLDatabase {
    private final StorageManager storageManager;

    public SQLDatabase(StorageManager storageManager, PluginConfiguration.Storage config) {
        this.storageManager = storageManager;
        this.connect(config);
    }

    /**
     * Initial connection to the database.
     */
    private void connect(PluginConfiguration.Storage config) {
        DatabaseOptions.DatabaseOptionsBuilder builder = DatabaseOptions.builder();
        if (config.useMySQL()) {
            builder.mysql(config.user(), config.password(), config.database(), config.host() + ":" + config.port());
        } else {
            builder.sqlite(FileUtils.EPICGUARD_DIR + "/data/storage.db");
        }

        Database database = PooledDatabaseOptions.builder().options(builder.build()).createHikariDatabase();
        DB.setGlobalDatabase(database);
    }

    /**
     * Loads the address data from the default table.
     */
    public void loadData() throws SQLException {
        DB.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_addresses(" +
                "`address` VARCHAR(255) NOT NULL PRIMARY KEY, " +
                "`blacklisted` BOOLEAN NOT NULL, " +
                "`whitelisted` BOOLEAN NOT NULL, " +
                "`nicknames` TEXT NOT NULL" +
                ")");

        // TODO: don't do this
        for (DbRow row : DB.getResults("SELECT * FROM epicguard_addresses")) {
            AddressMeta meta = new AddressMeta(
                    row.getInt("blacklisted") == 1,
                    row.getInt("whitelisted") == 1,
                    Arrays.asList(row.getString("nicknames").split(",")));

            this.storageManager.addresses().put(row.getString("address"), meta);
        }
    }

    /**
     * Inserts address data from memory to the default table.
     */
    public void saveData() throws SQLException {
        for (Map.Entry<String, AddressMeta> entry : this.storageManager.addresses().entrySet()) {
            AddressMeta meta = entry.getValue();

            // TODO: don't do this
            DB.executeInsert("INSERT OR REPLACE INTO epicguard_addresses" +
                    " (address, blacklisted, whitelisted, nicknames)" +
                    " VALUES (?, ?, ?, ?)", entry.getKey(), meta.blacklisted(), meta.whitelisted(), String.join(",", meta.nicknames()));
        }
    }
}
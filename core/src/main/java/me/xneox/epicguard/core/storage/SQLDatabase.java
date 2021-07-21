package me.xneox.epicguard.core.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.xneox.epicguard.core.config.PluginConfiguration;
import me.xneox.epicguard.core.util.FileUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;

public class SQLDatabase {
    private final StorageManager storageManager;
    private HikariDataSource source;

    public SQLDatabase(StorageManager storageManager, PluginConfiguration.Storage config) {
        this.storageManager = storageManager;
        this.connect(config);
    }

    /**
     * Initial connection to the database.
     */
    private void connect(PluginConfiguration.Storage config) {
        HikariConfig cfg = new HikariConfig();
        if (config.useMySQL()) {
            cfg.setJdbcUrl("jdbc:mysql://" + config.host() + ':' + config.port() + '/' + config.database());
            cfg.setUsername(config.user());
            cfg.setPassword(config.password());
            cfg.addDataSourceProperty("useSSL", config.useSSL());

            // some additional settings
            cfg.addDataSourceProperty("useServerPrepStmts", true);
            cfg.addDataSourceProperty("cachePrepStmts", true);
            cfg.addDataSourceProperty("prepStmtCacheSize", 250);
        } else {
            cfg.setJdbcUrl("jdbc:sqlite:" + FileUtils.EPICGUARD_DIR + "/data/storage.db");
        }

        this.source = new HikariDataSource(cfg);
    }

    /**
     * Loads the address data from the default table.
     */
    public void loadData() throws SQLException {
        // Creating the default table.
        String tableCreateStatement = "CREATE TABLE IF NOT EXISTS epicguard_addresses(" +
                "`address` VARCHAR(255) NOT NULL, " +
                "`blacklisted` BOOLEAN NOT NULL, " +
                "`whitelisted` BOOLEAN NOT NULL, " +
                "`nicknames` TEXT NOT NULL" +
                ")";

        Statement createStatement = this.source.getConnection().createStatement();
        createStatement.executeUpdate(tableCreateStatement);

        // Load addresses from database
        PreparedStatement statement = this.source.getConnection().prepareStatement("SELECT * FROM epicguard_addresses");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String address = rs.getString(1);
            boolean blacklisted = rs.getBoolean(2);
            boolean whitelisted = rs.getBoolean(3);
            String seenNames = rs.getString(4);

            this.storageManager.addresses().put(address, new AddressMeta(blacklisted, whitelisted, Arrays.asList(seenNames.split(","))));
        }
    }

    /**
     * Inserts address data from memory to the default table.
     */
    public void saveData() throws SQLException {
        for (Map.Entry<String, AddressMeta> entry : this.storageManager.addresses().entrySet()) {
            PreparedStatement ps = this.source.getConnection().prepareStatement(
                    "INSERT OR REPLACE INTO epicguard_addresses" +
                    " (address, blacklisted, whitelisted, nicknames)" +
                    " VALUES (?, ?, ?, ?)");

            AddressMeta meta = entry.getValue();
            ps.setString(1, entry.getKey());
            ps.setBoolean(2, meta.blacklisted());
            ps.setBoolean(3, meta.whitelisted());
            ps.setString(4, String.join(",", meta.nicknames()));
            ps.execute();
        }
    }
}

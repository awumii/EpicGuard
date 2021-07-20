package me.xneox.epicguard.core.storage.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.xneox.epicguard.core.storage.AddressMeta;
import me.xneox.epicguard.core.storage.StorageManager;
import me.xneox.epicguard.core.storage.StorageProvider;
import me.xneox.epicguard.core.util.FileUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class SQLiteProvider implements StorageProvider {
    private final StorageManager storageManager;
    private HikariDataSource source;

    public SQLiteProvider(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Override
    public void load() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + FileUtils.EPICGUARD_DIR + "/data/storage.db");

        this.source = new HikariDataSource(config);

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

            this.storageManager.addresses().put(address, new AddressMeta(blacklisted, whitelisted, seenNames));
        }
    }

    @Override
    public void save() throws SQLException {
        for (Map.Entry<String, AddressMeta> entry : this.storageManager.addresses().entrySet()) {
            PreparedStatement ps = this.source.getConnection().prepareStatement(
                    "INSERT INTO epicguard_addresses(address, blacklisted, whitelisted, nicknames)" +
                            " VALUES (?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "address=VALUES(address), blacklisted=VALUES(blacklisted), whitelisted=VALUES(whitelisted), nicknames=VALUES(nicknames)");

            AddressMeta meta = entry.getValue();
            ps.setString(1, entry.getKey());
            ps.setBoolean(2, meta.blacklisted());
            ps.setBoolean(3, meta.whitelisted());
            ps.setString(4, String.join(",", meta.nicknames()));
            ps.execute();
        }
    }
}

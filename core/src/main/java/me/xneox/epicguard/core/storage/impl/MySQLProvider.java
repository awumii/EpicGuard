package me.xneox.epicguard.core.storage.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import me.xneox.epicguard.core.config.PluginConfiguration.Storage;
import me.xneox.epicguard.core.storage.AddressMeta;
import me.xneox.epicguard.core.storage.StorageManager;
import me.xneox.epicguard.core.storage.StorageProvider;

public class MySQLProvider extends StorageProvider {
  private Connection connection;

  public MySQLProvider(StorageManager storageManager) {
    super(storageManager);
  }

  @Override
  public void connect(Storage config) throws SQLException {
    var hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl("jdbc:mysql://" + config.host() + ":" + config.port() + "/" + config.database());
    hikariConfig.setUsername(config.user());
    hikariConfig.setPassword(config.password());

    hikariConfig.addDataSourceProperty("cachePrepStmts", true);
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

    this.connection = new HikariDataSource(hikariConfig).getConnection();
  }

  @Override
  public void load() throws SQLException {
    try (var statement = this.connection.prepareStatement(
        "CREATE TABLE IF NOT EXISTS epicguard_addresses("
        + "`address` VARCHAR(255) NOT NULL PRIMARY KEY, "
        + "`blacklisted` BOOLEAN NOT NULL, "
        + "`whitelisted` BOOLEAN NOT NULL, "
        + "`nicknames` TEXT NOT NULL"
        + ")")) {

      statement.executeUpdate();
    }

    try (var statement = this.connection.prepareStatement("SELECT * FROM epicguard_addresses");
        var rs = statement.executeQuery()) {

      while (rs.next()) {
        var meta = new AddressMeta(
            rs.getBoolean("blacklisted"),
            rs.getBoolean("whitelisted"),
            new ArrayList<>(Arrays.asList(rs.getString("nicknames").split(","))));

        this.storageManager.addresses().put(rs.getString("address"), meta);
      }
    }
  }

  @Override
  public void save() throws SQLException {
    for (Map.Entry<String, AddressMeta> entry : this.storageManager.addresses().entrySet()) {
      var meta = entry.getValue();

      try (var statement = this.connection.prepareStatement(
          "REPLACE INTO"
          + " epicguard_addresses(address, blacklisted, whitelisted, nicknames)"
          + " VALUES(?, ?, ?, ?)")) {

        statement.setString(1, entry.getKey());
        statement.setBoolean(2, meta.blacklisted());
        statement.setBoolean(3, meta.whitelisted());
        statement.setString(4, String.join(",", meta.nicknames()));

        statement.executeUpdate();
      }
    }
  }
}

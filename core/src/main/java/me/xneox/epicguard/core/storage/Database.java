package me.xneox.epicguard.core.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.util.FileUtils;
import org.jetbrains.annotations.NotNull;

public class Database {
  private final EpicGuard core;
  private HikariDataSource source;

  public Database(@NotNull EpicGuard core) {
    this.core = core;
  }

  // Initial connection to the database, obtaining HikariDataSource.
  public void connect() throws SQLException {
    var config = this.core.config().storage();
    var hikariConfig = new HikariConfig();

    if (config.useMySQL()) {
      hikariConfig.setJdbcUrl("jdbc:mysql://" + config.host() + ":" + config.port() + "/" + config.database());
      hikariConfig.setUsername(config.user());
      hikariConfig.setPassword(config.password());

      hikariConfig.addDataSourceProperty("cachePrepStmts", true);
      hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
      hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    } else {
      var file = FileUtils.create(new File(FileUtils.EPICGUARD_DIR, "database.db"));
      hikariConfig.setJdbcUrl("jdbc:sqlite:" + file.getPath());
    }

    this.source = new HikariDataSource(hikariConfig);
    this.load();
  }

  // Creating default table and reading addresses from the database.
  public void load() throws SQLException {
    try (var statement = this.source.getConnection().prepareStatement(
        "CREATE TABLE IF NOT EXISTS epicguard_addresses("
        + "`address` VARCHAR(255) NOT NULL PRIMARY KEY, "
        + "`blacklisted` BOOLEAN NOT NULL, "
        + "`whitelisted` BOOLEAN NOT NULL, "
        + "`nicknames` TEXT NOT NULL"
        + ")")) {
      statement.executeUpdate();
    }

    try (var statement = this.source.getConnection().prepareStatement("SELECT * FROM epicguard_addresses");
        var rs = statement.executeQuery()) {

      while (rs.next()) {
        var meta = new AddressMeta(
            rs.getBoolean("blacklisted"),
            rs.getBoolean("whitelisted"),
            new ArrayList<>(Arrays.asList(rs.getString("nicknames").split(","))));

        this.core.storageManager().addresses().put(rs.getString("address"), meta);
      }
    }
  }

  // Saving cached addresses to the database.
  public void save() throws SQLException {
    for (Map.Entry<String, AddressMeta> entry : this.core.storageManager().addresses().entrySet()) {
      var meta = entry.getValue();

      try (var statement = this.source.getConnection().prepareStatement(
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

  // Shut down the Hikari connection pool.
  public void shutdown() {
    this.source.close();
  }

  // i hate this
  /*
  private boolean getBoolean(@NotNull ResultSet resultSet, @NotNull String column) {
    if (this.core.config().storage().useMySQL()) {
      return resultSet.getBoolean(column);
    }
    return resultSet.getInt(column) == 1;
  }
   */
}

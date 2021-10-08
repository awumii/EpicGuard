/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.core.storage;

import co.aikar.idb.DB;
import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.DbRow;
import co.aikar.idb.PooledDatabaseOptions;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import me.xneox.epicguard.core.config.PluginConfiguration;
import me.xneox.epicguard.core.util.FileUtils;
import org.jetbrains.annotations.NotNull;

/**
 * This class manages the SQL database for storing all known {@link AddressMeta}'s SQLite and MySQL
 * are both supported with the same queries.
 * <p>
 * TODO: Maybe don't keep all data in the memory? Some caching system instead of bulk read/write.
 */
public record SQLDatabase(@NotNull StorageManager storageManager, @NotNull PluginConfiguration.Storage config) {

  /**
   * Preparing the database connection
   */
  public void connect() {
    DatabaseOptions.DatabaseOptionsBuilder builder = DatabaseOptions.builder();
    if (config.useMySQL()) {
      builder.mysql(config.user(), config.password(), config.database(), config.host() + ":" + config.port());
    } else {
      builder.sqlite(FileUtils.EPICGUARD_DIR + "/data/storage.db");
    }

    Database database = PooledDatabaseOptions.builder()
        .options(builder.build())
        .createHikariDatabase();

    DB.setGlobalDatabase(database);
  }

  /**
   * Loads the address data from the default table.
   */
  public void loadData() throws SQLException {
    DB.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_addresses("
        + "`address` VARCHAR(255) NOT NULL PRIMARY KEY, "
        + "`blacklisted` BOOLEAN NOT NULL, "
        + "`whitelisted` BOOLEAN NOT NULL, "
        + "`nicknames` TEXT NOT NULL"
        + ")");

    for (DbRow row : DB.getResults("SELECT * FROM epicguard_addresses")) {
      AddressMeta meta =
          new AddressMeta(
              getBoolean(row, "blacklisted"),
              getBoolean(row, "whitelisted"),
              new ArrayList<>(Arrays.asList(row.getString("nicknames").split(","))));

      this.storageManager.addresses().put(row.getString("address"), meta);
    }
  }

  /**
   * Inserts address data from memory to the default table.
   */
  public void saveData() throws SQLException {
    for (Map.Entry<String, AddressMeta> entry : this.storageManager.addresses().entrySet()) {
      AddressMeta meta = entry.getValue();

      DB.executeInsert("REPLACE INTO"
              + " epicguard_addresses(address, blacklisted, whitelisted, nicknames)"
              + " VALUES(?, ?, ?, ?)",
          entry.getKey(),
          meta.blacklisted(),
          meta.whitelisted(),
          String.join(",", meta.nicknames()));
    }
  }

  // i hate this
  private boolean getBoolean(@NotNull DbRow row, @NotNull String column) {
    if (this.config.useMySQL()) {
      return row.get(column);
    }
    return row.getInt(column) == 1;
  }
}

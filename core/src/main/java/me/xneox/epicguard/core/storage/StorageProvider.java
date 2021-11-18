package me.xneox.epicguard.core.storage;

import java.sql.SQLException;
import me.xneox.epicguard.core.config.PluginConfiguration;

public abstract class StorageProvider {
  protected final StorageManager storageManager;

  protected StorageProvider(StorageManager storageManager) {
    this.storageManager = storageManager;
  }

  public abstract void connect(PluginConfiguration.Storage config) throws SQLException;

  public abstract void load() throws SQLException;

  public abstract void save() throws SQLException;
}

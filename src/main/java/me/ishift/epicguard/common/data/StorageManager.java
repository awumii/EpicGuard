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

package me.ishift.epicguard.common.data;

import de.leonhard.storage.Yaml;
import lombok.Getter;
import me.ishift.epicguard.common.data.storage.Flat;
import me.ishift.epicguard.common.data.storage.MySQL;

@Getter
public class StorageManager {
    private final StorageType type;
    private final DataStorage storage;
    private final Yaml config;

    private final String mysqlHost;
    private final int mysqlPort;
    private final String mysqlUser;
    private final String mysqlDatabase;
    private final String mysqlPassword;
    private final boolean mysqlSSL;
    private final int mysqlTimeout;
    private final int mysqlPoolSize;

    public StorageManager() {
        this.config = new Yaml("storage", "plugins/EpicGuard");
        this.config.setHeader("You can set the storage-mode to FLAT or MYSQL.", "After you set this to MySQL, restart the server.", "Currently, there is no option to transfer from FLAT to MYSQL");

        final String storageTypeString = this.config.getOrSetDefault("storage-mode", "FLAT");
        this.type = StorageType.valueOf(storageTypeString);

        mysqlPassword = config.getOrSetDefault("mysql.connection.password", "password");
        mysqlUser = config.getOrSetDefault("mysql.connection.username", "admin");
        mysqlHost = config.getOrSetDefault("mysql.connection.host", "localhost");
        mysqlPort = config.getOrSetDefault("mysql.connection.port", 3306);
        mysqlDatabase = config.getOrSetDefault("mysql.connection.database", "your-database");
        mysqlSSL = config.getOrSetDefault("mysql.settings.use-ssl", true);
        mysqlPoolSize = config.getOrSetDefault("mysql.settings.pool-size", 5);
        mysqlTimeout = config.getOrSetDefault("mysql.settings.connection-timeout", 30000);

        if (this.type == StorageType.FLAT) {
            this.storage = new Flat();
        } else {
            this.storage = new MySQL(this);
        }

        this.storage.load();
    }
}

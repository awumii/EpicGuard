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
import me.ishift.epicguard.common.data.storage.Flat;
import me.ishift.epicguard.common.data.storage.MySQL;

public class StorageManager {
    public static StorageType storageType;
    public static String mysqlHost;
    public static int mysqlPort;
    public static String mysqlDatabase;
    public static String mysqlUser;
    public static String mysqlPassword;
    public static boolean mysqlSSL;
    public static int poolSize;
    public static int connectionTimeout;
    private static DataStorage storage;

    public static void init() {
        final Yaml config = new Yaml("storage_config", "plugins/EpicGuard");

        final String storageTypeString = config.getOrSetDefault("storage-mode", "FLAT");
        storageType = StorageType.valueOf(storageTypeString);

        config.setDefault("DO-NOT-USE-MYSQL-PLEASE", "IT IS NOT READY YET, IT WILL BE WORKING IN THE FUTURE!!");

        mysqlPassword = config.getOrSetDefault("mysql.user.password", "password");
        mysqlUser = config.getOrSetDefault("mysql.user.username", "admin");
        mysqlHost = config.getOrSetDefault("mysql.connection.host", "127.0.0.1");
        mysqlPort = config.getOrSetDefault("mysql.connection.port", 3306);
        mysqlDatabase = config.getOrSetDefault("mysql.connection.database", "your-database");
        mysqlSSL = config.getOrSetDefault("mysql.connection.use-ssl", true);

        poolSize = config.getOrSetDefault("mysql.settings.pool-size", 5);
        connectionTimeout = config.getOrSetDefault("mysql.settings.connection-timeout", 30000);

        if (storageType == StorageType.FLAT) {
            storage = new Flat();
        } else {
            storage = new MySQL();
        }
        storage.load();
    }

    public static void save() {
        storage.save();
    }

    public static DataStorage getStorage() {
        return storage;
    }
}

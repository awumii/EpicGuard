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

import java.util.Date;

@Getter
public class StorageManager {
    private final DataStorage storage;
    private final boolean mysql;
    private final Yaml config;

    private Date lastSave;

    // MySQL section
    private final String host;
    private final int port;
    private final boolean ssl;
    private final String user;
    private final String database;
    private final String password;

    public StorageManager() {
        this.config = new Yaml("mysql", "plugins/EpicGuard");
        this.config.setHeader("You can set the value 'enabled' to: ", "true - plugin will store data on the MySQL server.", "false - plugin will store data on the local file.", "After changing this value, restart the server.");

        this.mysql = this.config.getOrSetDefault("enabled", false);

        this.password = this.config.getOrSetDefault("mysql.password", "password");
        this.user = this.config.getOrSetDefault("mysql.username", "admin");
        this.database = this.config.getOrSetDefault("mysql.database", "database");
        this.host = this.config.getOrSetDefault("mysql.host", "localhost");
        this.port = this.config.getOrSetDefault("mysql.port", 3306);
        this.ssl = this.config.getOrSetDefault("mysql.use-ssl", false);

        if (this.mysql) {
            this.storage = new MySQL(this);
        } else {
            this.storage = new Flat();
        }

        this.lastSave = new Date(System.currentTimeMillis());
        this.storage.load();
    }

    public void save() {
        this.storage.save();
        this.lastSave = new Date(System.currentTimeMillis());
    }
}

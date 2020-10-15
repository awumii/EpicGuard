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

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.config.PluginConfiguration;
import me.xneox.epicguard.core.storage.impl.Flat;
import me.xneox.epicguard.core.storage.impl.MySQL;

public class StorageFactory {
    private final EpicGuard epicGuard;

    public StorageFactory(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public StorageSystem createStorage(String type) {
        if (type.equalsIgnoreCase("flat")) {
            return new Flat();
        } else if (type.equalsIgnoreCase("mysql")) {
            PluginConfiguration.MySQLSettings sql = this.epicGuard.getConfig().mysql;
            return new MySQL(sql.address, sql.port, sql.database, sql.user, sql.password, sql.useSSL);
        } else {
            throw new IllegalArgumentException("Invalid configuration! Storage type '" + type + "' is not supported. Use MYSQL or FLAT.");
        }
    }
}

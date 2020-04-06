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
    private static DataStorage storage;

    public static void load() {
        /* This will be enabled after MYSQL is finished.

        final Yaml config = new Yaml("storage_config", "plugins/EpicGuard");
        config.setHeader("You can set this to FLAT or MYSQL.", "After setting this to MYSQL, more settings will appear.");

        final String storageTypeString = config.getOrSetDefault("storage-mode", "FLAT");
        storageType = StorageType.valueOf(storageTypeString);
        */
        storageType = StorageType.FLAT;

        if (storageType == StorageType.FLAT) {
            storage = new Flat();
        } else {
            storage = new MySQL();
        }
        storage.load();
    }

    public static void shutdown() {
        storage.save();
    }

    public static DataStorage getStorage() {
        return storage;
    }

    public static StorageType getStorageType() {
        return storageType;
    }
}

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

import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.data.storage.Flat;
import me.ishift.epicguard.common.data.storage.MySQL;

public class StorageManager {
    private static DataStorage storage;

    public static void init() {
        if (Config.storageType == StorageType.FLAT) {
            storage = new Flat();
            return;
        }
        storage = new MySQL();
    }

    public static void save() {
        storage.save();
    }

    public static DataStorage getStorage() {
        return storage;
    }
}

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

package me.ishift.epicguard.common.data.storage;

import de.leonhard.storage.Json;
import me.ishift.epicguard.common.data.DataStorage;

import java.util.HashSet;

public class Flat extends DataStorage {
    private Json storage;

    @Override
    public void load() {
        this.storage = new Json("storage", "plugins/EpicGuard/data");
        this.blacklist = this.storage.getOrSetDefault("addresses.blacklist", new HashSet<>());
        this.whitelist = this.storage.getOrSetDefault("addresses.whitelist", new HashSet<>());
    }

    @Override
    public void save() {
        this.storage.set("addresses.blacklist", this.blacklist);
        this.storage.set("addresses.whitelist", this.whitelist);
    }

    public Json getFile() {
        return storage;
    }
}

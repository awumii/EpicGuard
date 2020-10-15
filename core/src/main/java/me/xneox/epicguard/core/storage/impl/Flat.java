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

package me.xneox.epicguard.core.storage.impl;

import de.leonhard.storage.Json;
import me.xneox.epicguard.core.storage.StorageSystem;

import java.util.HashMap;
import java.util.HashSet;

public class Flat extends StorageSystem {
    private Json data;

    @Override
    public void load() {
        this.data = new Json("storage", "plugins/EpicGuard/data");
        this.accountMap = this.data.getOrSetDefault("account-data", new HashMap<>());
        this.blacklist = this.data.getOrSetDefault("blacklist", new HashSet<>());
        this.whitelist = this.data.getOrSetDefault("whitelist", new HashSet<>());
    }

    @Override
    public void save() {
        this.data.set("blacklist", this.blacklist);
        this.data.set("whitelist", this.whitelist);
        this.data.set("account-data", this.accountMap);
    }
}

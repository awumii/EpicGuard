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
    private Json file;

    @Override
    public void load() {
        this.file = new Json("storage", "plugins/EpicGuard/data");
        this.setBlacklist(this.file.getOrSetDefault("addresses.blacklist", new HashSet<>()));
        this.setWhitelist(this.file.getOrSetDefault("addresses.whitelist", new HashSet<>()));
        this.setPingData(this.file.getOrSetDefault("addresses.ping", new HashSet<>()));
        this.setRejoinData(this.file.getOrSetDefault("nicknames.reconnect", new HashSet<>()));
    }

    @Override
    public void save() {
        this.file.set("addresses.blacklist", this.getBlacklist());
        this.file.set("addresses.whitelist", this.getWhitelist());
        this.file.set("addresses.ping", this.getPingData());
        this.file.set("nicknames.reconnect", this.getRejoinData());
    }
}

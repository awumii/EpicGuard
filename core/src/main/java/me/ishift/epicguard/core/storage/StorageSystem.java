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

package me.ishift.epicguard.core.storage;

import me.ishift.epicguard.core.user.BotUser;

import java.util.*;

public abstract class StorageSystem {
    protected Collection<String> pingCache = new HashSet<>();

    protected Collection<String> blacklist;
    protected Collection<String> whitelist;
    protected Map<String, List<String>> accountMap;

    /**
     * Retrieves a list of nicknames used by specified IP Address.
     * If a nickname used currently by this address is not in the map, it will be added.
     */
    public List<String> getAccounts(BotUser user) {
        List<String> nicknames = this.accountMap.getOrDefault(user.getAddress(), new ArrayList<>());
        if (!nicknames.contains(user.getNickname())) {
            nicknames.add(user.getNickname());
        }

        this.accountMap.put(user.getAddress(), nicknames);
        return nicknames;
    }

    public void blacklist(String address) {
        if (!this.blacklist.contains(address)) {
            this.blacklist.add(address);
        }
    }

    public boolean isBlacklisted(String address) {
        return this.blacklist.contains(address);
    }

    public void whitelist(String address) {
        this.blacklist.remove(address);
        if (!this.whitelist.contains(address)) {
            this.whitelist.add(address);
        }
    }

    public boolean isWhitelisted(String address) {
        return this.whitelist.contains(address);
    }

    public Collection<String> getBlacklist() {
        return this.blacklist;
    }

    public Collection<String> getWhitelist() {
        return this.whitelist;
    }

    public Collection<String> getPingCache() {
        return this.pingCache;
    }

    public abstract void load();

    public abstract void save();
}

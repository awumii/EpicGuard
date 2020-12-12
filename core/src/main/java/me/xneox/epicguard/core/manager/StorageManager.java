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

package me.xneox.epicguard.core.manager;

import de.leonhard.storage.Json;
import me.xneox.epicguard.core.user.BotUser;
import org.diorite.libs.org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.*;

public class StorageManager {
    private final Json data;

    private final Collection<String> pingCache = new HashSet<>();
    private final Map<String, List<String>> accountMap;
    private final Collection<String> blacklist;
    private final Collection<String> whitelist;

    public StorageManager() {
        this.data = new Json("storage", "plugins/EpicGuard/data");

        this.accountMap = this.data.getOrSetDefault("account-data", new HashMap<>());
        this.blacklist = this.data.getOrSetDefault("blacklist", new HashSet<>());
        this.whitelist = this.data.getOrSetDefault("whitelist", new HashSet<>());
    }

    public void save() {
        this.data.set("blacklist", this.blacklist);
        this.data.set("whitelist", this.whitelist);
        this.data.set("account-data", this.accountMap);
    }

    /**
     * Retrieves a list of nicknames used by specified IP Address.
     * If a nickname used currently by this address is not in the map, it will be added.
     */
    @Nonnull
    public List<String> getAccounts(@Nonnull BotUser user) {
        Validate.notNull(user, "BotUser cannot be null!");
        List<String> nicknames = this.accountMap.getOrDefault(user.getAddress(), new ArrayList<>());
        if (!nicknames.contains(user.getNickname())) {
            nicknames.add(user.getNickname());
        }

        this.accountMap.put(user.getAddress(), nicknames);
        return nicknames;
    }

    public void blacklist(@Nonnull String address) {
        Validate.notNull(address, "Address cannot be null!");
        if (!this.blacklist.contains(address)) {
            this.blacklist.add(address);
        }
    }

    public boolean isBlacklisted(String address) {
        return this.blacklist.contains(address);
    }

    public void whitelist(@Nonnull String address) {
        Validate.notNull(address, "Address cannot be null!");
        if (!this.whitelist.contains(address)) {
            this.whitelist.add(address);
        }
    }

    public boolean isWhitelisted(String address) {
        return this.whitelist.contains(address);
    }

    @Nonnull
    public Collection<String> getBlacklist() {
        return this.blacklist;
    }

    @Nonnull
    public Collection<String> getWhitelist() {
        return this.whitelist;
    }

    @Nonnull
    public Collection<String> getPingCache() {
        return this.pingCache;
    }
}

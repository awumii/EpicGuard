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

import com.google.common.net.InetAddresses;
import de.leonhard.storage.Json;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.storage.impl.JsonStorageProvider;
import me.xneox.epicguard.core.user.PendingUser;
import org.diorite.libs.org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * This class manages the stored data and some global cache.
 *
 * TODO: Other storage implementations than just JSON.
 */
@SuppressWarnings("UnstableApiUsage")
public class StorageManager {
    private final StorageProvider provider; // Storage implementation.
    private final Collection<String> pingCache = new HashSet<>(); // Stores addresses of users who pinged the server.

    public StorageManager() {
        this.provider = new JsonStorageProvider();
        this.provider.load();
    }

    public StorageProvider getProvider() {
        return provider;
    }

    /**
     * Retrieves a list of nicknames used by specified IP Address.
     */
    @Nonnull
    public List<String> getAccounts(@Nonnull PendingUser user) {
        Validate.notNull(user, "BotUser cannot be null!");
        return this.provider.getAccountMap().getOrDefault(user.getAddress(), new ArrayList<>());
    }

    /**
     * If the user's address is not in the accountMap, it will be added.
     */
    public void updateAccounts(@Nonnull PendingUser user) {
        Validate.notNull(user, "BotUser cannot be null!");

        List<String> accounts = this.getAccounts(user);
        if (!accounts.contains(user.getNickname())) {
            accounts.add(user.getNickname());
        }

        this.provider.getAccountMap().put(user.getAddress(), accounts);
    }

    /**
     * Searches for the last used address of the specified nickname.
     * Returns null if not found.
     */
    @Nullable
    public String findByNickname(@Nonnull String nickname) {
        return this.provider.getAccountMap().entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(nick -> nick.equalsIgnoreCase(nickname)))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void blacklistPut(@Nonnull String value) {
        if (InetAddresses.isInetAddress(value)) {
            this.provider.getAddressBlacklist().add(value);
        } else {
            this.provider.getNameBlacklist().add(value);
        }
    }

    public void whitelistPut(@Nonnull String value) {
        if (InetAddresses.isInetAddress(value)) {
            this.provider.getAddressWhitelist().add(value);
        } else {
            this.provider.getNameWhitelist().add(value);
        }
    }

    public boolean isBlacklisted(String value) {
        if (InetAddresses.isInetAddress(value)) {
            return this.provider.getAddressBlacklist().contains(value);
        }
        return this.provider.getNameBlacklist().contains(value);
    }

    public boolean isWhitelisted(String value) {
        if (InetAddresses.isInetAddress(value)) {
            return this.provider.getAddressWhitelist().contains(value);
        }
        return this.provider.getNameWhitelist().contains(value);
    }

    public void removeFromBlacklist(String value) {
        if (InetAddresses.isInetAddress(value)) {
            this.provider.getAddressBlacklist().remove(value);
        } else {
            this.provider.getNameBlacklist().remove(value);
        }
    }

    public void removeFromWhitelist(String value) {
        if (InetAddresses.isInetAddress(value)) {
            this.provider.getAddressWhitelist().remove(value);
        } else {
            this.provider.getNameWhitelist().remove(value);
        }
    }

    @Nonnull
    public Collection<String> getPingCache() {
        return this.pingCache;
    }
}

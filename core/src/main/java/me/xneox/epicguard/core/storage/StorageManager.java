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
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.storage.impl.SQLiteProvider;
import me.xneox.epicguard.core.user.PendingUser;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * This class manages the stored data and some global cache.
 *
 * TODO: Other storage implementations than just JSON.
 */
@SuppressWarnings("UnstableApiUsage")
public class StorageManager {
    private final StorageProvider provider;
    private final Map<String, AddressMeta> addresses = new HashMap<>();
    private final Collection<String> pingCache = new HashSet<>(); // Stores addresses of users who pinged the server.

    public StorageManager(EpicGuard epicGuard) {
        this.provider = new SQLiteProvider(this);
        try {
            this.provider.load();
            this.provider.load();
        } catch (Exception e) {
            epicGuard.logger().error("Could not load plugin's storage");
            e.printStackTrace();
        }
    }

    public Map<String, AddressMeta> addresses() {
        return this.addresses;
    }

    /**
     * Retrieves a list of nicknames used by specified IP Address.
     */
    @NotNull
    public List<String> accounts(@NotNull PendingUser user) {
        Validate.notNull(user, "BotUser cannot be null!");
        return this.provider.accountMap().getOrDefault(user.address(), new ArrayList<>());
    }

    /**
     * If the user's address is not in the accountMap, it will be added.
     */
    public void updateAccounts(@NotNull PendingUser user) {
        Validate.notNull(user, "BotUser cannot be null!");

        List<String> accounts = this.accounts(user);
        if (!accounts.contains(user.nickname())) {
            accounts.add(user.nickname());
        }

        this.provider.accountMap().put(user.address(), accounts);
    }

    /**
     * Searches for the last used address of the specified nickname.
     * Returns null if not found.
     */
    @Nullable
    public String findByNickname(@NotNull String nickname) {
        return this.provider.accountMap().entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(nick -> nick.equalsIgnoreCase(nickname)))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void blacklistPut(@NotNull String value) {
        if (InetAddresses.isInetAddress(value)) {
            this.provider.addressBlacklist().add(value);
        } else {
            this.provider.nameBlacklist().add(value);
        }
    }

    public void whitelistPut(@NotNull String value) {
        if (InetAddresses.isInetAddress(value)) {
            this.provider.addressWhitelist().add(value);
        } else {
            this.provider.nameWhitelist().add(value);
        }
    }

    public boolean isBlacklisted(String value) {
        if (InetAddresses.isInetAddress(value)) {
            return this.provider.addressBlacklist().contains(value);
        }
        return this.provider.nameBlacklist().contains(value);
    }

    public boolean isWhitelisted(String value) {
        if (InetAddresses.isInetAddress(value)) {
            return this.provider.addressWhitelist().contains(value);
        }
        return this.provider.nameWhitelist().contains(value);
    }

    public void removeBlacklist(String value) {
        if (InetAddresses.isInetAddress(value)) {
            this.provider.addressBlacklist().remove(value);
        } else {
            this.provider.nameBlacklist().remove(value);
        }
    }

    public void removeWhitelist(String value) {
        if (InetAddresses.isInetAddress(value)) {
            this.provider.addressWhitelist().remove(value);
        } else {
            this.provider.nameWhitelist().remove(value);
        }
    }

    @NotNull
    public Collection<String> pingCache() {
        return this.pingCache;
    }
}

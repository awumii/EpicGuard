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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.net.InetAddresses;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.user.PendingUser;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class manages the stored data and some global cache.
 */
public class StorageManager {
    private final BiMap<String, AddressMeta> addresses = HashBiMap.create();
    private SQLDatabase database;

    private final Collection<String> pingCache = new HashSet<>(); // Stores addresses of users who pinged the server. //TODO: Move this

    public StorageManager(EpicGuard epicGuard) {
        try {
            this.database = new SQLDatabase(this);
            this.database.loadData();
        } catch (Exception e) {
            epicGuard.logger().error("Could not load plugin's storage");
            e.printStackTrace();
        }
    }

    /**
     * Returns an {@link AddressMeta} for the specified address.
     * Creates a new AddressMeta if it doesen't exist for this address.
     */
    @NotNull
    public AddressMeta addressMeta(@NotNull String address) {
        Validate.notNull(address, "Can't get meta for null address!");
        return this.addresses.computeIfAbsent(address, s -> new AddressMeta(false, false, new ArrayList<>()));
    }

    /**
     * When an address is specified:
     *   - Redirects to the {@link #addressMeta(String)} method.
     *   - Never returns null.
     *
     * When an nickname is specified:
     *   - Tries to detect last used address by this nickname.
     *   - If found, redirects to the {@link #addressMeta(String)} method.
     *   - If not found, returns null.
     */
    @Nullable
    public AddressMeta resolveAddressMeta(@NotNull String value) {
        Validate.notNull(value, "Can't resolve meta for null value!");
        //noinspection UnstableApiUsage
        String address = InetAddresses.isInetAddress(value) ? value : lastSeenAddress(value);
        return address != null ? addressMeta(address) : null;
    }

    /**
     * Searches for the last used address of the specified nickname.
     * Returns null if not found.
     */
    @Nullable
    public String lastSeenAddress(@NotNull String nickname) {
        return this.addresses.entrySet().stream()
                .filter(entry -> entry.getValue().nicknames().stream().anyMatch(nick -> nick.equalsIgnoreCase(nickname)))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Checks if the address meta of connecting user contains his current nickname.
     * If absent, it is added.
     */
    public void updateAccounts(@NotNull PendingUser user) {
        List<String> accounts = addressMeta(user.address()).nicknames();
        if (!accounts.contains(user.nickname())) {
            accounts.add(user.nickname());
        }
    }

    /**
     * A legacy method for viewing addresses that meet a specific condition.
     * For example, this can return whitelisted or blacklisted addresses.
     *
     * Returned list is immutable, used only for statistics and command suggestions.
     */
    public List<String> viewAddresses(Predicate<AddressMeta> predicate) {
        return this.addresses.entrySet().stream()
                .filter(entry -> predicate.test(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toUnmodifiableList()); //TODO: Replace with java 16's toList
    }

    public BiMap<String, AddressMeta> addresses() {
        return this.addresses;
    }

    public SQLDatabase database() {
        return this.database;
    }

    @NotNull
    public Collection<String> pingCache() {
        return this.pingCache;
    }
}

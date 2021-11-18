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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.storage.impl.MySQLProvider;
import me.xneox.epicguard.core.storage.impl.SQLiteProvider;
import me.xneox.epicguard.core.user.ConnectingUser;
import me.xneox.epicguard.core.util.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class caches all known {@link AddressMeta}'s and performs various operations on them.
 */
public class StorageManager {
  private final BiMap<String, AddressMeta> addresses = HashBiMap.create(); //TODO: HashBiMap is not thread safe, this may cause issues.
  private final StorageProvider provider;

  private final Collection<String> pingCache = new HashSet<>(); // Stores addresses of users who pinged the server. //TODO: Move this

  public StorageManager(EpicGuard epicGuard) {
    if (epicGuard.config().storage().useMySQL()) {
      this.provider = new MySQLProvider(this);
    } else {
      this.provider = new SQLiteProvider(this);
    }

    try {
      this.provider.connect(epicGuard.config().storage());
      this.provider.load();
    } catch (Exception exception) {
      LogUtils.catchException("Could not load data from SQL database", exception);
    }
  }

  /**
   * Returns an {@link AddressMeta} for the specified address. Creates a new AddressMeta if it
   * doesen't exist for this address.
   */
  @NotNull
  public AddressMeta addressMeta(@NotNull String address) {
    return this.addresses.computeIfAbsent(address, s -> new AddressMeta(false, false, new ArrayList<>()));
  }

  /**
   * When an address is specified:
   * - Redirects to the {@link #addressMeta(String)} method.
   * - Never returns null.
   *
   * When a nickname is specified:
   * - Tries to detect last used address by this nickname.
   * - If found, redirects to the {@link #addressMeta(String)} method.
   * - If not found, returns null.
   */
  @Nullable
  public AddressMeta resolveAddressMeta(@NotNull String value) {
    //noinspection UnstableApiUsage
    String address = InetAddresses.isInetAddress(value) ? value : lastSeenAddress(value);
    return address != null ? addressMeta(address) : null;
  }

  /**
   * Searches for the last used address of the specified nickname.
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
  public void updateAccounts(@NotNull ConnectingUser user) {
    List<String> accounts = addressMeta(user.address()).nicknames();
    if (!accounts.contains(user.nickname())) {
      accounts.add(user.nickname());
    }
  }

  /**
   * A legacy method for viewing addresses that meet a specific condition. For example, this can
   * return whitelisted or blacklisted addresses.
   *
   * Returned list is immutable, used only for statistics and command suggestions.
   */
  @NotNull
  public List<String> viewAddresses(@NotNull Predicate<AddressMeta> predicate) {
    return this.addresses.entrySet().stream()
        .filter(entry -> predicate.test(entry.getValue()))
        .map(Map.Entry::getKey)
        .toList();
  }

  @NotNull
  public BiMap<String, AddressMeta> addresses() {
    return this.addresses;
  }

  @NotNull
  public StorageProvider database() {
    return this.provider;
  }

  @NotNull
  public Collection<String> pingCache() {
    return this.pingCache;
  }
}

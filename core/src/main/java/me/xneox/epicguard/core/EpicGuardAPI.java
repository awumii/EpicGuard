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

package me.xneox.epicguard.core;

import de.leonhard.storage.util.Valid;
import me.xneox.epicguard.core.manager.GeoManager;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

/**
 * Class which can be safely used by other projects.
 * Methods won't be changed/removed after updates.
 */
public final class EpicGuardAPI {
    private static EpicGuard epicGuard;

    /**
     * The {@link GeoManager} class contains methods that you may
     * find useful if you want to check country/city of an address.
     *
     * @return An instance of {@link GeoManager}.
     */
    @Nonnull
    public static GeoManager getGeoManager() {
        validateInstance();
        return epicGuard.getGeoManager();
    }

    /**
     * @return true if server is under attack, false if not.
     */
    public static boolean isUnderAttack() {
        validateInstance();
        return epicGuard.getAttackManager().isAttack();
    }

    /**
     * @return An immutable Collection which contains whitelisted addresses.
     */
    @Nonnull
    public static Collection<String> getWhitelistedAddresses() {
        validateInstance();
        return Collections.unmodifiableCollection(epicGuard.getStorageManager().getProvider().getAddressWhitelist());
    }

    /**
     * @return An immutable Collection which contains blacklisted addresses.
     */
    @Nonnull
    public static Collection<String> getBlacklistedAddresses() {
        validateInstance();
        return Collections.unmodifiableCollection(epicGuard.getStorageManager().getProvider().getAddressWhitelist());
    }

    /**
     * @return An immutable Collection which contains whitelisted nicknames.
     */
    @Nonnull
    public static Collection<String> getWhitelistedNicknames() {
        validateInstance();
        return Collections.unmodifiableCollection(epicGuard.getStorageManager().getProvider().getNameWhitelist());
    }

    /**
     * @return An immutable Collection which contains blacklisted nicknames.
     */
    @Nonnull
    public static Collection<String> getBlacklistedNicknames() {
        validateInstance();
        return Collections.unmodifiableCollection(epicGuard.getStorageManager().getProvider().getNameBlacklist());
    }

    /**
     * @return Current connections per second.
     */
    public static int getConnectionsPerSecond() {
        validateInstance();
        return epicGuard.getAttackManager().getConnectionCounter();
    }

    /**
     * Adds the provided address to blacklist, if it is not already there.
     *
     * @param address Address to be blacklisted.
     */
    public static void blacklistAddress(@Nonnull String address) {
        validateInstance();
        Valid.notNull(address, "Address cannot be null!");

        epicGuard.getStorageManager().blacklistPut(address);
    }

    /**
     * Adds the provided address to whitelist.
     * Also, removes the address from blacklist.
     *
     * @param address Address to be blacklisted.
     */
    public static void whitelistAddress(@Nonnull String address) {
        validateInstance();
        Valid.notNull(address, "Address cannot be null!");

        epicGuard.getStorageManager().whitelistPut(address);
        epicGuard.getStorageManager().getProvider().getAddressBlacklist().remove(address);
    }

    /**
     * @return Instance of the {@link EpicGuard} core class.
     * It is recommended to use methods in this class instead.
     */
    @Nonnull
    public static EpicGuard getInstance() {
        validateInstance();
        return epicGuard;
    }

    /**
     * Checks if the EpicGuard has been initialized already.
     */
    private static void validateInstance() {
        Valid.notNull(epicGuard, "Can't acces EpicGuardAPI because the plugin is not initialized. Have you set is as dependency?.");
    }

    /**
     * This method is used during initialization to set an instance.
     * Can't be used twice.
     *
     * @param instance Instance of {@link EpicGuard} class.
     */
    public static void setInstance(@Nonnull EpicGuard instance) {
        if (epicGuard == null) {
            epicGuard = instance;
        } else {
            throw new UnsupportedOperationException("Instance already set.");
        }
    }

    private EpicGuardAPI() {}
}

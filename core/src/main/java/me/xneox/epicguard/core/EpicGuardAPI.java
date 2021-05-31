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

import org.apache.commons.lang3.Validate;
import me.xneox.epicguard.core.manager.GeoManager;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

/**
 * Class which can be safely used by other projects.
 * Methods won't be changed/removed after updates.
 */
public class EpicGuardAPI {
    public static final EpicGuardAPI INSTANCE = new EpicGuardAPI();
    private EpicGuard epicGuard;

    /**
     * The {@link GeoManager} class contains methods that you may
     * find useful if you want to check country/city of an address.
     *
     * @return An instance of {@link GeoManager}.
     */
    @Nonnull
    public GeoManager getGeoManager() {
        isAvailable();
        return this.epicGuard.geoManager();
    }

    /**
     * @return true if server is under attack, false if not.
     */
    public boolean isUnderAttack() {
        isAvailable();
        return this.epicGuard.attackManager().isAttack();
    }

    /**
     * @return An immutable Collection which contains whitelisted addresses.
     */
    @Nonnull
    public Collection<String> getWhitelistedAddresses() {
        isAvailable();
        return Collections.unmodifiableCollection(this.epicGuard.storageManager().provider().addressWhitelist());
    }

    /**
     * @return An immutable Collection which contains blacklisted addresses.
     */
    @Nonnull
    public Collection<String> getBlacklistedAddresses() {
        isAvailable();
        return Collections.unmodifiableCollection(this.epicGuard.storageManager().provider().addressWhitelist());
    }

    /**
     * @return An immutable Collection which contains whitelisted nicknames.
     */
    @Nonnull
    public Collection<String> getWhitelistedNicknames() {
        isAvailable();
        return Collections.unmodifiableCollection(this.epicGuard.storageManager().provider().nameWhitelist());
    }

    /**
     * @return An immutable Collection which contains blacklisted nicknames.
     */
    @Nonnull
    public Collection<String> getBlacklistedNicknames() {
        isAvailable();
        return Collections.unmodifiableCollection(this.epicGuard.storageManager().provider().nameBlacklist());
    }

    /**
     * @return Current connections per second.
     */
    public int getConnectionsPerSecond() {
        isAvailable();
        return this.epicGuard.attackManager().connectionCounter();
    }

    /**
     * Adds the provided address to blacklist, if it is not already there.
     *
     * @param address Address to be blacklisted.
     */
    public void blacklistAddress(@Nonnull String address) {
        isAvailable();
        Validate.notNull(address, "Address cannot be null!");

        this.epicGuard.storageManager().blacklistPut(address);
    }

    /**
     * Adds the provided address to whitelist.
     * Also, removes the address from blacklist.
     *
     * @param address Address to be blacklisted.
     */
    public void whitelistAddress(@Nonnull String address) {
        isAvailable();
        Validate.notNull(address, "Address cannot be null!");

        this.epicGuard.storageManager().whitelistPut(address);
        this.epicGuard.storageManager().provider().addressBlacklist().remove(address);
    }

    /**
     * This method is used during initialization to set an instance.
     * Can't be used twice.
     *
     * @param instance Instance of {@link EpicGuard} class.
     */
    protected void setInstance(@Nonnull EpicGuard instance) {
        if (this.epicGuard == null) {
            this.epicGuard = instance;
        } else {
            throw new UnsupportedOperationException("Instance already set.");
        }
    }

    /**
     * Checks if the EpicGuard has been initialized already.
     */
    public void isAvailable() {
        Validate.notNull(this.epicGuard, "Can't acces EpicGuardAPI because the plugin is not initialized. Have you set is as dependency?.");
    }
}

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

import me.xneox.epicguard.core.manager.GeoManager;
import me.xneox.epicguard.core.storage.AddressMeta;
import me.xneox.epicguard.core.storage.StorageManager;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

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
    @NotNull
    public GeoManager getGeoManager() {
        checkAvailability();
        return this.epicGuard.geoManager();
    }

    /**
     * @return The storage manager, which is used for various things regarding address data.
     */
    @NotNull
    public StorageManager storageManager() {
        checkAvailability();
        return this.epicGuard.storageManager();
    }

    /**
     * @return true if server is under attack, false if not.
     */
    public boolean isUnderAttack() {
        checkAvailability();
        return this.epicGuard.attackManager().isAttack();
    }

    /**
     * @return An immutable Collection which contains whitelisted addresses.
     */
    @NotNull
    public Collection<String> getWhitelistedAddresses() {
        checkAvailability();
        return this.epicGuard.storageManager().viewAddresses(AddressMeta::whitelisted);
    }

    /**
     * @return An immutable Collection which contains blacklisted addresses.
     */
    @NotNull
    public Collection<String> getBlacklistedAddresses() {
        checkAvailability();
        return this.epicGuard.storageManager().viewAddresses(AddressMeta::blacklisted);
    }

    /**
     * @return Current connections per second.
     */
    public int getConnectionsPerSecond() {
        checkAvailability();
        return this.epicGuard.attackManager().connectionCounter();
    }

    /**
     * This method is used during initialization to set an instance.
     * Can't be used twice.
     *
     * @param instance Instance of {@link EpicGuard} class.
     */
    protected void setInstance(@NotNull EpicGuard instance) {
        if (this.epicGuard == null) {
            this.epicGuard = instance;
        } else {
            throw new UnsupportedOperationException("Instance already set.");
        }
    }

    /**
     * Checks if the EpicGuard has been initialized already.
     */
    public void checkAvailability() {
        Validate.notNull(this.epicGuard, "Can't acces EpicGuardAPI because the plugin is not initialized. Have you set is as dependency?.");
    }
}

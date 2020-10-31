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
import org.diorite.libs.org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

/**
 * Class which can be safely used by other projects.
 * Methods won't be changed/removed after updates.
 */
public class EpicGuardAPI {
    private static EpicGuard epicGuard;

    /**
     * The {@link GeoManager} class contains methods that you may
     * find useful if you want to check country/city of an address.
     *
     * @return An instance of {@link GeoManager}.
     */
    public static GeoManager getGeoManager() {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        return epicGuard.getGeoManager();
    }

    /**
     * @return true if server is under attack, false if not.
     */
    public static boolean isUnderAttack() {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        return epicGuard.getAttackManager().isAttack();
    }

    /**
     * @return An immutable Collection which contains whitelisted addresses.
     */
    public static Collection<String> getWhitelist() {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        return Collections.unmodifiableCollection(epicGuard.getStorageManager().getWhitelist());
    }

    /**
     * @return An immutable Collection which contains blacklisted addresses.
     */
    public static Collection<String> getBlacklist() {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        return Collections.unmodifiableCollection(epicGuard.getStorageManager().getWhitelist());
    }

    /**
     * @return Current connections per second.
     */
    public static int getCPS() {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        return epicGuard.getAttackManager().getCPS();
    }

    /**
     * @return Amount of connections made to the server from the time of startup.
     */
    public static int getTotalConnections() {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        return epicGuard.getAttackManager().getTotalBots();
    }

    /**
     * Adds the provided address to blacklist, if it is not already there.
     *
     * @param address Address to be blacklisted.
     */
    public static void blacklist(@Nonnull String address) {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        Validate.notNull(address, "Address cannot be null!");

        epicGuard.getStorageManager().blacklist(address);
    }

    /**
     * Adds the provided address to whitelist.
     * Also, removes the address from blacklist.
     *
     * @param address Address to be blacklisted.
     */
    public static void whitelist(@Nonnull String address) {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        Validate.notNull(address, "Address cannot be null!");

        epicGuard.getStorageManager().whitelist(address);
        epicGuard.getStorageManager().getBlacklist().remove(address);
    }

    /**
     * @return Instance of {@link EpicGuard} class, which holds all managers.
     * It is recommended to use methods in this class instead.
     */
    public static EpicGuard getInstance() {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        return epicGuard;
    }

    /**
     * This method is used during initalization to set an instance.
     * Can't be used twice.
     *
     * @param instance Instance of {@link EpicGuard} class.
     */
    public static void setInstance(EpicGuard instance) {
        if (epicGuard == null) {
            epicGuard = instance;
        } else {
            throw new IllegalStateException("Instance already set.");
        }
    }
}

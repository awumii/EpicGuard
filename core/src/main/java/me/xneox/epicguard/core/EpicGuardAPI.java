package me.xneox.epicguard.core;

import me.xneox.epicguard.core.manager.GeoManager;
import org.diorite.libs.org.apache.commons.lang3.Validate;

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
    public static void blacklist(String address) {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        epicGuard.getStorageManager().blacklist(address);
    }

    /**
     * Adds the provided address to whitelist.
     * Also, removes the address from blacklist.
     *
     * @param address Address to be blacklisted.
     */
    public static void whitelist(String address) {
        Validate.notNull(epicGuard, "Can't acces EpicGuardAPI because it has been not initialized yet.");
        epicGuard.getStorageManager().whitelist(address);
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

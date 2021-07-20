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

package me.xneox.epicguard.core.handler;

import me.xneox.epicguard.core.EpicGuard;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;


/**
 * Handler for the ServerListPing listeners.
 * Used for the ServerListCheck to verify if the user has pinged the server (added it to their list).
 */
public class PingHandler {
    private final EpicGuard epicGuard;

    public PingHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    /**
     * Handling the user who just pinged the server.
     *
     * @param address Address of the pinger.
     */
    public void handle(@NotNull String address) {
        Validate.notNull(address, "Address cannot be null!");
        this.epicGuard.storageManager().pingCache().add(address);
    }
}

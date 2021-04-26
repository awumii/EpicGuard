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

import java.util.UUID;

/**
 * If the user has sent the Settings packet, it will be marked on their {@link me.xneox.epicguard.core.user.User}
 * object.
 * This packet is sent after joining the server by the vanilla client.
 */
public class SettingsHandler {
    private final EpicGuard epicGuard;

    public SettingsHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public void handle(UUID uuid) {
        this.epicGuard.getUserManager().getOrCreate(uuid).setSettingsChanged(true);
    }
}

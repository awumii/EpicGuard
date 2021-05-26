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
import me.xneox.epicguard.core.check.WhitelistMode;
import me.xneox.epicguard.core.user.User;
import org.diorite.libs.org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * Handler for the PlayerJoin or PostLogin listeners
 * Used for the auto-whitelist feature, and for SettingsCheck.
 */
public class JoinHandler {
    private final EpicGuard epicGuard;

    public JoinHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    /**
     * Handling the player who just have joined to the server.
     *
     * @param uuid UUID of the online player.
     * @param address Address of the online player.
     * @param nickname Nickname of the online player.
     */
    public void handle(@Nonnull UUID uuid, @Nonnull String address, @Nonnull String nickname) {
        Validate.notNull(uuid, "UUID cannot be null!");
        Validate.notNull(address, "Address cannot be null!");

        User user = this.epicGuard.getUserManager().getOrCreate(uuid);

        // Schedule a delayed task to whitelist the player.
        WhitelistMode mode = WhitelistMode.valueOf(this.epicGuard.getConfig().autoWhitelist);
        if (mode != WhitelistMode.DISABLED) {
            this.epicGuard.getPlatform().runTaskLater(() -> {
                if (user != null) {
                    if (mode == WhitelistMode.BOTH || mode == WhitelistMode.ADDRESS) {
                        this.epicGuard.getStorageManager().whitelistPut(address);
                    }

                    if (mode == WhitelistMode.BOTH || mode == WhitelistMode.NICKNAME) {
                        this.epicGuard.getStorageManager().whitelistPut(nickname);
                    }
                }
            }, this.epicGuard.getConfig().autoWhitelistTime);
        }

        // Schedule a delayed task to check if the player has sent the Settings packet.
        if (this.epicGuard.getConfig().settingsCheck) {
            this.epicGuard.getPlatform().runTaskLater(() -> {
                if (user != null && !user.hasChangedSettings()) {
                    this.epicGuard.getPlatform().disconnectUser(user, this.epicGuard.getMessages().kickMessageSettings);
                }
            }, this.epicGuard.getConfig().settingsCheckDelay);
        }
    }
}

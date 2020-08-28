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

package me.ishift.epicguard.core.handler;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.user.User;

import java.util.UUID;

public class JoinHandler {
    private final EpicGuard epicGuard;

    public JoinHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public void handle(UUID uuid, String address) {
        this.epicGuard.getUserManager().addUser(uuid);

        this.epicGuard.getPlugin().runTaskLater(() -> {
            User user = epicGuard.getUserManager().getUser(uuid);
            if (user != null) {
                epicGuard.getStorageManager().whitelist(address);
            }
        }, this.epicGuard.getConfig().autoWhitelistTime);
    }
}

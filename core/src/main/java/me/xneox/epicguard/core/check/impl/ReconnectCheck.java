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

package me.xneox.epicguard.core.check.impl;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.CheckMode;
import me.xneox.epicguard.core.user.PendingUser;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * This check will force player to reconnect if he is connecting for the first time.
 */
public class ReconnectCheck extends Check {
    private final Collection<PendingUser> pendingUserCache = new HashSet<>();

    public ReconnectCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@Nonnull PendingUser user) {
        CheckMode mode = CheckMode.valueOf(this.epicGuard.config().reconnectCheckMode());
        return this.assertCheck(mode, needsReconnect(user));
    }

    private boolean needsReconnect(PendingUser pendingUser) {
        if (!this.pendingUserCache.contains(pendingUser)) {
            this.pendingUserCache.add(pendingUser);
            return true;
        }
        return false;
    }

    @Override
    public @Nonnull List<String> kickMessage() {
        return this.epicGuard.messages().disconnect().reconnect();
    }
}

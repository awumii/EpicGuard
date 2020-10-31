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
import me.xneox.epicguard.core.user.BotUser;

import javax.annotation.Nonnull;
import java.util.List;

public class ServerListCheck extends Check {
    public ServerListCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@Nonnull BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().serverListCheck);

        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return !this.getStorage().getPingCache().contains(user.getAddress());
            case ATTACK:
                if (this.isAttack()) {
                    return !this.getStorage().getPingCache().contains(user.getAddress());
                }
        }
        return false;
    }

    @Override
    public @Nonnull List<String> getKickMessage() {
        return this.getMessages().kickMessageServerList;
    }

    @Override
    public boolean shouldBlacklist() {
        return false;
    }
}

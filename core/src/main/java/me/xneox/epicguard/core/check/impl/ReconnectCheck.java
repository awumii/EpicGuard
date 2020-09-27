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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ReconnectCheck extends Check {
    private final Collection<String> addresses = new HashSet<>();

    public ReconnectCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().reconnectCheck);

        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return this.reconnectCheck(user.getAddress());
            case ATTACK:
                if (this.isAttack()) {
                    return this.reconnectCheck(user.getAddress());
                }
        }
        return false;
    }

    private boolean reconnectCheck(String address) {
        if (!this.addresses.contains(address)) {
            this.addresses.add(address);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getKickMessage() {
        return this.getMessages().kickMessageReconnect;
    }

    @Override
    public boolean shouldBlacklist() {
        return false;
    }
}

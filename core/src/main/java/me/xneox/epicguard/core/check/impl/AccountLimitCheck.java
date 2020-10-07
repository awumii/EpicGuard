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
import me.xneox.epicguard.core.user.BotUser;

import java.util.List;

public class AccountLimitCheck extends Check {
    public AccountLimitCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(BotUser user) {
        return this.getEpicGuard().getStorageManager().getAccounts(user).size() > this.getConfig().accountLimit;
    }

    @Override
    public List<String> getKickMessage() {
        return this.getMessages().kickMessageAccountLimit;
    }

    @Override
    public boolean shouldBlacklist() {
        return true;
    }
}

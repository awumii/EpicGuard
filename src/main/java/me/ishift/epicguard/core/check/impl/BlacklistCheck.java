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

package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.user.BotUser;

import java.util.List;

public class BlacklistCheck extends Check {
    public BlacklistCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(BotUser user) {
        return this.getStorage().isBlacklisted(user.getAddress());
    }

    @Override
    public List<String> reason() {
        return this.getMessages().kickMessageBlacklist;
    }

    @Override
    public boolean blacklist() {
        return false;
    }
}

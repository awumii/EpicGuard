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
import java.util.List;

/**
 * This check will limit how many accounts can be created on one address.
 * TODO: Fix this buggy mess. Sometimes working perfectly and sometimes not at all.
 */
public class AccountLimitCheck extends Check {
    public AccountLimitCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@Nonnull PendingUser user) {
        CheckMode mode = CheckMode.valueOf(this.epicGuard.config().accountLimitCheck().checkMode());
        List<String> accounts = this.epicGuard.storageManager().accounts(user);

        return this.assertCheck(mode, !accounts.contains(user.nickname()) && accounts.size() >= this.epicGuard.config().accountLimitCheck().accountLimit());
    }

    @Override
    public @Nonnull List<String> kickMessage() {
        return this.epicGuard.messages().disconnect().accountLimit();
    }
}

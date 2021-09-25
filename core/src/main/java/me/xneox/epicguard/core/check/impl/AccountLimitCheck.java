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

import java.util.List;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.user.ConnectingUser;
import org.jetbrains.annotations.NotNull;

/**
 * This check limits how many accounts can be created on one address.
 */
public class AccountLimitCheck extends Check {
  public AccountLimitCheck(EpicGuard epicGuard) {
    super(epicGuard, epicGuard.messages().disconnect().accountLimit(), epicGuard.config().accountLimitCheck().priority());
  }

  @Override
  public boolean isDetected(@NotNull ConnectingUser user) {
    List<String> accounts = this.epicGuard.storageManager().addressMeta(user.address()).nicknames();

    return this.evaluate(this.epicGuard.config().accountLimitCheck().checkMode(),
        !accounts.contains(user.nickname()) && accounts.size() >= this.epicGuard.config().accountLimitCheck().accountLimit());
  }
}

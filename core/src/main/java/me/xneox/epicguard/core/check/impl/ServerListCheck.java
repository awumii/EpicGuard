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
import me.xneox.epicguard.core.user.ConnectingUser;
import org.jetbrains.annotations.NotNull;

/** This will check if the user has pinged the server (added it to their server list). */
public class ServerListCheck extends Check {
  public ServerListCheck(EpicGuard epicGuard) {
    super(epicGuard, epicGuard.messages().disconnect().serverListPing(), epicGuard.config().serverListCheck().priority());
  }

  @Override
  public boolean isDetected(@NotNull ConnectingUser user) {
    return this.evaluate(this.epicGuard.config().serverListCheck().checkMode(),
        !this.epicGuard.storageManager().pingCache().contains(user.address()));
  }
}

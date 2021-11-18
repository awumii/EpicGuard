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

package me.xneox.epicguard.core.task;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.user.OnlineUser;
import me.xneox.epicguard.core.util.TextUtils;

/**
 * This task displays current attack status on the actionbar of users who enabled them.
 */
public record MonitorTask(EpicGuard epicGuard) implements Runnable {

  @Override
  public void run() {
    var config = this.epicGuard.messages();
    var textComponent = TextUtils.component(config.actionbarMonitor()
        .replace("%cps%", String.valueOf(this.epicGuard.attackManager().connectionCounter()))
        .replace("%status%", this.epicGuard.attackManager().isUnderAttack() ? config.actionbarAttack() : config.actionbarNoAttack()));

    for (OnlineUser user : this.epicGuard.userManager().users()) {
      if (user.notifications()) {
        var audience = this.epicGuard.platform().audience(user.uuid());
        if (audience != null) {
          audience.sendActionBar(textComponent);
        }
      }
    }

    // Because this task is repeating every second, we can reset the connections/s counter.
    this.epicGuard.attackManager().resetConnectionCounter();
  }
}

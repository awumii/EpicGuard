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
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.User;

public class MonitorTask implements Runnable {
    private final EpicGuard epicGuard;

    public MonitorTask(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public void run() {
        MessagesConfiguration config = this.epicGuard.getMessages();
        String monitor = config.monitor
                .replace("%cps%", String.valueOf(this.epicGuard.getAttackManager().getCPS()))
                .replace("%status%", this.epicGuard.getAttackManager().isAttack() ? config.attack : config.noAttack);

        for (User user : this.epicGuard.getUserManager().getUsers()) {
            if (user.hasNotifications()) {
                this.epicGuard.getPlugin().sendActionBar(monitor, user);
            }
        }
        this.epicGuard.getAttackManager().resetCPS();
    }
}

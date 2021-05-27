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

/**
 * This task displays current attack status on the actionbar of users who enabled them.
 */
public class MonitorTask implements Runnable {
    private final EpicGuard epicGuard;

    public MonitorTask(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public void run() {
        MessagesConfiguration config = this.epicGuard.getMessages();
        String monitor = config.actionbarMonitor
                .replace("%cps%", String.valueOf(this.epicGuard.getAttackManager().getConnectionCounter()))
                .replace("%status%", this.epicGuard.getAttackManager().isAttack() ? config.actionbarAttack : config.actionbarNoAttack);

        this.epicGuard.getUserManager().getUsers().stream()
                .filter(User::hasNotifications)
                .forEach(user -> this.epicGuard.getPlatform().sendActionBar(monitor, user));

        // Because this task is repeating every second, we can reset the connections/s counter.
        this.epicGuard.getAttackManager().resetConnectionCounter();
    }
}

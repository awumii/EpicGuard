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

/**
 * This task disables attack-mode if the connections per seconds are lower than configured.
 */
public class AttackResetTask implements Runnable {
    private final EpicGuard epicGuard;

    public AttackResetTask(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public void run() {
        if (this.epicGuard.attackManager().connectionCounter() < this.epicGuard.config().attackConnectionThreshold()) {
            this.epicGuard.attackManager().attack(false);
        }
    }
}

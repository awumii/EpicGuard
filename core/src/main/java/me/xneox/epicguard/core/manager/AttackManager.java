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

package me.xneox.epicguard.core.manager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class holds variables related to the current server attack status.
 */
public class AttackManager {
    private final AtomicInteger connectionCounter = new AtomicInteger();
    private boolean attack;

    public boolean isAttack() {
        return this.attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public int getConnectionCounter() {
        return this.connectionCounter.get();
    }

    public void resetConnectionCounter() {
        this.connectionCounter.set(0);
    }

    public int incrementConnectionCounter() {
        return this.connectionCounter.incrementAndGet();
    }
}

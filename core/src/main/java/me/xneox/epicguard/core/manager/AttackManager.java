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

  /**
   * Attack-mode is a special temporary state, that indicates that the server is under attack. It
   * triggers when connection counter is above the threshold.
   *
   * @return State of the attack-mode
   */
  public boolean isUnderAttack() {
    return this.attack;
  }

  public void attack(boolean attack) {
    this.attack = attack;
  }

  public int connectionCounter() {
    return this.connectionCounter.get();
  }

  public void resetConnectionCounter() {
    this.connectionCounter.set(0);
  }

  /**
   * Increments the connection per second counter, and returns it's current value
   *
   * @return Current connections per second.
   */
  public int incrementConnectionCounter() {
    return this.connectionCounter.incrementAndGet();
  }
}

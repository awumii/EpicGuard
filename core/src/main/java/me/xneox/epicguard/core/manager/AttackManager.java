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

/**
 * This class holds variables related to the current server attack status.
 */
public class AttackManager {
    private boolean attack;
    private int connectionPerSecond;
    private int totalBots;

    public boolean isAttack() {
        return this.attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public int getCPS() {
        return this.connectionPerSecond;
    }

    public void resetCPS() {
        this.connectionPerSecond = 0;
    }

    public void incrementCPS() {
        this.connectionPerSecond++;
        this.totalBots++;
    }

    public int getTotalBots() {
        return this.totalBots;
    }
}

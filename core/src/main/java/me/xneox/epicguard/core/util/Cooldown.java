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

package me.xneox.epicguard.core.util;

import java.util.concurrent.TimeUnit;

public class Cooldown {
    private final String id;
    private final long duration;
    private final long time;

    public Cooldown(String id, long duration) {
        this.id = id;
        this.duration = TimeUnit.SECONDS.toMillis(duration);
        this.time = System.currentTimeMillis();
    }

    public String getId() {
        return this.id;
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() > this.time + this.duration;
    }
}

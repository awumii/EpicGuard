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

package me.ishift.epicguard.bukkit.util.server;

public class Memory {
    private static long format(long value) {
        return value / 1048576;
    }

    public static long getUsage() {
        final Runtime runtime = Runtime.getRuntime();
        return format(runtime.maxMemory() - runtime.freeMemory());
    }

    public static long getTotal() {
        final Runtime runtime = Runtime.getRuntime();
        return format(runtime.maxMemory());
    }

    public static long getFree() {
        final Runtime runtime = Runtime.getRuntime();
        return format(runtime.freeMemory());
    }
}

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

package me.ishift.epicguard.common.util;

public class Memory {
    /**
     * Returned value is formatted (MB).
     *
     * @return Current runtime memory usage.
     */
    public static long getUsage() {
        final Runtime runtime = Runtime.getRuntime();
        return format(runtime.maxMemory() - runtime.freeMemory());
    }

    /**
     * Returned value is formatted (MB).
     *
     * @return Current runtime total memory.
     */
    public static long getTotal() {
        final Runtime runtime = Runtime.getRuntime();
        return format(runtime.maxMemory());
    }

    /**
     * Returned value is formatted (MB).
     *
     * @return Current runtime free memory.
     */
    public static long getFree() {
        final Runtime runtime = Runtime.getRuntime();
        return format(runtime.freeMemory());
    }

    /**
     * Calculating value / 1048576.
     *
     * @param value Value in long to be formatted.
     * @return Formatted value.
     */
    public static long format(long value) {
        return value / 1048576;
    }
}

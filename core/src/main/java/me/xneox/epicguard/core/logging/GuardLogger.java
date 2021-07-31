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

package me.xneox.epicguard.core.logging;

/**
 * Used as a wrapper around a logger provided by the platform EpicGuard is running on.
 * Available implementations:
 *  {@link me.xneox.epicguard.core.logging.impl.JavaLogger} for {@link java.util.logging.Logger}
 *  {@link me.xneox.epicguard.core.logging.impl.SLF4JLogger} for {@link org.slf4j.Logger}
 */
public interface GuardLogger {
    void info(String message);

    void warning(String message);

    void error(String message);
}

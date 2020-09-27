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

public final class ChatUtils {
    public static String colored(String message) {
        // import unavailable...
        //return ChatColor.translateAlternateColorCodes('&', message);
        return coloredLegacy(message);
    }

    public static String coloredLegacy(String message) {
        return message.replace("&", "§"); // Method compatible with Velocity.
    }

    private ChatUtils() {
    }
}

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

import java.util.List;

public final class ChatUtils {
    public static String colored(String message) {
        return message.replace("&", "§"); //TODO: Use Adventure/Bungee for this...
    }

    // This method creates one, multiline and colored string from every string in the provided list.
    public static String buildString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        list.forEach(s -> builder.append(ChatUtils.colored(s)).append("\n"));
        return builder.toString();
    }

    private ChatUtils() {}
}

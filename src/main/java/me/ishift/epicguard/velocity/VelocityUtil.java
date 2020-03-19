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

package me.ishift.epicguard.velocity;

import net.kyori.text.TextComponent;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;

public class VelocityUtil {
    /**
     * @param message Message to be formatted.
     * @return Formatted message.
     */
    public static String fixColor(String message) {
        return message.replace("&", "§");
    }

    /**
     * @param message Raw message.
     * @return TextComponent from message.
     */
    public static @NonNull TextComponent getComponent(String message) {
        return LegacyComponentSerializer.legacy().deserialize(VelocityUtil.fixColor(message));
    }
}

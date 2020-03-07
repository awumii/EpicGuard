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

package me.ishift.epicguard.api.bukkit;

import me.ishift.epicguard.api.ChatUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class TitleAPI {
    /**
     * Sends title and/or subtitle message with specified times.
     * Message is formatted by ChatUtil with color '&' replacement.
     * Using Reflection to work on every Bukkit version.
     *
     * @param player Target player who should see the title message.
     * @param title Title message.
     * @param subtitle Subtitle message.
     * @param fadeInTime Fade in time in ticks.
     * @param showTime How long title should be displayed.
     * @param fadeOutTime Fade out time in ticks
     */
    public static void send(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
        try {
            final Object chatTitle = Reflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + ChatUtil.fix(title) + "\"}");
            final Constructor<?> titleConstructor = Reflection.getNMSClass("PacketPlayOutTitle").getConstructor(
                    Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], Reflection.getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            final Object packet = titleConstructor.newInstance(
                    Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
                    fadeInTime, showTime, fadeOutTime);

            final Object chatsTitle = Reflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + ChatUtil.fix(subtitle) + "\"}");
            final Constructor<?> timingTitleConstructor = Reflection.getNMSClass("PacketPlayOutTitle").getConstructor(
                    Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], Reflection.getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            final Object timingPacket = timingTitleConstructor.newInstance(
                    Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
                    fadeInTime, showTime, fadeOutTime);

            Reflection.sendPacket(player, packet);
            Reflection.sendPacket(player, timingPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

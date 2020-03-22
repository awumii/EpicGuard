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


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reflection {
    /**
     * Example output for 1.8 "v1_8_R3".
     *
     * @return Current NMS version.
     */
    public static String getVersion() {
        final String version = Bukkit.getServer().getClass().getPackage().getName();
        return version.substring(version.lastIndexOf(".") + 1);
    }

    /**
     * @return Is the server version older than 1.13 (material version change).
     */
    public static boolean isOldVersion() {
        return !getVersion().startsWith("v1_13") || !getVersion().startsWith("v1_14") || !getVersion().startsWith("v1_15");
    }

    /**
     * Sends specific packet to the player.
     *
     * @param player Target player.
     * @param packet Packet object.
     */
    public static void sendPacket(final Player player, final Object packet) {
        try {
            final Object handle = player.getClass().getMethod("getHandle").invoke(player);
            final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param name Name of the class.
     * @return NMS class.
     */
    public static Class<?> getNMSClass(final String name) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

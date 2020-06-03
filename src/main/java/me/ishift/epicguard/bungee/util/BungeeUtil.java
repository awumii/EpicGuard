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

package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.common.util.MessageHelper;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeUtil {
    /**
     * @param player  Target player.
     * @param message Message to be sent.
     */
    public static void sendActionBar(ProxiedPlayer player, String message) {
        player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageHelper.color(message)));
    }

    /**
     * @param sender  Target command sender.
     * @param message Message to be sent.
     */
    public static void sendMessage(CommandSender sender, String message) {
        final TextComponent component = new TextComponent(MessageHelper.color(message));
        sender.sendMessage(component);
    }
}

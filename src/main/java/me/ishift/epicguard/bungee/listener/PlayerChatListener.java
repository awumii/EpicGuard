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

package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.common.data.config.BungeeSettings;
import me.ishift.epicguard.common.data.config.Messages;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onChat(ChatEvent event) {
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        final String[] message = event.getMessage().split(" ");
        if (BungeeSettings.blockedCommandsEnable && BungeeSettings.blockedCommands.contains(message[0])) {
            if (BungeeSettings.blockedCommandsBypass && player.hasPermission("epicguard.bypass.blocked-commands")) {
                return;
            }

            event.setCancelled(true);
            BungeeUtil.sendMessage(player, Messages.prefix + Messages.blockedCommand);
        }
    }
}

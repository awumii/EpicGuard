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

package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.SpigotSettings;
import me.ishift.epicguard.common.Configuration;
import me.ishift.epicguard.common.Messages;
import me.ishift.epicguard.common.util.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String cmd = event.getMessage();
        final String[] args = cmd.split(" ");

        // OP Protection module.
        if (SpigotSettings.opProtectionEnable && !SpigotSettings.opProtectionList.contains(player.getName()) && player.isOp()) {
            event.setCancelled(true);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), SpigotSettings.opProtectionCommand.replace("{PLAYER}", player.getName()));
            return;
        }

        // Allowed Commands module.
        if (SpigotSettings.allowedCommandsEnable && !SpigotSettings.allowedCommands.contains(args[0])) {
            if (SpigotSettings.allowedCommandsBypass && player.hasPermission("epicguard.bypass.allowed-commands")) {
                return;
            }
            event.setCancelled(true);
            player.sendMessage(MessageHelper.color(Messages.notAllowedCommand));
            return;
        }

        // Blocked Commands module.
        if (SpigotSettings.blockedCommandsEnable && SpigotSettings.blockedCommands.contains(args[0])) {
            event.setCancelled(true);
            player.sendMessage(MessageHelper.color(Messages.prefix + Messages.blockedCommand));
        }
    }
}

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

package me.xneox.epicguard.bukkit.listener;

import me.xneox.epicguard.bukkit.PlatformBukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {
    private final PlatformBukkit plugin;

    public CommandListener(PlatformBukkit plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        String command = event.getMessage();
        String[] args = command.split(" ");

        if (this.plugin.getModuleManager().check(player, args)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent event) {
        String cmd = event.getCommand();
        if (this.plugin.getModuleManager().disableOperatorMechanicsConsole) {
            if (cmd.startsWith("op") || cmd.startsWith("deop") || cmd.startsWith("minecraft:op") || cmd.startsWith("minecraft:deop")) {
                event.setCancelled(true);
                event.getSender().sendMessage("Operator mechanics has been disabled on this server.");
            }
        }
    }
}

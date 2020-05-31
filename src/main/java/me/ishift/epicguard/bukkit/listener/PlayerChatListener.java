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

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.common.types.Reason;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        // MCSpam bots sends these messages to the chat after connect.
        final String message = event.getMessage();
        if (message.equals("'Attack") || message.equals("'/login") || message.equals("'/register")) {
            event.setCancelled(true);
            // Bukkit can't kick player in the async thread...
            Bukkit.getScheduler().runTask(EpicGuardBukkit.getInstance(), () -> event.getPlayer().kickPlayer(Reason.BOT_BEHAVIOUR.getMessage()));
        }
    }
}

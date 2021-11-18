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

package me.xneox.epicguard.paper.listener;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.DisconnectHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends DisconnectHandler implements Listener {
  public PlayerQuitListener(EpicGuard epicGuard) {
    super(epicGuard);
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    this.onDisconnect(event.getPlayer().getUniqueId());
  }
}

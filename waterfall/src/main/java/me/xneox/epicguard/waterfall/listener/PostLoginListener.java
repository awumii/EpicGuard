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

package me.xneox.epicguard.waterfall.listener;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.PostLoginHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginListener extends PostLoginHandler implements Listener {
  public PostLoginListener(EpicGuard epicGuard) {
    super(epicGuard);
  }

  @EventHandler
  public void onPostLogin(PostLoginEvent event) {
    ProxiedPlayer player = event.getPlayer();

    //noinspection deprecation
    this.onPostLogin(player.getUniqueId(), player.getAddress().getAddress().getHostAddress());
  }
}

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


import me.ishift.epicguard.common.detection.Detection;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;


public class PreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        final PendingConnection connection = event.getConnection();
        final String address = connection.getAddress().getAddress().getHostAddress();
        final String name = connection.getName();

        final Detection detection = new Detection(address, name);
        if (detection.isDetected()) {
            event.setCancelReason(new TextComponent(detection.getReason().getReason()));
            event.setCancelled(true);
        }
    }
}

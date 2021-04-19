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

package me.xneox.epicguard.bungee.listener;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.SettingsHandler;
import net.md_5.bungee.api.event.SettingsChangedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerSettingsListener extends SettingsHandler implements Listener {
    public PlayerSettingsListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @EventHandler
    public void onSettingsChanged(SettingsChangedEvent event) {
        this.handle(event.getPlayer().getUniqueId());
    }
}

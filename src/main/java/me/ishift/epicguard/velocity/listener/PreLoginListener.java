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

package me.ishift.epicguard.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.proxy.InboundConnection;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.antibot.Detection;
import me.ishift.epicguard.common.types.Reason;
import me.ishift.epicguard.velocity.util.Utils;

public class PreLoginListener {
    private final AttackManager manager;

    public PreLoginListener(AttackManager manager) {
        this.manager = manager;
    }

    @Subscribe
    public void onPreLogin(PreLoginEvent event) {
        final InboundConnection connection = event.getConnection();
        final String address = connection.getRemoteAddress().getAddress().getHostAddress();
        final String name = event.getUsername();

        final Reason reason = this.manager.check(address, name);
        if (reason != null) {
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(Utils.getComponent(reason.getMessage())));
        }
    }
}

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
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.CheckResult;
import me.ishift.epicguard.core.handler.DetectionHandler;
import me.ishift.epicguard.velocity.util.VelocityUtils;
import net.kyori.text.Component;

public class PreLoginListener extends DetectionHandler {
    public PreLoginListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Subscribe
    public void onPreLogin(PreLoginEvent event) {
        String address = event.getConnection().getRemoteAddress().getAddress().getHostAddress();
        String nickname = event.getUsername();

        CheckResult result = this.handle(address, nickname);
        if (result.isDetected()) {
            Component reason = VelocityUtils.getTextComponent(result.getKickMessage());
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(reason));
        }
    }
}

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

package me.ishift.epicguard.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.proxy.InboundConnection;
import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.StorageManager;
import me.ishift.epicguard.common.detection.AttackSpeed;
import me.ishift.epicguard.common.detection.BotCheck;
import me.ishift.epicguard.common.detection.Detection;
import me.ishift.epicguard.common.types.CounterType;

public class PreLoginListener {
    @Subscribe
    public void onPreLogin(PreLoginEvent event) {
        final InboundConnection connection = event.getConnection();
        final String address = connection.getRemoteAddress().getAddress().getHostAddress();
        final String nickname = event.getUsername();
        AttackSpeed.increase(CounterType.CONNECT);

        if (AttackSpeed.getConnectPerSecond() > Config.connectSpeed || AttackSpeed.getPingPerSecond() > Config.pingSpeed) {
            AttackSpeed.setAttackMode(true);
        }

        if (StorageManager.isWhitelisted(address)) {
            return;
        }

        final Detection detection = BotCheck.getDetection(address, nickname);
        if (detection.isDetected()) {
            this.handleDetection(address, event, detection);
        }
    }

    private void handleDetection(String address, PreLoginEvent event, Detection detection) {
        final String reason = detection.getReason().getReason();

        event.setResult(PreLoginEvent.PreLoginComponentResult.denied(Utils.getComponent(reason)));
        if (detection.isBlacklist()) {
            StorageManager.blacklist(address);
        }
    }
}

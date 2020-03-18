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

import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.common.detection.AttackSpeed;
import me.ishift.epicguard.common.detection.BotCheck;
import me.ishift.epicguard.common.StorageManager;
import me.ishift.epicguard.common.types.CounterType;
import me.ishift.epicguard.common.types.Reason;
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

        AttackSpeed.increase(CounterType.CONNECT);

        if (StorageManager.isWhitelisted(address)) {
            return;
        }

        final Detection detection = BotCheck.getDetection(address, name);
        if (detection.isDetected()) {
            handleDetection(address, connection, detection.getReason(), detection.isBlacklist());
            EpicGuardAPI.getLogger().debug("Detected for: " + detection.getReason().name() + ", blacklist: " + detection.isBlacklist());
            return;
        }
        EpicGuardAPI.getLogger().debug("Player has been not detected by any check.");
    }

    public static void handleDetection(String address, PendingConnection connection, Reason reason, boolean blacklist) {
        connection.disconnect(new TextComponent(reason.getReason()));
        if (GuardBungee.log) {
            EpicGuardAPI.getLogger().info("Closing: " + address + "(" + connection.getName() + "), (" + reason + ")]");
        }

        if (blacklist) {
            StorageManager.blacklist(address);
        }
        AttackSpeed.setTotalBots(AttackSpeed.getTotalBots() + 1);
        AttackSpeed.setLastReason(reason);
        AttackSpeed.setLastBot(connection.getName());
    }
}

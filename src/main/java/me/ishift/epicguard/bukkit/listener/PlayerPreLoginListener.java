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

import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.common.*;
import me.ishift.epicguard.common.types.CounterType;
import me.ishift.epicguard.common.types.Reason;
import me.ishift.epicguard.common.Detection;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        final String address = event.getAddress().getHostAddress();
        final String name = event.getName();
        final String country = EpicGuardAPI.getGeoApi().getCountryCode(address);

        if (AttackSpeed.getConnectPerSecond() > Config.connectSpeed || AttackSpeed.getPingPerSecond() > Config.pingSpeed) {
            AttackSpeed.setAttackMode(true);
        }

        StorageManager.increaseCheckedConnections();
        EpicGuardAPI.getLogger().debug(" ");
        EpicGuardAPI.getLogger().debug("~-~-~-~-~-~-~-~-~-~-~-~-");
        EpicGuardAPI.getLogger().debug("Player: " + name);
        EpicGuardAPI.getLogger().debug("Address: " + address);
        EpicGuardAPI.getLogger().debug("Country: " + country);

        AttackSpeed.increase(CounterType.CONNECT);
        Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> AttackSpeed.decrease(CounterType.CONNECT), 20L);

        if (StorageManager.isWhitelisted(address)) {
            return;
        }

        final Detection detection = BotCheck.getDetection(address, name);
        if (detection.isDetected()) {
            handleDetection(address, event, detection.getReason(), detection.isBlacklist());
            EpicGuardAPI.getLogger().debug("Detected for: " + detection.getReason().name() + ", blacklist: " + detection.isBlacklist());
            return;
        }
        EpicGuardAPI.getLogger().debug("Player has been not detected by any check.");
    }

    public static void handleDetection(String address, AsyncPlayerPreLoginEvent event, Reason reason, boolean blacklist) {
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, reason.getReason());
        if (blacklist) StorageManager.blacklist(address);

        StorageManager.increaseBlockedBots();
        AttackSpeed.setTotalBots(AttackSpeed.getTotalBots() + 1);
        AttackSpeed.setLastReason(reason);
        AttackSpeed.setLastBot(event.getName());
    }
}

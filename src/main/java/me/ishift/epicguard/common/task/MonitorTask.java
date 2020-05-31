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

package me.ishift.epicguard.common.task;

import lombok.AllArgsConstructor;
import me.ishift.epicguard.bukkit.util.BukkitNotify;
import me.ishift.epicguard.bungee.util.BungeeNotify;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.types.Platform;

@AllArgsConstructor
public class MonitorTask implements Runnable {
    private final AttackManager manager;
    private final Platform platform;

    @Override
    public void run() {
        final String message = Messages.monitorActionAttack
                .replace("{CPS}", String.valueOf(this.manager.getConnectPerSecond()))
                .replace("{BLOCKED}", String.valueOf(this.manager.getTotalBots()))
                .replace("{STATUS}", this.manager.isAttackMode() ? "&a✔" : "&c✖");
        if (this.platform == Platform.BUKKIT) {
            BukkitNotify.notify(message);
        } else {
            BungeeNotify.notify(message);
        }
    }
}

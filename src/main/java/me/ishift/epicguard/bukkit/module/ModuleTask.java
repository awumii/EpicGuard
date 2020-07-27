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

package me.ishift.epicguard.bukkit.module;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ModuleTask implements Runnable {
    private final EpicGuardBukkit plugin;

    public ModuleTask(EpicGuardBukkit plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // This will check if player has operator, even before he executes any command.
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.plugin.getModuleManager().check(player, null, null);
        }
    }
}

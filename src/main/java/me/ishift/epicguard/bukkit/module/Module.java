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

import org.bukkit.entity.Player;

public interface Module {
    /**
     * @param player Player executing the command.
     * @param command Full command which is executed, as String.
     * @param args Full command which is executed, as Array.
     * @return if the event should be cancelled
     */
    boolean execute(Player player, String command, String[] args);
}

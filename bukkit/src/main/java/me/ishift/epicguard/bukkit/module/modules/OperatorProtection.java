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

package me.ishift.epicguard.bukkit.module.modules;

import me.ishift.epicguard.bukkit.module.Module;
import me.ishift.epicguard.bukkit.module.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OperatorProtection extends Module {
    public OperatorProtection(ModuleManager manager) {
        super(manager);
    }

    @Override
    public boolean execute(Player player, String[] args) {
        if (this.getManager().opProtectionEnable && !this.getManager().opProtectionList.contains(player.getName()) && player.isOp()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.getManager().opProtectionCommand.replace("{PLAYER}", player.getName()));
            return true;
        }
        return false;
    }
}

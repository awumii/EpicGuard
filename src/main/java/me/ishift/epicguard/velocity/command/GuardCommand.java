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

package me.ishift.epicguard.velocity.command;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.velocity.util.Utils;
import org.checkerframework.checker.nullness.qual.NonNull;

public class GuardCommand implements Command {
    private final AttackManager manager;

    public GuardCommand(AttackManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(@NonNull CommandSource source, String[] args) {
        Utils.send(source, "&8&m---------------------------------------");
        Utils.send(source, "&e&lEpicGuard &e3.11.2-BETA &6(by iShift, ruzekh)");
        Utils.send(source, "");
        Utils.send(source, "&c&l★ Attack Statistics.");
        Utils.send(source, "");
        Utils.send(source, " &8» &7Connections: &6" + this.manager.getConnectPerSecond() + "/s");
        Utils.send(source, " &8» &7Blocked bots: &6" + this.manager.getTotalBots() + "/s");
        Utils.send(source, " &8» &7Blacklist size: &6" + this.manager.getStorageManager().getStorage().getBlacklist().size() + " IPs");
        Utils.send(source, " &8» &7Whitelist size: &6" + this.manager.getStorageManager().getStorage().getWhitelist().size() + " IPs");
        Utils.send(source, "&8&m---------------------------------------");
    }
}

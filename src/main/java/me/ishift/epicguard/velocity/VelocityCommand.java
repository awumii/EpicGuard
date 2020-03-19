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

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import me.ishift.epicguard.common.StorageManager;
import me.ishift.epicguard.common.detection.AttackSpeed;
import org.checkerframework.checker.nullness.qual.NonNull;

public class VelocityCommand implements Command {
    @Override
    public void execute(@NonNull CommandSource source, String[] args) {
        VelocityUtil.send(source, "&8*-------------------------------------------------------------*");
        VelocityUtil.send(source, "&e&lEpicGuard");
        VelocityUtil.send(source, "");
        VelocityUtil.send(source, "&eVersion 3.11.2-BETA &6(by iShift, ruzekh)");
        VelocityUtil.send(source, "");
        VelocityUtil.send(source, "&eAtack Statistics.");
        VelocityUtil.send(source, "");
        VelocityUtil.send(source, "&eCurrent connections: &6" + AttackSpeed.getConnectPerSecond() + "/s");
        VelocityUtil.send(source, "&eBlacklist size: &6" + StorageManager.getBlacklist().size() + " IPs");
        VelocityUtil.send(source, "&eWhitelist size: &6" + StorageManager.getWhitelist().size() + " IPs");
        VelocityUtil.send(source, "&8&m*-------------------------------------------------------------*");
    }
}

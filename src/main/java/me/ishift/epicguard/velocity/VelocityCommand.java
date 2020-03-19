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
        VelocityUtil.send(source, "&3&lEpicGuard &3v3.11.2-BETA (by iShift, ruzekh)");
        VelocityUtil.send(source, "&7Collected server statistics.");
        VelocityUtil.send(source, "&fCurrent connections: &a" + AttackSpeed.getConnectPerSecond() + "/s");
        VelocityUtil.send(source, "&fBlacklist size: &a" + StorageManager.getBlacklist().size() + " IPs");
        VelocityUtil.send(source, "&fWhitelist size: &a" + StorageManager.getWhitelist().size() + " IPs");
    }
}

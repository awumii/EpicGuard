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

package me.xneox.epicguard.core.task;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.util.VersionUtils;

public class UpdateCheckerTask implements Runnable {
    private final EpicGuard epicGuard;

    public UpdateCheckerTask(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public void run() {
        VersionUtils.checkForUpdates(this.epicGuard);
        if (VersionUtils.isAvailable()) {
            this.epicGuard.getLogger().info(this.epicGuard.getMessages().updateAvailable()
                    .replace("{NEWVER}", VersionUtils.getRemoteVersion())
                    .replace("{OLDVER}", this.epicGuard.getPlatform().getVersion()));
        }
    }
}

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

package me.xneox.epicguard.core.util;

import me.xneox.epicguard.core.EpicGuard;

// this class needs to be rewritten...
public final class UpdateChecker {
    private static final String CHECK_URL = "https://api.spigotmc.org/legacy/update.php?resource=72369";
    private static String remoteVersion;
    private static boolean available;

    public static void checkForUpdates(EpicGuard epicGuard) {
        if (!epicGuard.getConfig().updateChecker) {
            return;
        }

        remoteVersion = URLUtils.readString(CHECK_URL);
        int latest = Integer.parseInt(remoteVersion.replace(".", ""));
        int current = Integer.parseInt(epicGuard.getPlugin().getVersion().replace(".", ""));

        available = latest > current;
    }

    public static boolean isAvailable() {
        return available;
    }

    public static String getRemoteVersion() {
        return remoteVersion;
    }

    private UpdateChecker() {
    }
}

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

package me.ishift.epicguard.bukkit.util;

import me.ishift.epicguard.common.util.ChatUtil;
import me.ishift.epicguard.common.util.URLHelper;
import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.Messages;
import org.bukkit.entity.Player;

public class Updater {
    private static final String CURRENT_VERSION = EpicGuardBukkit.getInstance().getDescription().getVersion();
    private static final String UPDATE_URL = "https://raw.githubusercontent.com/PolskiStevek/EpicGuard/master/files/version.info";
    private static String latestVersion;
    private static boolean updateAvailable = false;

    public static String getCurrentVersion() {
        return CURRENT_VERSION;
    }

    public static void checkForUpdates() {
        if (Config.updater) {
            latestVersion = URLHelper.readString(UPDATE_URL);
            updateAvailable = !latestVersion.equals(CURRENT_VERSION);
        }
    }

    public static void notify(Player p) {
        if (Config.updater && p.hasPermission("epicguard.admin") && updateAvailable) {
            p.sendMessage(ChatUtil.fix(Messages.prefix + "&cOutdated version &8(&6" + CURRENT_VERSION + "&8)&c! New version is available &8(&6" + Updater.latestVersion + "&8)"));
            p.sendMessage(ChatUtil.fix(Messages.prefix + "&cPlease download update here: &6https://www.spigotmc.org/resources/72369"));
        }
    }
}

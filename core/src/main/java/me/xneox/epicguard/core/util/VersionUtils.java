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

import java.util.function.Consumer;
import me.xneox.epicguard.core.EpicGuard;

public final class VersionUtils {
  private static final String CHECK_URL = "https://raw.githubusercontent.com/xxneox/EpicGuard/master/VERSION.txt";

  /**
   * Checks the latest version to see if there's any update available.
   *
   * @param epicGuard EpicGuard instance.
   * @param action Action to run when there's an update available.
   */
  public static void checkForUpdates(EpicGuard epicGuard, Consumer<String> action) {
    if (!epicGuard.config().misc().updateChecker()) {
      return;
    }

    String latest = URLUtils.readString(CHECK_URL);
    if (latest == null) {
      epicGuard.logger().warn("Could not fetch the latest version.");
      return;
    }

    if (parse(latest) > parse(epicGuard.platform().version())) {
      action.accept(latest);
    }
  }

  private static int parse(String version) {
    return Integer.parseInt(version.replace(".", ""));
  }
}

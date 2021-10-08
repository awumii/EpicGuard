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

/**
 * This util holds current EpicGuard version and checks for the latest available version.
 * TODO: Rewrite to automatically get latest release from github (and proper semver parsing)
 */
public final class VersionUtils {
  public static final String VERSION = "{version}"; // replaced by the blossom task.
  private static final String CHECK_URL = "https://raw.githubusercontent.com/xxneox/EpicGuard/master/VERSION.txt";

  /**
   * Checks the latest version to see if there's any update available.
   *
   * @param action Action to run when there's an update available.
   */
  public static void checkForUpdates(Consumer<String> action) {
    String latest = URLUtils.readString(CHECK_URL);
    if (latest == null) {
      return; // a warning will be thrown by the URLUtils anyway.
    }

    if (parse(latest) > parse(VERSION)) {
      action.accept(latest);
    }
  }

  /**
   * A primitive version parsing. Replaces all dots in the version string
   * and returns the result as an integer. (todo: proper parsing)
   *
   * @param version Version string, eg. "5.1.0"
   * @return an int version value, eg. "510"
   */
  private static int parse(String version) {
    return Integer.parseInt(version.replace(".", ""));
  }
}

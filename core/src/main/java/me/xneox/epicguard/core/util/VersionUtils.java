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
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

/**
 * This util holds current EpicGuard version and checks for the latest available version.
 */
public final class VersionUtils {
  public static final String CURRENT_VERSION = "{version}"; // replaced by the blossom task.
  private static final String CHECK_URL = "https://raw.githubusercontent.com/xxneox/EpicGuard/master/VERSION.txt";

  /**
   * Checks the latest version to see if there's any update available.
   * @param action Action to run when there's an update available.
   */
  public static void checkForUpdates(@NotNull Consumer<String> action) {
    String latest = URLUtils.readString(CHECK_URL);
    if (latest == null) {
      return; // a warning will be thrown by the URLUtils anyway.
    }

    var latestVersion = new Version(latest);
    var currentVersion = new Version(CURRENT_VERSION);

    // 1 means outdated, 0 means up-to-date, -1 means newer than released.
    if (latestVersion.compareTo(currentVersion) > 0) {
      action.accept(latest);
    }
  }

  /**
   * @author brianguertin (https://gist.github.com/brianguertin/ada4b65c6d1c4f6d3eee3c12b6ce021b)
   * Slightly modified.
   */
  public static class Version implements Comparable<Version> {
    public final int[] numbers;

    public Version(@NonNull String version) {
      var split = version.split("-")[0].split("\\.");
      numbers = new int[split.length];
      for (int i = 0; i < split.length; i++) {
        numbers[i] = Integer.parseInt(split[i]);
      }
    }

    @Override
    public int compareTo(@NonNull Version another) {
      int maxLength = Math.max(numbers.length, another.numbers.length);
      for (int i = 0; i < maxLength; i++) {
        int left = i < numbers.length ? numbers[i] : 0;
        int right = i < another.numbers.length ? another.numbers[i] : 0;
        if (left != right) {
          return left < right ? -1 : 1;
        }
      }
      return 0;
    }
  }
}

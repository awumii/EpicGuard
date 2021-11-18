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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import me.xneox.epicguard.core.EpicGuardAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This util helps with performing URL requests.
 */
public final class URLUtils {
  @Nullable
  public static String readString(@NotNull String url) {
    try {
      var connection = openConnection(url);
      try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.toString())) {
        scanner.useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
      }
    } catch (IOException exception) {
      EpicGuardAPI.INSTANCE.instance().logger().warn("Couldn't read the content of " + url + " [" + exception.getMessage() + "]");
    }
    return null;
  }

  @NotNull
  public static URLConnection openConnection(@NotNull String url) throws IOException {
    var connection = new URL(url).openConnection();
    connection.addRequestProperty("User-Agent", "Mozilla/4.0");
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);
    return connection;
  }
}

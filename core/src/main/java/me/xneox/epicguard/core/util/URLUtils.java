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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import me.xneox.epicguard.core.EpicGuardAPI;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class URLUtils {
  @Nullable
  public static String readString(@NotNull String url) {
    try {
      URLConnection connection = openConnection(url);
      try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.toString())) {
        scanner.useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
      }
    } catch (IOException exception) {
      EpicGuardAPI.INSTANCE.instance().logger().warn("Couldn't read the content of " + url + " [" + exception.getMessage() + "]");
    }
    return null;
  }

  @Nullable
  public static List<String> readLines(@NotNull String url) {
    try {
      URLConnection connection = openConnection(url);
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      return reader.lines().toList();
    } catch (IOException exception) {
      EpicGuardAPI.INSTANCE.instance().logger().warn("Couldn't read the content of " + url + " [" + exception.getMessage() + "]");
    }
    return null;
  }

  @NotNull
  public static URLConnection openConnection(@NotNull String url) throws IOException {
    URLConnection connection = new URL(url).openConnection();
    connection.addRequestProperty("User-Agent", "Mozilla/4.0");
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);
    return connection;
  }
}

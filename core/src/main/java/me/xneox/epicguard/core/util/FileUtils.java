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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import me.xneox.epicguard.core.EpicGuardAPI;
import org.jetbrains.annotations.NotNull;

/**
 * This util helps with managing files.
 */
public final class FileUtils {
  public static final String EPICGUARD_DIR = "plugins/EpicGuard";

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static void downloadFile(@NotNull String urlFrom, @NotNull File file) throws IOException {
    EpicGuardAPI.INSTANCE.instance().logger().info("Downloading file from " + urlFrom + " to " + file.getAbsolutePath());

    // Make sure the original file will be replaced, if it exists
    file.delete();
    file.createNewFile();

    URLConnection connection = URLUtils.openConnection(urlFrom);
    try (ReadableByteChannel channel = Channels.newChannel(connection.getInputStream());
        FileOutputStream out = new FileOutputStream(file)) {

      out.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
    }
  }
}

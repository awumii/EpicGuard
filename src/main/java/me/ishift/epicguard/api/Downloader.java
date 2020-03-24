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

package me.ishift.epicguard.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {

    /**
     * @param urlFrom URL from where file will be downloaded.
     * @param file File where it should be downloaded.
     * @throws IOException If file could not be created/deleted.
     */
    public static void download(String urlFrom, File file) throws IOException {
        EpicGuardAPI.getLogger().info("Downloding file from: " + urlFrom);
        EpicGuardAPI.getLogger().info("This may take some time, please wait...");
        if (file.delete()) {
            EpicGuardAPI.getLogger().debug("Deleted file " + file.getName());
        }
        if (file.createNewFile()) {
            EpicGuardAPI.getLogger().debug("Created file " + file.getName());
        }
        final URLConnection connection = new URL(urlFrom).openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");

        final ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
        final FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }
}

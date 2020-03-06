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

package me.ishift.epicguard.universal.cloud;

import me.ishift.epicguard.universal.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {
    public static final String MIRROR_GEO = "https://github.com/PolskiStevek/EpicGuard/raw/master/files/GeoLite2-Country.mmdb";

    public static void download(String from, String to) throws IOException {
        final File file = new File(to);
        if (file.createNewFile()) {
            Logger.debug("Created file " + file.getName());
        }
        if (file.delete()) {
            Logger.debug("Deleted file " + file.getName());
        }
        final URL url = new URL(from);
        final ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        final FileOutputStream fos = new FileOutputStream(to);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }
}

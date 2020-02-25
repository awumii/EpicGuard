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

package me.ishift.epicguard.bukkit.util.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {
    public static void download(String from, String to) throws IOException {
        final File file = new File(to);
        if (file.exists()) {
            file.delete();
        }
        final URL url = new URL(from);
        final ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        final FileOutputStream fos = new FileOutputStream(to);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }
}

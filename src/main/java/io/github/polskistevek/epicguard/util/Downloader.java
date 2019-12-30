package io.github.polskistevek.epicguard.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {
    public static final String MIRROR_GEO = "https://github.com/PolskiStevek/EpicGuard/raw/master/files/GeoLite2-Country.mmdb";
    public static final String MIRROR_MESSAGES = "https://raw.githubusercontent.com/PolskiStevek/EpicGuard/master/files/messages_en_US.yml";

    public static void download(String from, String to) throws IOException {
        URL url = new URL(from);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(to);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }
}

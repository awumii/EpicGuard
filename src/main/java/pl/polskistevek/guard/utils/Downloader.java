package pl.polskistevek.guard.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {
    public static void download(String from, String to) throws IOException {
        URL url = new URL(from);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(to);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}

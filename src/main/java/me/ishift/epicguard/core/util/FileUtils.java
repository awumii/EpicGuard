package me.ishift.epicguard.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public final class FileUtils {
    public static void downloadFile(String urlFrom, File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            URLConnection connection = new URL(urlFrom).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");

            ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileUtils() {
    }
}

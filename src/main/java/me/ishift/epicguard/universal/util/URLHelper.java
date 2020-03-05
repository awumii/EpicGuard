package me.ishift.epicguard.universal.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class URLHelper {
    public static String readString(String requestURL) throws IOException {
        final URLConnection connection = new URL(requestURL).openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");

        try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    public static List<String> readLines(String requestURL) {
        try {
            final URLConnection connection = new URL(requestURL).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");

            final Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.toString());
            final List<String> lines = new ArrayList<>();

            if (scanner.hasNextLine()) {
                while (scanner.hasNext()) {
                    lines.add(scanner.next());
                }
            }

            scanner.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

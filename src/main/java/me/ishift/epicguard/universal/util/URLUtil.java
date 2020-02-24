package me.ishift.epicguard.universal.util;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class URLUtil {
    public static String readString(String requestURL) throws IOException {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    public static List<String> readLines(String requestURL) throws IOException {
        final Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString());
        final List<String> lines = new ArrayList<>();
        if (scanner.hasNextLine()) {
            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        }
        scanner.close();
        return lines;
    }
}

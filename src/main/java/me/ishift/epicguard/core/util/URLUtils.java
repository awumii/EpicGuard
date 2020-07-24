package me.ishift.epicguard.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class URLUtils {
    public static String readString(String requestURL) {
        try {
            final URLConnection connection = new URL(requestURL).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");

            try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.toString())) {
                scanner.useDelimiter("\\A");
                return scanner.hasNext() ? scanner.next() : "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestURL;
    }

    public static List<String> readLines(String from) {
        List<String> list = new ArrayList<>();
        try {
            URLConnection connection = new URL(from).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while (reader.readLine() != null) {
                list.add(reader.readLine());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private URLUtils() {
    }
}

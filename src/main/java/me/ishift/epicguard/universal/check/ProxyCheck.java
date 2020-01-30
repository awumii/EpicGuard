package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class ProxyCheck {

    public static boolean check(String adress) {
        final String url = "http://proxycheck.io/v2/" + adress + "?key=" + Config.apiKey;

        try {
            final Scanner scanner = new Scanner(new URL(url).openStream());
            Logger.debug("# Checking proxy from URL: " + url);
            if (scanner.hasNextLine()) {
                while (scanner.hasNext()) {
                    if (scanner.next().contains("yes")) {
                        Logger.debug("# Detected Proxy, URL: " + url);
                        return true;
                    }
                }
                Logger.debug("# Proxy is not detected from: " + url);
                scanner.close();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

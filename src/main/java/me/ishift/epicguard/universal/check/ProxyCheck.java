package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.Logger;

import java.net.URL;
import java.util.Scanner;

public class ProxyCheck {

    public static boolean check(String adress) {
        final String url1 = Config.antibotQuery1.replace("{IP}", adress);
        final String url2 = Config.antibotQuery2.replace("{IP}", adress);

        return checkUrl(url1) || checkUrl(url2);
    }

    private static boolean checkUrl(String url) {
        try {
            // Not working site.
            if (url.contains("ip.teoh.io")) {
                return false;
            }
            final Scanner s = new Scanner(new URL(url).openStream());
            Logger.debug("# Checking proxy from URL: " + url);
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (Config.antibotQueryContains.contains(s.next())) {
                        Logger.debug("# Detected Proxy, URL: " + url);
                        return true;
                    }
                }
                Logger.debug("# Proxy is not detected from: " + url);
                return false;
            }
        } catch (Exception e) {
            Logger.info("EXCEPTION WHILE CHECKING DATA FROM URL: " + url);
            return false;
        }
        return false;
    }
}

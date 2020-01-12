package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.Logger;

import java.net.URL;
import java.util.Scanner;

public class ProxyCheck {

    public static boolean check(String adress) {
        final String url1 = Config.ANTIBOT_QUERY_1.replace("{IP}", adress);
        final String url2 = Config.ANTIBOT_QUERY_2.replace("{IP}", adress);
        final String url3 = Config.ANTIBOT_QUERY_3.replace("{IP}", adress);

        return checkUrl(url1) || checkUrl(url2) || checkUrl(url3);
    }

    private static boolean checkUrl(String url) {
        try {
            final Scanner s = new Scanner(new URL(url).openStream());
            Logger.debug("# Checking proxy from URL: " + url);
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (Config.ANTIBOT_QUERY_CONTAINS.contains(s.next())) {
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

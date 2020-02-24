package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.URLUtil;

import java.io.IOException;

public class ProxyCheck {

    public static boolean check(String adress) {
        final String url = "http://proxycheck.io/v2/" + adress + "?key=" + Config.apiKey;
        try {
            return URLUtil.readLines(url).contains("yes");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

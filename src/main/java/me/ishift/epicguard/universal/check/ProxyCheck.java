package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.URLHelper;

import java.io.IOException;
import java.util.List;

public class ProxyCheck {
    public static boolean perform(String address) {
        final String url = "http://proxycheck.io/v2/" + address + "?key=" + Config.apiKey;
        final List<String> response = URLHelper.readLines(url);

        if (response != null) {
            return response.contains("yes");
        }
        return false;
    }
}

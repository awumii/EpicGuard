package me.ishift.epicguard.common.detection;

import me.ishift.epicguard.api.URLHelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ProxyManager {
    private static final Collection<ProxyChecker> CHECKERS = new HashSet<>();

    public static Collection<ProxyChecker> getCheckers() {
        return CHECKERS;
    }

    public static boolean isProxyUser(String address) {
        for (ProxyChecker proxyChecker : CHECKERS) {
            final String url = proxyChecker.getUrl().replace("{ADDRESS}", address);
            final List<String> response = URLHelper.readLines(url);
            if (response != null && response.contains(proxyChecker.getUrl())) {
                return true;
            }
        }
        return false;
    }
}

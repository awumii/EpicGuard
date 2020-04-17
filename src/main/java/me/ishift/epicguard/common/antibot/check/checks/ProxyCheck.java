package me.ishift.epicguard.common.antibot.check.checks;

import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.antibot.ProxyChecker;
import me.ishift.epicguard.common.antibot.check.Check;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.util.URLHelper;

import java.util.List;

public class ProxyCheck implements Check {
    @Override
    public boolean execute(String address, String nickname, AttackManager attackManager) {
        if (Configuration.simpleProxyCheck) {
            final String url = "https://proxycheck.io/v2/" + address + "?key=" + Configuration.apiKey;
            final List<String> response = URLHelper.readLines(url);
            return response != null && response.contains("yes");
        }

        if (Configuration.advancedProxyChecker) {
            for (ProxyChecker proxyChecker : attackManager.getProxyCheckers()) {
                final String url = proxyChecker.getUrl().replace("{ADDRESS}", address);
                final List<String> response = URLHelper.readLines(url);

                if (response == null) {
                    return false;
                }
                for (String responseString : response) {
                    for (String containsString : proxyChecker.getContains()) {
                        if (responseString.contains(containsString)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

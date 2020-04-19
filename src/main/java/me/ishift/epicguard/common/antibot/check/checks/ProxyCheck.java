/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.ishift.epicguard.common.antibot.check.checks;

import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.antibot.ProxyChecker;
import me.ishift.epicguard.common.antibot.check.Check;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.util.URLHelper;

import java.util.List;

public class ProxyCheck implements Check {
    private final AttackManager attackManager;

    public ProxyCheck(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    @Override
    public boolean execute(String address, String nickname) {
        if (Configuration.simpleProxyCheck) {
            final String url = "https://proxycheck.io/v2/" + address + "?key=" + Configuration.apiKey;
            final List<String> response = URLHelper.readLines(url);
            return response != null && response.contains("yes");
        }

        if (Configuration.advancedProxyChecker) {
            for (ProxyChecker proxyChecker : this.attackManager.getProxyCheckers()) {
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

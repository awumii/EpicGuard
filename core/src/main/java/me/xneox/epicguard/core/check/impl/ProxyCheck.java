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

package me.xneox.epicguard.core.check.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.CheckMode;
import me.xneox.epicguard.core.user.BotUser;
import me.xneox.epicguard.core.util.URLUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProxyCheck extends Check {
    private final Cache<String, Boolean> detectionMap = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    public ProxyCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@Nonnull BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().proxyCheck);
        return this.assertCheck(mode, this.isProxy(user.getAddress()));
    }

    private boolean isProxy(String address) {
        return this.detectionMap.asMap().computeIfAbsent(address, ip -> {
            String apiUrl;

            if (this.getConfig().customProxyCheck.equals("disabled")) {
                // Use the default API service - proxycheck.io.
                apiUrl = "http://proxycheck.io/v2/" + ip + "?key=" + this.getConfig().proxyCheckKey + "&vpn=1";
            } else {
                // Use the custom API service.
                apiUrl = this.getConfig().customProxyCheck.replace("%ip%", ip);
            }

            String response = URLUtils.readString(apiUrl);
            return response != null && response.contains("yes");
        });
    }

    @Override
    public @Nonnull List<String> getKickMessage() {
        return this.getMessages().kickMessageProxy;
    }

    @Override
    public boolean shouldBlacklist() {
        return this.getConfig().proxyCheckBlacklist;
    }
}

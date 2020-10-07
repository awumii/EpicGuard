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

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.CheckMode;
import me.xneox.epicguard.core.user.BotUser;
import me.xneox.epicguard.core.util.URLUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProxyCheck extends Check implements Runnable {
    private int requests;

    private final LoadingCache<String, Boolean> detections = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Boolean>() {
                @Override
                public Boolean load(@NotNull String address) {
                    return proxyCheck(address);
                }
            });

    public ProxyCheck(EpicGuard epicGuard) {
        super(epicGuard);
        // This request limiter should be rewritten
        epicGuard.getPlugin().scheduleTask(this, 1L);
    }

    @Override
    public boolean handle(BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().proxyCheck);
        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return this.detections.getUnchecked(user.getAddress());
            case ATTACK:
                if (this.isAttack()) {
                    return this.detections.getUnchecked(user.getAddress());
                }
        }
        return false;
    }

    private boolean proxyCheck(String address) {
        if (this.requests > this.getConfig().requestLimit) {
            return false;
        }

        String url = "http://proxycheck.io/v2/" + address + "?key=" + this.getConfig().proxyCheckKey + "&vpn=1";
        if (!this.getConfig().customProxyCheck.equals("disabled")) {
            url = this.getConfig().customProxyCheck.replace("%ip%", address);
        }

        this.requests++;
        return URLUtils.readString(url).contains("yes");
    }

    @Override
    public void run() {
        this.requests = 0;
    }

    @Override
    public List<String> getKickMessage() {
        return this.getMessages().kickMessageProxy;
    }

    @Override
    public boolean shouldBlacklist() {
        return this.getConfig().proxyCheckBlacklist;
    }
}

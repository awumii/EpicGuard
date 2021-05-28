package me.xneox.epicguard.core.proxy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.util.URLUtils;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * Performs requests to registered ProxyServices and caches the results.
 */
public class ProxyManager {
    private final EpicGuard epicGuard;
    private final Cache<String, Boolean> resultCache;

    public ProxyManager(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
        this.resultCache = CacheBuilder.newBuilder()
                .expireAfterWrite(epicGuard.config().proxyCheck().cacheDuration(), TimeUnit.SECONDS)
                .build();
    }

    /**
     * This method will send requests to the registered ProxyServices until the result is positive.
     * If the result is present in cache, the value from the cache will be returned instead.
     *
     * @param address The checked IP address.
     * @return Whenever the address is detected to be a proxy or not.
     */
    public boolean isProxy(@Nonnull String address) {
        return this.resultCache.asMap().computeIfAbsent(address, userIp -> {
            for (ProxyService service : this.epicGuard.config().proxyCheck().services()) {
                String response = URLUtils.readString(service.url().replace("%ip%", userIp));

                if (response != null && response.matches(service.responseContains())) {
                    return true;
                }
            }
            return false;
        });
    }
}

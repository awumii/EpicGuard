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

package me.xneox.epicguard.core.proxy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.util.URLUtils;
import org.jetbrains.annotations.NotNull;

/** Performs requests to registered ProxyServices and caches the results. */
public class ProxyManager {
  private final EpicGuard epicGuard;
  private final Cache<String, Boolean> resultCache;

  public ProxyManager(EpicGuard epicGuard) {
    this.epicGuard = epicGuard;
    this.resultCache = CacheBuilder.newBuilder()
        .expireAfterWrite(epicGuard.config().proxyCheck().cacheDuration(), TimeUnit.SECONDS)
        .build();

    for (ProxyService service : this.epicGuard.config().proxyCheck().services()) {
      epicGuard.logger().warn(service.url() + "<>" + service.matcher());
    }
  }

  /**
   * This method will send requests to the registered ProxyServices until the result is positive. If
   * the result is present in cache, the value from the cache will be returned instead.
   *
   * @param address The checked IP address.
   * @return Whenever the address is detected to be a proxy or not.
   */
  public boolean isProxy(@NotNull String address) {
    return this.resultCache.asMap().computeIfAbsent(address, userIp -> {
      for (ProxyService service : this.epicGuard.config().proxyCheck().services()) {
        String response = URLUtils.readString(service.url().replace("{IP}", userIp));
        if (response != null && service.matcher().matcher(response).find()) {
          return true;
        }
      }
      return false;
    });
  }
}

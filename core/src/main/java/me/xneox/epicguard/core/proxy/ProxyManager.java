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

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.util.LogUtils;
import me.xneox.epicguard.core.util.URLUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * Performs requests to the registered ProxyServices and caches the results.
 */
public final class ProxyManager {
  private final EpicGuard epicGuard;
  private final Cache<String, Boolean> resultCache;

  public ProxyManager(final EpicGuard epicGuard) {
    this.epicGuard = epicGuard;
    this.resultCache = Caffeine.newBuilder()
        .expireAfterWrite(epicGuard.config().proxyCheck().cacheDuration(), TimeUnit.SECONDS)
        .build();
  }

  /**
   * This method reads the response from all the registered ProxyServices until the detection is positive.
   * If the result is present in cache, the value from the cache will be returned instead.
   *
   * @param address The checked IP address.
   * @return Whenever the address is detected to be a proxy or not.
   */
  public boolean isProxy(final @NotNull String address) {
    return this.resultCache.get(address, userIp -> {
      for (final ProxyService service : this.epicGuard.config().proxyCheck().services()) {
        final String url = service.url().replace("{IP}", userIp);
        LogUtils.debug("Sending request to: " + url);

        final String response = URLUtils.readString(url);
        LogUtils.debug("Received response: " + response);

        if (response != null && service.matcher().matcher(response).find()) {
          return true;
        }
      }
      return false;
    });
  }
}

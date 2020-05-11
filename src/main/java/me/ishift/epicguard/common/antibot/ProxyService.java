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

package me.ishift.epicguard.common.antibot;

import java.util.List;

public class ProxyService {
    private final String url;
    private final List<String> contains;

    /**
     * This creates a new ProxyService which can be
     * used to detect if user is using proxy/VPN.
     *
     * @param url URL of the service (request)
     * @param contains Words which must be in the response to
     * cause a positive proxy detection.
     */
    public ProxyService(String url, List<String> contains) {
        this.url = url;
        this.contains = contains;
    }

    /**
     * @return URL of the service.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @return Words which must be in the response to
     * cause a positive proxy detection.
     */
    public List<String> getContains() {
        return this.contains;
    }
}

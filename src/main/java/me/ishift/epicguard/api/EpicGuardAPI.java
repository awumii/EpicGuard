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

package me.ishift.epicguard.api;

public class EpicGuardAPI {
    private static GeoAPI geoApi;
    private static GuardLogger guardLogger;

    /**
     * @return Current Logger instance.
     */
    public static GuardLogger getLogger() {
        return guardLogger;
    }

    /**
     * Should be used only when plugin is loading.
     *
     * @param guardLogger New Logger instance.
     */
    public static void setLogger(GuardLogger guardLogger) {
        EpicGuardAPI.guardLogger = guardLogger;
    }

    /**
     * @return GeoAPI instance
     */
    public static GeoAPI getGeoApi() {
        return geoApi;
    }

    /**
     * Should be used only when plugin is loading.
     *
     * @param geoApi new GeoAPI object.
     */
    public static void setGeoApi(GeoAPI geoApi) {
        EpicGuardAPI.geoApi = geoApi;
    }
}

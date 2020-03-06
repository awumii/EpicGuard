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

import me.ishift.epicguard.bukkit.util.server.ServerTPS;

public class EpicGuardAPI {
    private static GeoAPI geoApi;

    /**
     * This method does work on every Bukkit-based server.
     *
     * @return Server's recent TPS (from 1 minute).
     */
    public static String getBukkitTps() {
        return ServerTPS.getTPS();
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

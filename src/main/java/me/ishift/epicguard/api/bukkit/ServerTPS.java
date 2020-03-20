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

package me.ishift.epicguard.api.bukkit;

import io.sentry.Sentry;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

public class ServerTPS {
    /**
     * @return Current server's TPS (from 1 minute).
     */
    public static String getTPS() {
        try {
            final DecimalFormat format = new DecimalFormat("##.##");
            final Object serverInstance;
            final Field tpsField;
            serverInstance = Reflection.getNMSClass("MinecraftServer").getMethod("getServer").invoke(null);
            tpsField = serverInstance.getClass().getField("recentTps");

            double[] tps = ((double[]) tpsField.get(serverInstance));
            return format.format(tps[0]);
        } catch (NoSuchMethodException | InvocationTargetException | NoSuchFieldException | IllegalAccessException e) {
            Sentry.capture(e);
        }
        return "20.0";
    }
}

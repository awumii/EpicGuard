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

package me.xneox.epicguard.core.util;

import me.xneox.epicguard.core.config.ConfigLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.logging.Logger;

public final class ConfigUtils {
    private static final Logger LOGGER = Logger.getLogger(ConfigUtils.class.getName());

    /**
     * Loads the configuration from provided file, based on the implementation class.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Nullable
    public static <C> C loadConfig(@NotNull File file, Class<C> implementation) {
        try {
            file.createNewFile(); // create new file if it doesn't exist.

            ConfigLoader<C> configLoader = new ConfigLoader<>(file, implementation);
            C configuration = configLoader.load(); // load the configuration.

            configLoader.save(configuration); // on first load the config file will be empty, so we write the default values.
            return configuration;
        } catch (Exception e) {
            LOGGER.severe("Could load configuration for " + file.getName());
            e.printStackTrace();
        }
        return null;
    }

    private ConfigUtils() {}
}

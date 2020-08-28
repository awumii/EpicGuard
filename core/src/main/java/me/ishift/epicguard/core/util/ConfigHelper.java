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

package me.ishift.epicguard.core.util;

import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.libs.org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author dzikoysk
 * https://github.com/FunnyGuilds/FunnyGuilds/blob/master/src/main/java/net/dzikoysk/funnyguilds/util/commons/ConfigHelper.java
 */
public final class ConfigHelper {

    @SuppressWarnings("unchecked")
    public static <T> T loadConfig(final File file, final Class<T> implementationFile) {
        final Constructor<T> implementationFileConstructor = (Constructor<T>) getConstructor(implementationFile);
        final Template<T> template = TemplateCreator.getTemplate(implementationFile);

        T config;

        if (!file.exists()) {
            try {
                try {
                    config = template.fillDefaults(implementationFileConstructor.newInstance());
                } catch (final InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException("Couldn't get access to " + implementationFile.getName() + "  constructor", e);
                }

                Validate.isTrue(file.createNewFile(), "Couldn't create " + file.getAbsolutePath() + " config file");

            } catch (final IOException e) {
                throw new RuntimeException("IO exception when creating config file: " + file.getAbsolutePath(), e);
            }
        } else {
            try {
                try {
                    config = template.load(file);
                    if (config == null) {
                        config = template.fillDefaults(implementationFileConstructor.newInstance());
                    }
                } catch (final IOException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException("IO exception when loading config file: " + file.getAbsolutePath(), e);
                }
            } catch (final InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Couldn't get access to " + implementationFile.getName() + "  constructor", e);
            }
        }

        try {
            template.dump(file, config, false);
        } catch (final IOException e) {
            throw new RuntimeException("Can't dump configuration file!", e);
        }

        return config;
    }

    private static Constructor<?> getConstructor(Class<?> clazz, Class<?>... arguments) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (Arrays.equals(constructor.getParameterTypes(), arguments)) {
                return constructor;
            }
        }

        return null;
    }

    private ConfigHelper() {
    }
}

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

package me.ishift.epicguard.velocity.util;

import io.sentry.Sentry;
import me.ishift.epicguard.api.Downloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Velocity does not include mysql/jdbc, so we need to download it and load.
 */
public class JdbcLoader {
    public static void init() {
        final String version = "8.0.19";
        final String url = "https://repo1.maven.org/maven2/mysql/mysql-connector-java/" + version + "/mysql-connector-java-" + version + ".jar";
        final File lib = new File("plugins/EpicGuard/mysql-connector-java-" + version + ".jar");

        if (!lib.exists()) {
            final Downloader downloader = new Downloader(url, lib);
            try {
                downloader.download();
            } catch (IOException e) {
                Sentry.capture(e);
            }
        }
        loadLibrary(lib);
    }

    public static void loadLibrary(File file) {
        try {
            final URL url = file.toURI().toURL();

            final URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            final Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, url);
        } catch (Exception e) {
            Sentry.capture(e);
        }
    }
}

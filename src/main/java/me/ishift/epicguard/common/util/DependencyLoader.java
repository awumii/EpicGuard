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

package me.ishift.epicguard.common.util;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Velocity does not include mysql/jdbc, so we need to download it and load.
 */
public class DependencyLoader {
    public static void checkJars() {
        tryDownload("com.zaxxer.hikari.HikariDataSource", "https://repo1.maven.org/maven2/com/zaxxer/HikariCP/3.4.2/HikariCP-3.4.2.jar", "HikariCP-3.4.2.jar.jar");
        tryDownload("com.mysql.jdbc.Driver", "https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.19/mysql-connector-java-8.0.19.jar", "mysql-connector-java-8.0.19.jar");
    }

    public static void tryDownload(String className, String url, String fileName) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            final File dir = new File("plugins/EpicGuard/lib");
            dir.mkdir();
            final File lib = new File("plugins/EpicGuard/lib/" + fileName + ".jar");

            if (!lib.exists()) {
                try {
                    Downloader.download(url, lib);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            loadLibrary(lib);
        }
    }

    public static void loadLibrary(File file) {
        try {
            final URL url = file.toURI().toURL();

            final URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            final Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

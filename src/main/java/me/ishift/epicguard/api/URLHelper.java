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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class URLHelper {
    /**
     * The User-Agent property is set to "Mozilla/4.0"
     *
     * @param requestURL Target URL.
     * @return Content of website, in single String.
     */
    public static String readString(String requestURL) {
        try {
            final URLConnection connection = new URL(requestURL).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");

            try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.toString())) {
                scanner.useDelimiter("\\A");
                return scanner.hasNext() ? scanner.next() : "";
            }
        } catch (IOException e) {
            EpicGuardAPI.getLogger().info("[ERROR] ExceptionStackTrace");
            EpicGuardAPI.getLogger().info(" Error: " + e.toString());
            Arrays.stream(e.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString().split("\\(")).filter(errors -> errors[0].contains("me.ishift.epicguard")).map(errors -> errors[1]).map(line -> line.replace(".java", "")).map(line -> line.replace(":", " Line: ")).map(line -> line.replace("\\)", "")).forEach(line -> EpicGuardAPI.getLogger().info("Klasa: " + line));
        }
        return requestURL;
    }

    /**
     * The User-Agent property is set to "Mozilla/4.0"
     *
     * @param requestURL Target URL.
     * @return Content of website, every line as String list.
     */
    public static List<String> readLines(String requestURL) {
        try {
            final URLConnection connection = new URL(requestURL).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");

            final Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.toString());
            final List<String> lines = new ArrayList<>();

            if (scanner.hasNextLine()) {
                while (scanner.hasNext()) {
                    lines.add(scanner.next());
                }
            }

            scanner.close();
            return lines;
        } catch (IOException e) {
            EpicGuardAPI.getLogger().info("[ERROR] ExceptionStackTrace");
            EpicGuardAPI.getLogger().info(" Error: " + e.toString());
            Arrays.stream(e.getStackTrace())
                    .map(stackTraceElement -> stackTraceElement.toString().split("\\("))
                    .filter(errors -> errors[0].contains("me.ishift.epicguard"))
                    .map(errors -> errors[1])
                    .map(line -> line.replace(".java", ""))
                    .map(line -> line.replace(":", " Line: "))
                    .map(line -> line.replace("\\)", ""))
                    .forEach(line -> EpicGuardAPI.getLogger().info("Klasa: " + line));
        }
        return null;
    }
}

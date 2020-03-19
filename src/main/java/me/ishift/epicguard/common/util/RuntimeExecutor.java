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

import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.common.Config;

import java.io.IOException;
import java.util.Arrays;

public class RuntimeExecutor {
    public static void execute(String command) {
        if (!Config.firewallEnabled) {
            return;
        }
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            EpicGuardAPI.getLogger().info("[ERROR] ExceptionStackTrace");
            EpicGuardAPI.getLogger().info(" Error: " + ex.toString());
            Arrays.stream(ex.getStackTrace())
                    .map(stackTraceElement -> stackTraceElement.toString().split("\\("))
                    .filter(errors -> errors[0].contains("me.ishift.epicguard"))
                    .map(errors -> errors[1])
                    .map(line -> line.replace(".java", ""))
                    .map(line -> line.replace(":", " Line: "))
                    .map(line -> line.replace("\\)", ""))
                    .forEach(line -> EpicGuardAPI.getLogger().info("Klasa: " + line));
        }
    }

    public static void blacklist(String adress) {
        execute(Config.firewallBlacklistCommand.replace("{IP}", adress));
    }

    public static void whitelist(String adress) {
        execute(Config.firewallWhitelistCommand.replace("{IP}", adress));
    }
}

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

package me.ishift.epicguard.common;

import me.ishift.epicguard.common.util.DateUtil;
import me.ishift.epicguard.common.util.FileUtil;

import java.io.File;
import java.util.logging.Logger;

public class GuardLogger {
    private final AttackManager attackManager;
    private final Logger logger;
    private final String name;
    private final String path;

    /**
     * Creating new GuardLogger instance
     * @param name Name of the Logger.
     * @param path Path for the log files.
     */
    public GuardLogger(String name, String path, AttackManager manager) {
        this.logger = Logger.getLogger(name);
        this.attackManager = manager;
        this.name = name;
        this.path = path;
        final File logDir = new File(path + "/logs");

        if (logDir.mkdirs()) {
            this.info("Created log directory.");
        }
    }

    /**
     * Debug message will only be logged in file.
     *
     * @param message Log message.
     */
    public void debug(String message) {
        this.log(message, true);
    }

    /**
     * Info message will be logged in file and displayed in the console.
     *
     * @param message Log message.
     */
    public void info(String message) {
        this.log(message, false);
    }

    /**
     * Method is private, use debug() or info().
     *
     * @param message Log message
     * @param hide Should message be hidden in console (debug) or not.
     */
    private void log(String message, boolean hide) {
        if (!hide) {
            this.logger.info("[EpicGuard] " + message);
        }

        // If there is fast bot attack then the cpu will 'explode' from the amount of written lines.
        if (this.logger.getName().equals("EpicGuard") && this.attackManager.getConnectPerSecond() > 20) {
            return;
        }
        final File file = new File(this.path + "/logs/" + this.name + "-" + DateUtil.getDate() + ".txt");
        final String logMessage = "(" + DateUtil.getTime() + "/" + (hide ? "DEBUG" : "INFO") + ")" + message;
        FileUtil.writeToFile(file, logMessage);
    }
}

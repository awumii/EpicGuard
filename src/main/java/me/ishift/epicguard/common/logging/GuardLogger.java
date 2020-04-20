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

package me.ishift.epicguard.common.logging;

import me.ishift.epicguard.common.util.DateUtil;
import me.ishift.epicguard.common.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class GuardLogger {
    private final Logger logger;

    public GuardLogger() {
        this.logger = Logger.getLogger(GuardLogger.class.getName());
        this.logger.setUseParentHandlers(false);

        final ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new LogFormatter());

        this.logger.addHandler(handler);
    }

    /**
     * Logs message on the console and to the log file.
     *
     * @param message Log message.
     */
    public void info(String message) {
        this.logger.info(message);
        this.log(message);
    }

    /**
     * Logs message only to the log file, not displayed in the console.
     *
     * @param message Log message.
     */
    public void debug(String message) {
        this.log(message);
    }

    /**
     * Logs message to the log file.
     *
     * @param message Log message.
     */
    private void log(String message) {
        final File dir = new File("plugins/EpicGuard/logs");
        final File file = new File("plugins/EpicGuard/logs/" + DateUtil.getDate());
        dir.mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileUtil.writeToFile(file, "[" + DateUtil.getTime() + "] " + message);
    }
}

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

import me.ishift.epicguard.common.detection.AttackSpeed;

import java.io.*;
import java.util.logging.Logger;

public class GuardLogger {
    private String name;
    private String path;
    private Logger logger;

    /**
     * Creating new GuardLogger instance
     * @param name Name of the Logger.
     * @param path Path for the log files.
     */
    public GuardLogger(String name, String path) {
        this.name = name;
        this.path = path;
        this.logger = Logger.getLogger(name);
        final File logDir = new File(path + "/logs");

        if (logDir.mkdir()) {
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
     * Appending a message to the text file.
     *
     * @param file Target file.
     * @param message Message to be logged.
     */
    public void writeToFile(File file, String message) {
        if (this.logger.getName().equals("EpicGuard") && AttackSpeed.isUnderAttack()) {
            return;
        }
        try {
            if (!file.createNewFile()) {
                return;
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Erases content of a text file, without deleting it.
     *
     * @param file File which should be erased.
     */
    public void eraseFile(File file) {
        try {
            final PrintWriter pw = new PrintWriter(file);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is private, use debug() or info().
     *
     * @param message Log message
     * @param hide Should message be hidden in console (debug) or not.
     */
    private void log(String message, boolean hide) {
        final String logMessage = "(" + DateUtil.getTime() + ") " + message;

        if (!hide) {
            this.logger.info(message);
        }
        final File file = new File(this.path + "/logs/" + this.name + "-" + DateUtil.getDate() + ".txt");
        this.writeToFile(file, logMessage);
    }
}
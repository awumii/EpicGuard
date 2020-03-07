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

import me.ishift.epicguard.common.AttackSpeed;

import java.io.*;

public class Logger {
    private static File file;

    public static void debug(String message) {
        log(message, true);
    }

    public static void info(String message) {
        log(message, false);
    }

    private static void log(String message, boolean hide) {
        final String msg = "(" + DateUtil.getTime() + ") " + message;
        if (!hide) {
            System.out.println("[EpicGuard] " + message);
        }
        writeToFile(file, msg);
    }

    public static void init() {
        try {
            new File("plugins/EpicGuard/logs").mkdir();
            file = new File("plugins/EpicGuard/logs/EpicGuardLogs-" + DateUtil.getDate() + ".txt");
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(File file, String message) {
        try {
            if (AttackSpeed.getConnectPerSecond() > 40) return;
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void eraseFile(File file) {
        try {
            final PrintWriter pw = new PrintWriter(file);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
package me.ishift.epicguard.universal;

import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import me.ishift.epicguard.universal.util.DateUtil;

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
            if (SpeedCheck.getConnectPerSecond() > 40) return;
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
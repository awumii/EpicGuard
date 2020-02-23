package me.ishift.epicguard.universal.util;

import me.ishift.epicguard.bukkit.manager.AttackManager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    private static File file;

    public static void debug(String message) {
        log(message, true);
    }

    public static void info(String message) {
        log(message, false);
    }

    private static void log(String message, boolean hide) {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy/HH:mm:ss");
        final String time = sdf.format(cal.getTime());
        final String msg = "(" + time + ") " + message;
        if (!hide) {
            System.out.println("[EpicGuard] " + message);
        }
        writeToFile(file, msg);
    }

    public static void create() {
        try {
            final Calendar cal = Calendar.getInstance();
            final SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
            final String date = sdf.format(cal.getTime());

            file = new File("plugins/EpicGuard/logs/EpicGuardLogs-" + date + ".txt");
            if (file.createNewFile()) {
                System.out.println("[EpicGuard] Created new log file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(File file, String message) {
        try {
            if (file.createNewFile()) return;
            if (AttackManager.getConnectPerSecond() > 50) return;
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
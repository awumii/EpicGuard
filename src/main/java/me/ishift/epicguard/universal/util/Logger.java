package me.ishift.epicguard.universal.util;

import me.ishift.epicguard.bukkit.manager.AttackManager;

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
        file = new File("plugins/EpicGuard/logs/EpicGuardLogs-" + DateUtil.getDate() + ".txt");
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
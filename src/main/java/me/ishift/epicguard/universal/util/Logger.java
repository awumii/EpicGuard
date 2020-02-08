package me.ishift.epicguard.universal.util;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.types.Platform;

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
        String msg;
        if (!hide) {
            msg = "[" + time + "] > " + message;
            System.out.println("[EpicGuard] > " + message);
        } else {
            msg = "[" + time + "] > " + message;
        }
        writeToFile(file, msg);
    }

    public static void create(Platform platform) {
        try {
            final Calendar cal = Calendar.getInstance();
            final SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
            final String date = sdf.format(cal.getTime());

            if (platform == Platform.SPIGOT) {
                file = new File(GuardBukkit.getInstance().getDataFolder() + "/logs/EpicGuardLogs-" + date + ".txt");
            }
            if (platform == Platform.BUNGEE) {
                file = new File(GuardBungee.getInstance().getDataFolder() + "/logs/EpicGuardLogs-" + date + ".txt");
            }
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("[EpicGuard] Created new log file.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(File file, String message) {
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void eraseFile(File file) {
        if (!file.exists()) {
            return;
        }
        try {
            final PrintWriter pw = new PrintWriter(file);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

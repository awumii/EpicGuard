package me.ishift.epicguard.universal.util;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.ServerType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    private static File file;
    private ServerType type;

    public Logger(ServerType type) {
        this.type = type;
        this.create();
    }

    public static void debug(String message) {
        log(message, true);
    }

    public static void info(String message) {
        log(message, false);
    }

    public static void log(String message, boolean hide) {
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

    public static void throwException(Exception exception) {
        info("");
        info("<> EpicGuard Error (" + exception.getMessage() + ")");
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            if (stackTraceElement.toString().contains("me.ishift.epicguard")) {
                info("<> " + stackTraceElement.toString());
            }
        }
        info("");
    }

    private void create() {
        try {
            final Calendar cal = Calendar.getInstance();
            final SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
            final String date = sdf.format(cal.getTime());
            if (this.type == ServerType.SPIGOT) {
                file = new File(GuardBukkit.getInstance().getDataFolder() + "/logs/EpicGuardLogs-" + date + ".txt");
            }
            if (this.type == ServerType.BUNGEE) {
                file = new File(GuardBungee.getInstance().getDataFolder() + "/logs/EpicGuardLogs-" + date + ".txt");
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            throwException(e);
        }
    }

    public static void writeToFile(File file, String message) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}

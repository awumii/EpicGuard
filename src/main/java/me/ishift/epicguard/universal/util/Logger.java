package me.ishift.epicguard.universal.util;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bungee.GuardBungee;

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
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy/HH:mm:ss");
        String time = sdf.format(cal.getTime());
        String msg;
        if (!hide) {
            msg = "[" + time + "] " + message;
            System.out.println("[EpicGuard] " + message);
        } else {
            msg = "[" + time + "] " + message;
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(msg);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    public static void throwException(Exception exception) {
        info("#######  EPICGUARD ERROR LOG #######");
        info("<> Information: " + exception.getMessage());
        info("<> Stack Trace:");
        info(" ");
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            if (stackTraceElement.toString().contains("me.ishift.epicguard")) {
                info(" " + stackTraceElement.toString());
            }
        }
        info("#######  EPICGUARD ERROR LOG #######");
    }

    private void create() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
            String date = sdf.format(cal.getTime());
            if (this.type == ServerType.SPIGOT) {
                file = new File(GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder() + "/logs/EpicGuardLogs-" + date + ".txt");
            }
            if (this.type == ServerType.BUNGEE) {
                file = new File(GuardBungee.plugin.getDataFolder() + "/logs/EpicGuardLogs-" + date + ".txt");
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            throwException(e);
        }
    }
}

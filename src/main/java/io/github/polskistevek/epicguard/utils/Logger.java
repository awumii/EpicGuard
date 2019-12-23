package io.github.polskistevek.epicguard.utils;

import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.bungee.GuardBungee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    private static File file;

    public static void create(ServerType serverType) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
            String date = sdf.format(cal.getTime());
            if (serverType == ServerType.SPIGOT) {
                file = new File(GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder() + "/logs/EpicGuardLogs-" + date + ".txt");
            }
            if (serverType == ServerType.BUNGEE) {
                file = new File(GuardBungee.plugin.getDataFolder() + "/logs/EpicGuardLogs-" + date + ".txt");
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            throwException(e);
        }
    }

    public static void info(String message, boolean hide) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void throwException(Exception exception) {
        info("#######  EPICGUARD ERROR LOG #######", false);
        info(" ", false);
        info(" <> Information: " + exception.getMessage(), false);
        info(" <> Stack Trace:", false);
        info(" ", false);
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            info("   " + stackTraceElement.toString(), false);
        }
        info(" ", false);
        info(" <> If you think this is a bug, report it on github.", false);
        info(" <> https://github.com/PolskiStevek/EpicGuard/issues", false);
        info(" ", false);
        info("#######  EPICGUARD ERROR LOG #######", false);
    }
}

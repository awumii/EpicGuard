package pl.polskistevek.guard.utils;

import org.bukkit.Bukkit;
import pl.polskistevek.guard.bukkit.BukkitMain;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    private static File file = new File(BukkitMain.getPlugin(BukkitMain.class).getDataFolder() + "/logs.txt");

    public static void create(){
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void log(String message) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String time = sdf.format(cal.getTime());
        String msg = "[" + time + "] [EpicGuard/Logger] " + message;
        Bukkit.getConsoleSender().sendMessage(msg);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(msg);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

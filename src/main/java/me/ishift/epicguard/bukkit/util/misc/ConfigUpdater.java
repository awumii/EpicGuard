package me.ishift.epicguard.bukkit.util.misc;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigUpdater {
    public static void update() {
        final FileConfiguration config = GuardBukkit.getInstance().getConfig();
        final String version = config.getString("version");

        if (version != null && version.equals("3.6.1")) {
            try {
                final File configFile = new File(GuardBukkit.getInstance().getDataFolder() + "/config.yml");
                final Scanner scanner = new Scanner(configFile);
                final List<String> list = new ArrayList<>();

                if (scanner.hasNext()) {
                    while (scanner.hasNextLine()) {
                        list.add(scanner.nextLine());
                    }
                }

                scanner.close();
                list.add("");
                list.add("# Replaces default tab completion in /<tab>,");
                list.add("# with your custom list, provided below.");
                list.add("# WARN: You NEED ProtocolLib!");
                list.add("custom-tab-complete:");
                list.add("  enabled: false");
                list.add("  list:");
                list.add("    - /msg");
                list.add("    - /home");

                PrintWriter pw = new PrintWriter(configFile);
                pw.close();
                list.forEach(s -> {
                    if (s.equals("version: 3.6.1")) {
                        s = "version: " + GuardBukkit.getInstance().getDescription().getVersion();
                    }
                    Logger.writeToFile(configFile, s);
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

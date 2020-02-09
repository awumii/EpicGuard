package me.ishift.epicguard.bukkit.util.misc;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigUpdater {
    public static void update() {
        final FileConfiguration config = GuardBukkit.getInstance().getConfig();
        final String version = config.getString("version");

        if (version == null) {
            return;
        }
        if (version.equals("3.6.1")) {
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

                Logger.eraseFile(configFile);
                list.forEach(s -> {
                    if (s.equals("version: 3.6.1")) {
                        s = "version: 3.7.0";
                    }
                    Logger.writeToFile(configFile, s);
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (version.equals("3.7.0")) {
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
                list.add("# Enabling beta-layout will change design");
                list.add("# of actionbar & title on attack.");
                list.add("beta-layout: false");
                list.add("");
                list.add("# Toggle ability to bypass specific modules");
                list.add("# if player has permission for it.");
                list.add("bypass:");
                list.add("  # Permission: epicguard.bypass.allowed-commands");
                list.add("  allowed-commands: true");
                list.add("  # Permission: epicguard.bypass.custom-tab-complete");
                list.add("  custom-tab-complete: false");

                Logger.eraseFile(configFile);
                list.forEach(s -> {
                    if (s.equals("version: 3.7.0")) {
                        s = "version: 3.7.1";
                    }
                    if (s.equals("# Permission for bypass this module.")) {
                        return;
                    }
                    if (s.contains("epicguard.command.bypass")) {
                        return;
                    }
                    if (s.equals("# Everyone with the permission below can use any command.")) {
                        return;
                    }
                    Logger.writeToFile(configFile, s);
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

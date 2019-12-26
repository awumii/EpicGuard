package io.github.polskistevek.epicguard.bukkit.manager;

import io.github.polskistevek.epicguard.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import io.github.polskistevek.epicguard.bukkit.GuardBukkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFileManager {
    private static final String file = GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder() + "/data/data_flat.yml";
    private static YamlConfiguration configuration;
    public static int blockedBots = 0;
    public static int checkedConnections = 0;
    public static List<String> notificationUsers = new ArrayList<>();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void load() {
        File data = new File(file);
        if (!data.exists()) {
            try {
                data.createNewFile();
            } catch (Exception e) {
                Logger.throwException(e);
            }
        }
        configuration = YamlConfiguration.loadConfiguration(data);
        BlacklistManager.IP_BL = configuration.getStringList("blacklist");
        BlacklistManager.IP_WL = configuration.getStringList("whitelist");
        blockedBots = configuration.getInt("blocked-bots");
        blockedBots = configuration.getInt("checked-connections");
        AttackManager.rejoinData = configuration.getStringList("rejoin-data");
        notificationUsers = configuration.getStringList("notifications");
        notificationUsers.add("do_not_touch__");
    }

    public static FileConfiguration get() {
        return configuration;
    }

    public static void save() {
        try {
            get().set("blocked-bots", blockedBots);
            get().set("checked-connections", checkedConnections);
            get().set("rejoin-data", AttackManager.rejoinData);
            get().set("notifications", notificationUsers);
            configuration.save(file);
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}

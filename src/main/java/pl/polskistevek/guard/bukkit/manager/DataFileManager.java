package pl.polskistevek.guard.bukkit.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.polskistevek.guard.bukkit.GuardPluginBukkit;

import java.io.File;
import java.io.IOException;

public class DataFileManager {
    private static final String file = GuardPluginBukkit.getPlugin(GuardPluginBukkit.class).getDataFolder() + "/data/data_flat.yml";
    private static YamlConfiguration configuration;
    public static int blockedBots = 0;
    public static int checkedConnections = 0;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void load() {
        File data = new File(file);
        if (!data.exists()) {
            try {
                data.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(data);
        BlacklistManager.IP_BL = configuration.getStringList("blacklist");
        BlacklistManager.IP_WL = configuration.getStringList("whitelist");
        blockedBots = configuration.getInt("blocked-bots");
        blockedBots = configuration.getInt("checked-connections");
        AttackManager.rejoinData = configuration.getStringList("rejoin-data");
    }

    public static FileConfiguration get() {
        return configuration;
    }

    public static void save() {
        try {
            get().set("blocked-bots", blockedBots);
            get().set("checked-connections", checkedConnections);
            get().set("rejoin-data", AttackManager.rejoinData);
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

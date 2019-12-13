package pl.polskistevek.guard.bukkit.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.polskistevek.guard.bukkit.BukkitMain;

import java.io.File;
import java.io.IOException;

public class DataFileManager {
    private static final String file = BukkitMain.getPlugin(BukkitMain.class).getDataFolder() + "/data.yml";
    private static final String file1 = BukkitMain.getPlugin(BukkitMain.class).getDataFolder() + "/customLicense.yml";
    private static YamlConfiguration configuration;
    public static int blockedBots = 0;
    public static int checkedConnections = 0;
    public static String license = "";

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
        File customLicense = new File(file1);
        if (customLicense.exists()) {
            YamlConfiguration customLicenseData = YamlConfiguration.loadConfiguration(customLicense);
            license = customLicenseData.getString("licensedTo");
        }
        configuration = YamlConfiguration.loadConfiguration(data);
        BlacklistManager.IP_BL = configuration.getStringList("blacklist");
        BlacklistManager.IP_WL = configuration.getStringList("whitelist");
        blockedBots = configuration.getInt("blocked-bots");
        blockedBots = configuration.getInt("checked-connections");
    }

    public static FileConfiguration get() {
        return configuration;
    }

    public static void save() {
        try {
            get().set("blocked-bots", blockedBots);
            get().set("checked-connections", checkedConnections);
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

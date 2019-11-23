package pl.polskistevek.guard.bukkit.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.polskistevek.guard.bukkit.BukkitMain;
import java.io.File;
import java.io.IOException;

public class DataFileManager {
    private static final String file = BukkitMain.getPlugin(BukkitMain.class).getDataFolder() + "/data.yml";
    private static YamlConfiguration configuration;
    public static int blockedBots = 0;
    public static int checkedConnections = 0;

    public static void load(){
        File customYml = new File(file);
        if (!customYml.exists()){
            try {
                customYml.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(customYml);
        BlacklistManager.IP_BL = configuration.getStringList("blacklist");
        BlacklistManager.IP_WL = configuration.getStringList("whitelist");
        blockedBots = configuration.getInt("blocked-bots");
        blockedBots = configuration.getInt("checked-connections");
    }

    public static FileConfiguration get(){
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

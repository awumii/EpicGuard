package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataFileManager {
    public static int blockedBots = 0;
    public static int checkedConnections = 0;
    private static File file;
    private static FileConfiguration fileConfiguration;

    public static void init(String cfgFile) throws IOException {
        final File configurationFile = new File(cfgFile);
        if (!configurationFile.exists()) {
            configurationFile.createNewFile();
        }
        file = configurationFile;
        fileConfiguration = YamlConfiguration.loadConfiguration(configurationFile);
        load();
    }

    public static int getBlockedBots() {
        return blockedBots;
    }

    public static void setBlockedBots(int blockedBots) {
        DataFileManager.blockedBots = blockedBots;
    }

    public static int getCheckedConnections() {
        return checkedConnections;
    }

    public static void setCheckedConnections(int checkedConnections) {
        DataFileManager.checkedConnections = checkedConnections;
    }

    public static FileConfiguration getDataFile() {
        return fileConfiguration;
    }

    public static void save() {
        try {
            getDataFile().set("blocked-bots", getBlockedBots());
            getDataFile().set("checked-connections", getCheckedConnections());
            getDataFile().set("rejoin-data", AttackManager.getRejoinData());
            getDataFile().save(file);
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    public static void load() {
        BlacklistManager.blacklist = getDataFile().getStringList("blacklist");
        BlacklistManager.whitelist = getDataFile().getStringList("whitelist");
        setBlockedBots(getDataFile().getInt("blocked-bots"));
        setCheckedConnections(getDataFile().getInt("checked-connections"));
        AttackManager.setRejoinData(getDataFile().getStringList("rejoin-data"));
    }
}

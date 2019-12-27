package io.github.polskistevek.epicguard.utils;

import com.maxmind.geoip2.DatabaseReader;
import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.bungee.GuardBungee;

import java.io.File;
import java.io.IOException;

public class GeoAPI {
    private static DatabaseReader dbReader;

    public static DatabaseReader getDatabase() {
        return dbReader;
    }

    public static void create(ServerType serverType) {
        try {
            File dataFolder = null;
            String dbLocation;
            if (serverType == ServerType.SPIGOT) {
                dataFolder = GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder();
            }
            if (serverType == ServerType.BUNGEE) {
                dataFolder = GuardBungee.plugin.getDataFolder();
            }
            dbLocation = dataFolder + "/data/GeoLite2-Country.mmdb";
            if (!new File(dbLocation).exists()) {
                Logger.info("GeoLite2-Country.mmdb not found! Starting download...");
                Downloader.download(Downloader.MIRROR_GEO, dbLocation);
            }
            File database;
            database = new File(dbLocation);
            dbReader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            Logger.throwException(e);
        }
    }
}

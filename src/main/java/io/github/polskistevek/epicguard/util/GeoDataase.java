package io.github.polskistevek.epicguard.util;

import com.maxmind.geoip2.DatabaseReader;
import io.github.polskistevek.epicguard.GuardBukkit;
import io.github.polskistevek.epicguard.bungee.GuardBungee;

import java.io.File;
import java.io.IOException;

public class GeoDataase {
    private static DatabaseReader dbReader;
    private ServerType type;

    public static DatabaseReader getDatabase() {
        return dbReader;
    }

    public GeoDataase(ServerType type) {
        this.type = type;
        this.create();
    }

    private void create() {
        try {
            File dataFolder = null;
            String dbLocation;
            if (type == ServerType.SPIGOT) {
                dataFolder = GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder();
            }
            if (type == ServerType.BUNGEE) {
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

package pl.polskistevek.guard.utils;

import com.maxmind.geoip2.DatabaseReader;
import pl.polskistevek.guard.bukkit.GuardPluginBukkit;
import pl.polskistevek.guard.bungee.GuardPluginBungee;

import java.io.File;
import java.io.IOException;

public class GeoAPI {
    private static DatabaseReader dbReader;
    private final ServerType serverType;

    public GeoAPI(ServerType serverType){
        this.serverType = serverType;
        this.register();
    }

    public static DatabaseReader getDatabase() {
        return dbReader;
    }

    private void register() {
        try {
            File dataFolder = null;
            String dbLocation;
            if (this.serverType == ServerType.SPIGOT) {
                dataFolder = GuardPluginBukkit.getPlugin(GuardPluginBukkit.class).getDataFolder();
            }
            if (this.serverType == ServerType.BUNGEE) {
                dataFolder = GuardPluginBungee.plugin.getDataFolder();
            }
            dbLocation = dataFolder + "/data/GeoLite2-Country.mmdb";
            if (!new File(dbLocation).exists()) {
                Logger.info("GeoIP Database not found! Starting download...", false);
                //I need to download it from external site (my cloud) because official site has only tar.zip packed version (plugin don't need to extract it)
                Downloader.download(Mirrors.MIRROR_GEO, dbLocation);
            }
            File database;
            database = new File(dbLocation);
            dbReader = new DatabaseReader.Builder(database).build();
            Logger.info("GeoIP Database succesfully loaded.", false);
        } catch (IOException e) {
            Logger.error(e);
        }
    }
}

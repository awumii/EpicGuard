package pl.polskistevek.guard.utils;

import com.maxmind.geoip2.DatabaseReader;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bungee.BungeeMain;

import java.io.File;
import java.io.IOException;

public class GEO {
    public static DatabaseReader dbReader;
    public static boolean spigot = false;

    public static void registerDatabase(ServerType type) throws IOException {
        File dataFolder = null;
        String dbLocation;
        if (type == ServerType.SPIGOT) {
            dataFolder = BukkitMain.getPlugin(BukkitMain.class).getDataFolder();
        }
        if (type == ServerType.BUNGEE) {
            dataFolder = BungeeMain.plugin.getDataFolder();
        }
        dbLocation = dataFolder + "/GeoLite2-Country.mmdb";
        if (!new File(dbLocation).exists()) {
            if (spigot) {
                Logger.info("GeoIP Database not found! Starting download...", false);
            }
            //I need to download it from external site (my cloud) because official site has only tar.zip packed version (plugin don't need to extract it)
            Downloader.download("http://epicmc.cba.pl/cloud/uploads/GeoLite2-Country.mmdb", dbLocation);
        }
        File database;
        database = new File(dbLocation);
        dbReader = new DatabaseReader.Builder(database).build();
        Logger.info("GeoIP Database succesfully loaded.", false);
    }
}

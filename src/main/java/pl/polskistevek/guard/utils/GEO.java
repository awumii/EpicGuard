package pl.polskistevek.guard.utils;

import com.maxmind.geoip2.DatabaseReader;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bungee.BungeeMain;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class GEO {
    public static DatabaseReader dbReader;
    public static boolean spigot = false;

    public static void registerDatabase() throws IOException {
        File dataFolder;
        String dbLocation;
        if (spigot) {
            dataFolder = BukkitMain.getPlugin(BukkitMain.class).getDataFolder();
        } else {
            dataFolder = BungeeMain.plugin.getDataFolder();
        }
        dbLocation = dataFolder + "/GeoLite2-Country.mmdb";
        if(!new File(dbLocation).exists()){
            if (spigot) {
                Logger.log("GeoIP Database not found! Starting download...");
            }
            URL url = new URL("http://infinity-cloud.cba.pl/api/GeoLite2-Country.mmdb");
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(dbLocation);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        File database;
        database = new File(dbLocation);
        try {
            dbReader = new DatabaseReader.Builder(database).build();
            if (spigot) {
                Logger.log("GeoIP Database succesfully loaded.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

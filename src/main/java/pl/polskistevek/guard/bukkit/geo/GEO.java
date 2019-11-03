package pl.polskistevek.guard.bukkit.geo;

import com.maxmind.geoip2.DatabaseReader;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.Logger;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class GEO {
    private static File dataFolder = BukkitMain.getPlugin(BukkitMain.class).getDataFolder();
    private static String dbLocation = dataFolder + "/GeoLite2-Country.mmdb";
    public static DatabaseReader dbReader;

    public static void registerDatabase() throws IOException {
        if(!new File(dbLocation).exists()){
            Logger.log("GeoIP Database not found! Starting download...");
            URL url = new URL("http://infinity-cloud.cba.pl/api/GeoLite2-Country.mmdb");
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(dbLocation);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        File database = new File(dbLocation);
        try {
            dbReader = new DatabaseReader.Builder(database).build();
            Logger.log("GeoIP Database succesfully loaded.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

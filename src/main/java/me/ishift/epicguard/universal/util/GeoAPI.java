package me.ishift.epicguard.universal.util;

import com.maxmind.geoip2.DatabaseReader;
import me.ishift.epicguard.universal.Logger;
import me.ishift.epicguard.universal.cloud.Downloader;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class GeoAPI {
    private static DatabaseReader dbReader;

    private static DatabaseReader getDatabase() {
        return dbReader;
    }

    public static String getCountryCode(InetAddress address) {
        if (!address.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
            try {
                return getDatabase().country(address).getCountry().getIsoCode();
            } catch (Exception e) {
                Logger.debug("Country for IP: " + address.getHostAddress() + " has been not found!");
                return "Unknown?";
            }
        }
        return "Unknown?";
    }

    public static void create() {
        try {
            Logger.info("This product includes GeoLite2 data created by MaxMind, available from www.maxmind.com");
            Logger.info("By using this software, you agree to GeoLite2 EULA (https://www.maxmind.com/en/geolite2/eula)");
            final String dataFolder = "plugins/EpicGuard";
            final String dbLocation = dataFolder + "/data/GeoLite2-Country.mmdb";
            final File dateFile = new File(dataFolder + "/data/" + "last_db_download.txt");

            if (!new File(dbLocation).exists() || isOutdated(dateFile)) {
                Logger.info("Databse is outdated or not found, update is required");
                Logger.info("Downloading GEO Database... This may take some time.");
                Downloader.download(Downloader.MIRROR_GEO, dbLocation);
                Logger.eraseFile(dateFile);
                Logger.writeToFile(dateFile, String.valueOf(System.currentTimeMillis()));
            }
            final File database = new File(dbLocation);
            dbReader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isOutdated(File dateFile) throws IOException {
        if (dateFile.createNewFile()) {
            return true;
        }
        final Scanner scanner = new Scanner(dateFile);
        final long timeOld = Long.parseLong(scanner.next());
        // 604 800 000 = 1 week
        return (System.currentTimeMillis() - timeOld) > 604800000;
    }
}

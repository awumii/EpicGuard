package me.ishift.epicguard.universal;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import me.ishift.epicguard.universal.cloud.Downloader;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GeoAPI {
    private static DatabaseReader dbReader;

    public static InetAddress getAddress(String hostname) {
        try {
            return InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            Logger.info("[UnknownHostException] Can't resolve InetAddress from hostname: " + hostname);
        }
        return null;
    }

    public static String getCountryCode(String host) {
        final InetAddress address = getAddress(host);

        if (address != null) {
            if (!address.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                try {
                    return dbReader.country(address).getCountry().getIsoCode();
                } catch (IOException | GeoIp2Exception e) {
                    Logger.info("[GeoIp2Exception/IOException] Can't find country for address: " + host);
                }
            }
        }
        return "Unknown?";
    }

    public static void init() {
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
            dbReader = new DatabaseReader.Builder(database).withCache(new CHMCache()).build();
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
        return (System.currentTimeMillis() - timeOld) > 604800000;
    }
}

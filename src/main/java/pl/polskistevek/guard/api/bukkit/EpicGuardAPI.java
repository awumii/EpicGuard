package pl.polskistevek.guard.api.bukkit;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.utils.GEO;

import java.io.IOException;
import java.net.InetAddress;

public class EpicGuardAPI {
    public static String getVersion() {
        return BukkitMain.getPlugin(BukkitMain.class).getDescription().getVersion();
    }

    public static int getBlockedBots() {
        return DataFileManager.blockedBots;
    }

    public static int getCheckedConnections() {
        return DataFileManager.checkedConnections;
    }

    public static DatabaseReader getGeoDatabase() {
        return GEO.dbReader;
    }

    public static String getCountryCode(InetAddress address) {
        try {
            return GEO.dbReader.country(address).getCountry().getIsoCode();
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

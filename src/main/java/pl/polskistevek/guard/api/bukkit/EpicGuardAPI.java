package pl.polskistevek.guard.api.bukkit;

import com.maxmind.geoip2.DatabaseReader;
import org.bukkit.entity.Player;
import pl.polskistevek.guard.bukkit.GuardPluginBukkit;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.bukkit.manager.UserManager;
import pl.polskistevek.guard.bukkit.object.User;
import pl.polskistevek.guard.utils.GeoAPI;
import pl.polskistevek.guard.utils.Logger;

import java.net.InetAddress;

public class EpicGuardAPI {
    public static String getVersion() {
        return GuardPluginBukkit.getPlugin(GuardPluginBukkit.class).getDescription().getVersion();
    }

    public static int getBlockedBots() {
        return DataFileManager.blockedBots;
    }

    public static int getCheckedConnections() {
        return DataFileManager.checkedConnections;
    }

    public static User getUser(Player player){
        return UserManager.getUser(player);
    }

    public static DatabaseReader getGeoDatabase() {
        return GeoAPI.getDatabase();
    }

    public static String getCountryCode(InetAddress address) {
        try {
            return GeoAPI.getDatabase().country(address).getCountry().getIsoCode();
        } catch (Exception e) {
            Logger.error(e);
        }
        return null;
    }
}

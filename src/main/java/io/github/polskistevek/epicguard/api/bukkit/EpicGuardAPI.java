package io.github.polskistevek.epicguard.api.bukkit;

import com.maxmind.geoip2.DatabaseReader;
import io.github.polskistevek.epicguard.bukkit.GuardPluginBukkit;
import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import io.github.polskistevek.epicguard.bukkit.manager.UserManager;
import io.github.polskistevek.epicguard.bukkit.object.User;
import io.github.polskistevek.epicguard.utils.GeoAPI;
import io.github.polskistevek.epicguard.utils.Logger;
import org.bukkit.entity.Player;

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

package io.github.polskistevek.epicguard.bungee.listener;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import io.github.polskistevek.epicguard.bungee.GuardBungee;
import io.github.polskistevek.epicguard.bungee.util.PendingConnectionCloser;
import io.github.polskistevek.epicguard.bungee.util.FirewallManager;
import io.github.polskistevek.epicguard.utils.GeoAPI;
import io.github.polskistevek.epicguard.utils.KickReason;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ProxyPreLoginListener implements Listener {
    public static int cps = 0;
    public static boolean attack = false;
    public static int cps_ping = 0;

    @EventHandler
    public void onPreLogin(PreLoginEvent e) {
        if (e.isCancelled()) {
            return;
        }
        PendingConnection c = e.getConnection();
        String adress = c.getAddress().getAddress().getHostAddress();
        String p = c.getName();
        if (!GuardBungee.COUNTRY_MODE.equals("DISABLED")) {
            String country = null;
            try {
                country = GeoAPI.getDatabase().country(c.getAddress().getAddress()).getCountry().getIsoCode();
            } catch (IOException | GeoIp2Exception ex) {
                ex.printStackTrace();
            }
            if (GuardBungee.COUNTRY_MODE.equals("WHITELIST")) {
                if (!GuardBungee.COUNTRIES.contains(country)) {
                    cps++;
                    PendingConnectionCloser.close(c, KickReason.GEO);
                    remove(0);
                    return;
                }
            }
            if (GuardBungee.COUNTRY_MODE.equals("BLACKLIST")) {
                if (!GuardBungee.COUNTRIES.contains(country)) {
                    cps++;
                    PendingConnectionCloser.close(c, KickReason.GEO);
                    remove(0);
                    return;
                }
            }
            if (!GuardBungee.ANTIBOT) {
                return;
            }
            if (attack) {
                cps++;
                PendingConnectionCloser.close(c, KickReason.ATTACK);
                remove(0);
            }
            if (cps > GuardBungee.CPS_ACTIVATE) {
                cps++;
                PendingConnectionCloser.close(c, KickReason.ATTACK);
                remove(0);
                return;
            }

            String url1 = GuardBungee.ANTIBOT_QUERY_1.replace("{IP}", adress);
            String url2 = GuardBungee.ANTIBOT_QUERY_2.replace("{IP}", adress);
            String url3 = GuardBungee.ANTIBOT_QUERY_3.replace("{IP}", adress);

            if (checkUrl(url1)) {
                PendingConnectionCloser.close(c, KickReason.PROXY);
                FirewallManager.blacklist(adress);
                return;
            }
            if (checkUrl(url2)) {
                PendingConnectionCloser.close(c, KickReason.PROXY);
                FirewallManager.blacklist(adress);
                return;
            }
            if (checkUrl(url3)) {
                PendingConnectionCloser.close(c, KickReason.PROXY);
                FirewallManager.blacklist(adress);
            }
        }
    }

    public static void remove(int i) {
        ProxyServer.getInstance().getScheduler().schedule(GuardBungee.plugin, () -> {
            if (i == 0) {
                cps--;
                return;
            }
            cps_ping--;
        }, 1, TimeUnit.SECONDS);
    }

    private static boolean checkUrl(String url) {
        try {
            final Scanner s = new Scanner(new URL(url).openStream());
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (GuardBungee.ANTIBOT_QUERY_CONTAINS.contains(s.next())) {
                        return true;
                    }
                }
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}

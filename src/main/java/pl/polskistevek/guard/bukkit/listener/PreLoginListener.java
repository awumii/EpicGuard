package pl.polskistevek.guard.bukkit.listener;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.closer.ConnectionCloser;
import pl.polskistevek.guard.utils.KickReason;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.bukkit.util.MessagesBukkit;
import pl.polskistevek.guard.utils.GEO;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.util.Notificator;
import pl.polskistevek.guard.utils.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class PreLoginListener implements Listener {
    public static int cps = 0;
    private static int blocked = 0;
    public static boolean attack = false;

    /*
    This code is really messy.
    I will need to remake it soon.
     */

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) throws IOException, GeoIp2Exception {
        String adress = e.getAddress().getHostAddress();
        String p = e.getName();
        if (Bukkit.hasWhitelist()){
            return;
        }
        DataFileManager.checkedConnections++;
        if (!BukkitMain.COUNTRY_MODE.equals("DISABLED")){
            String country = GEO.dbReader.country(e.getAddress()).getCountry().getIsoCode();
            if (BukkitMain.COUNTRY_MODE.equals("WHITELIST")){
                if (BukkitMain.COUNTRIES.contains(country)){
                    Logger.log("# GEO Check - Passed", true);
                } else {
                    cps++;
                    blocked++;
                    DataFileManager.blockedBots++;
                    title();
                    Logger.log("# GEO Check - FAILED", true);
                    ConnectionCloser.close(e, KickReason.GEO);
                    failed("GEO Check", p, adress);
                    remove(0);
                    return;
                }
            }
            if (BukkitMain.COUNTRY_MODE.equals("BLACKLIST")){
                if (!BukkitMain.COUNTRIES.contains(country)){
                    cps++;
                    blocked++;
                    DataFileManager.blockedBots++;
                    title();
                    Logger.log("# GEO Check - FAILED", true);
                    ConnectionCloser.close(e, KickReason.GEO);
                    failed("GEO Check", p, adress);
                    remove(0);
                    return;
                } else {
                    Logger.log("# GEO Check - Passed", true);
                }
            }
        }
        if (!BukkitMain.ANTIBOT){
            return;
        }
        if (cps > BukkitMain.CPS_ACTIVATE){
            attack = true;
        }
        String url1 = BukkitMain.ANTIBOT_QUERY_1.replace("{IP}", adress);
        String url2 = BukkitMain.ANTIBOT_QUERY_2.replace("{IP}", adress);
        String url3 = BukkitMain.ANTIBOT_QUERY_3.replace("{IP}", adress);
        Logger.log("###  CHECKING PLAYER " + p + " [" + adress + "]  ###", true);
        if (BlacklistManager.checkWhitelist(adress)){
            Logger.log("# Whitelist Check - Passed", true);
            return;
        }
        if (BlacklistManager.check(adress)){
            blocked++;
            cps++;
            DataFileManager.blockedBots++;
            title();
            Logger.log("# Blacklist Check - FAILED", true);
            ConnectionCloser.close(e, KickReason.BLACKLIST);
            failed("Blacklist Check", p, adress);
            remove(0);
            return;
        }
        if (attack){
            cps++;
            blocked++;
            DataFileManager.blockedBots++;
            title();
            Logger.log("# Attack Mode Check - FAILED", true);
            ConnectionCloser.close(e, KickReason.ATTACK);
            failed("Attack Mode", p, adress);
            remove(0);
            return;
        }
        if (checkUrl(url1)){
            ConnectionCloser.close(e, KickReason.PROXY);
            detected(adress, p);
            return;
        }
        if (checkUrl(url2)){
            ConnectionCloser.close(e, KickReason.PROXY);
            detected(adress, p);
            return;
        }
        if (checkUrl(url3)){
            ConnectionCloser.close(e, KickReason.PROXY);
            detected(adress, p);
            return;
        }
        Logger.log("###  PLAYER " + p + " [" + adress + "]  PASSED EVERY CHECK ###", true);
    }

    private static void failed(String reason, String p, String adress){
        Notificator.action(MessagesBukkit.ACTIONBAR_ATTACK.replace("{NICK}", p).replace("{IP}", adress).replace("{DETECTION}", reason).replace("{CPS}", String.valueOf(cps)));
    }

    private static void detected(String adress, String p){
        Logger.log("# Proxy Check - FAILED", true);
        BlacklistManager.add(adress);
        blocked++;
        cps++;
        DataFileManager.blockedBots++;
        title();
        failed("Proxy Check", p, adress);
        remove(0);
    }

    private static void title(){
        if (cps > BukkitMain.CPS_MIN) {
            Notificator.title(MessagesBukkit.ATTACK_TITLE.replace("{MAX}", String.valueOf(blocked)), MessagesBukkit.ATTACK_SUBTITLE.replace("{CPS}", String.valueOf(cps)));
        }
    }

    static void remove(int type){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (type == 0) {
                    cps--;
                    return;
                }
                if (type == 1) {
                    ServerListPingListener.cps_ping--;
                    return;
                }
                if (type == 2) {
                    PlayerJoinListener.jps--;
                }
            }
        }.runTaskLater(BukkitMain.getPlugin(BukkitMain.class), 20);
    }

    private static boolean checkUrl(String url) {
        try {
            final Scanner s = new Scanner(new URL(url).openStream());
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (BukkitMain.ANTIBOT_QUERY_CONTAINS.contains(s.next())) {
                        return true;
                    }
                }
                return false;
            }
        } catch (IOException e) {
            Logger.log("[RATE LIMIT] Website returned code 403, it may be down, or rate limited. URL: " + url, true);
            return false;
        }
        return false;
    }
}

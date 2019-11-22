package pl.polskistevek.guard.bukkit.listener;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.manager.MessageFileManager;
import pl.polskistevek.guard.utils.GEO;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.utils.Notificator;
import pl.polskistevek.guard.utils.ChatUtil;
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
            title();
            Logger.log("# Blacklist Check - FAILED", true);
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(MessageFileManager.MESSAGE_KICK_BLACKLIST));
            failed("Blacklist Check", p, adress);
            rem(0);
            return;
        }
        if (attack){
            cps++;
            blocked++;
            title();
            Logger.log("# Attack Mode Check - FAILED", true);
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(MessageFileManager.MESSAGE_KICK_ATTACK));
            failed("Attack Mode", p, adress);
            rem(0);
            return;
        }
        if (!BukkitMain.COUNTRY_MODE.equals("DISABLED")){
            String country = GEO.dbReader.country(e.getAddress()).getCountry().getIsoCode();
            if (BukkitMain.COUNTRY_MODE.equals("WHITELIST")){
                if (BukkitMain.COUNTRIES.contains(country)){
                    Logger.log("# Country Check - Passed", true);
                } else {
                    cps++;
                    blocked++;
                    title();
                    Logger.log("# Country Check - FAILED", true);
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(MessageFileManager.MESSAGE_KICK_COUNTRY));
                    failed("Country Check", p, adress);
                    rem(0);
                    return;
                }
            }
            if (BukkitMain.COUNTRY_MODE.equals("BLACKLIST")){
                if (!BukkitMain.COUNTRIES.contains(country)){
                    cps++;
                    blocked++;
                    title();
                    Logger.log("# Country Check - FAILED", true);
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(MessageFileManager.MESSAGE_KICK_COUNTRY));
                    failed("Country Check", p, adress);
                    rem(0);
                    return;
                } else {
                    Logger.log("# Country Check - Passed", true);
                }
            }
        }
        if (checkUrl(url1)){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(ChatUtil.fix(MessageFileManager.MESSAGE_KICK_PROXY)));
            detected(adress, p);
            return;
        }
        if (checkUrl(url2)){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(MessageFileManager.MESSAGE_KICK_PROXY));
            detected(adress, p);
            return;
        }
        if (checkUrl(url3)){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(MessageFileManager.MESSAGE_KICK_PROXY));
            detected(adress, p);
            return;
        }
        Logger.log("###  PLAYER " + p + " [" + adress + "]  PASSED EVERY CHECK ###", true);
    }

    private static void failed(String reason, String p, String adress){
        Notificator.action(MessageFileManager.ACTIONBAR_ATTACK.replace("{NICK}", p).replace("{IP}", adress).replace("{DETECTION}", reason).replace("{CPS}", String.valueOf(cps)));
    }

    private static void detected(String adress, String p){
        Logger.log("# Proxy Check - FAILED", true);
        BlacklistManager.add(adress);
        blocked++;
        cps++;
        title();
        failed("Proxy Check", p, adress);
        rem(0);
    }

    private static void title(){
        if (cps > BukkitMain.CPS_MIN) {
            Notificator.title(MessageFileManager.ATTACK_TITLE.replace("{MAX}", String.valueOf(blocked)), MessageFileManager.ATTACK_SUBTITLE.replace("{CPS}", String.valueOf(cps)));
        }
    }

    static void rem(int type){
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

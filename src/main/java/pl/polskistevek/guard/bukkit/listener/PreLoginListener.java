package pl.polskistevek.guard.bukkit.listener;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.GEO;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.manager.Notificator;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.bukkit.utils.ExactTPS;
import pl.polskistevek.guard.utils.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class PreLoginListener implements Listener {
    public static int cps = 0;
    private static int blocked = 0;
    public static boolean attack = false;

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) throws IOException, GeoIp2Exception {
        String adress = e.getAddress().getHostAddress();
        String p = e.getName();
        if (Bukkit.hasWhitelist()){
            return;
        }
        if (!BukkitMain.WHITELIST_MESSAGE.equals("")) {
            if (Bukkit.hasWhitelist()) {
                if (!Bukkit.getOfflinePlayer(e.getUniqueId()).isWhitelisted()) {
                    Bukkit.broadcast(ChatUtil.fix(BukkitMain.PREFIX + BukkitMain.WHITELIST_MESSAGE).replace("{NICK}", p), BukkitMain.PERMISSION);
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
        /*if (BukkitMain.API){
            if (dev.jaqobb.bot_sentry_spigot.api.BotSentryAPI.isWhitelisted(adress)){
                passed(p, "Whitelist Check (External)");
                Logger.log(adress + " (" + p + ") connected -> External Whitelist check [✔]");
                if (!BlacklistManager.checkWhitelist(adress)){
                    BlacklistManager.addWhitelist(adress);
                }
                return;
            }
            if (dev.jaqobb.bot_sentry_spigot.api.BotSentryAPI.isAntiBotEnabled()){
                blocked++;
                cps++;
                title();
                Logger.log(adress + " (" + p + ") detected for ---> ATTACK_LEGACY [✖]");
                failed("Attack Legacy", p, adress);
                rem(0);
                return;
            }
        }*/
        if (BlacklistManager.checkWhitelist(adress)){
            Logger.log(adress + " (" + p + ") connected -> Internal whitelist check [✔]");
            passed(p, "Whitelist Check (Internal)");
            return;
        }
        if (BlacklistManager.check(adress)){
            blocked++;
            cps++;
            title();
            Logger.log(adress + " (" + p + ") detected for ---> BLACKLIST_CHECK [✖]");
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(BukkitMain.MESSAGE_KICK_BLACKLIST));
            failed("Blacklist Check", p, adress);
            rem(0);
            return;
        }
        if (attack){
            cps++;
            blocked++;
            title();
            Logger.log(adress + " (" + p + ") detected for ---> ATTACK_MODE [✖]");
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(BukkitMain.MESSAGE_KICK_ATTACK));
            failed("Protection Mode", p, adress);
            rem(0);
            return;
        }
        if (!BukkitMain.COUNTRY_MODE.equals("DISABLED")){
            String country = GEO.dbReader.country(e.getAddress()).getCountry().getIsoCode();
            if (BukkitMain.COUNTRY_MODE.equals("WHITELIST")){
                if (BukkitMain.COUNTRIES.contains(country)){
                    Logger.log(adress + " (" + p + ") passed COUNTRY_WHITELIST check, but need to go for proxy check...");
                } else {
                    cps++;
                    blocked++;
                    title();
                    Logger.log(adress + " (" + p + ") detected for ---> COUNTRY_WHITELIST [✖]");
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(BukkitMain.MESSAGE_KICK_COUNTRY));
                    failed("Country Whitelist", p, adress);
                    rem(0);
                    return;
                }
            }
            if (BukkitMain.COUNTRY_MODE.equals("BLACKLIST")){
                if (!BukkitMain.COUNTRIES.contains(country)){
                    cps++;
                    blocked++;
                    title();
                    Logger.log(adress + " (" + p + ") detected for ---> COUNTRY_BLACKLIST [✖]");
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(BukkitMain.MESSAGE_KICK_COUNTRY));
                    failed("Country Blacklist", p, adress);
                    rem(0);
                    return;
                } else {
                    Logger.log(adress + " (" + p + ") passed COUNTRY_BLACKLIST check, but need to go for proxy check...");
                }
            }
        }
        if (checkUrl(url1)){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(ChatUtil.fix(BukkitMain.MESSAGE_KICK_PROXY)));
            detected(adress, p);
            return;
        }
        if (checkUrl(url2)){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(BukkitMain.MESSAGE_KICK_PROXY));
            detected(adress, p);
            return;
        }
        if (checkUrl(url3)){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fix(BukkitMain.MESSAGE_KICK_PROXY));
            detected(adress, p);
            return;
        }
        Logger.log(adress + " (" + p + ") connected -> Passed Checks [✔]");
        passed(p, "Safe Player");
    }

    private static void failed(String reason, String p, String adress){
        Notificator.action(BukkitMain.ACTION_BOT.replace("{NICK}", p).replace("{IP}", adress).replace("{DET}", reason).replace("{CPS}", String.valueOf(cps)).replace("{TPS}", String.valueOf(ExactTPS.getTPS2())));
    }

    private static void detected(String adress, String p){
        Logger.log(adress + " (" + p + ") detected for ---> PROXY_VPN [✖]");
        BlacklistManager.add(adress);
        blocked++;
        cps++;
        title();
        failed("Proxy / VPN", p, adress);
        rem(0);
    }

    private static void title(){
        if (cps > BukkitMain.CPS_MIN) {
            Notificator.title(BukkitMain.ATTACK_TITLE.replace("{MAX}", String.valueOf(blocked)), BukkitMain.ATTACK_SUBTITLE.replace("{CPS}", String.valueOf(cps)));
        }
    }

    private static void passed(String a, String b){
        Notificator.action(BukkitMain.PASSED_ACTION.replace("{NICK}", a).replace("{DET}", b));
    }

    public static void rem(int type){
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
            Logger.log("[RATE LIMIT] Website returned code 403, it may be down, or rate limited. URL: " + url);
            return false;
        }
        return false;
    }
}

package pl.polskistevek.guard.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.manager.Notificator;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.utils.ExactTPS;
import pl.polskistevek.guard.utils.Updater;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class AntiBotListener implements Listener {
    public static int cps = 0;
    private static int blocked = 0;
    private static boolean attack = false;

    @EventHandler
    public void updateCheckOnJoin(PlayerJoinEvent e){
        Updater.notify(e.getPlayer());
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e){
        String adress = e.getAddress().getHostAddress();
        String url = BukkitMain.ANTIBOT_QUERY.replace("{IP}", adress);
        String p = e.getName();
        if (BukkitMain.WHITELIST_MESSAGE != "") {
            if (Bukkit.hasWhitelist()) {
                if (Bukkit.getPlayer(p) != null && !Bukkit.getPlayer(p).isOp()) {
                    Bukkit.broadcast(BukkitMain.WHITELIST_MESSAGE.replace("{NICK}", p), BukkitMain.PERMISSION);
                }
            }
        }
        if (BukkitMain.API){
            if (dev.jaqobb.bot_sentry_spigot.api.BotSentryAPI.isWhitelisted(adress)){
                passed(p, "Whitelist Check (External)");
                if (!BlacklistManager.checkWhitelist(adress)){
                    BlacklistManager.addWhitelist(adress);
                }
                return;
            }
            if (dev.jaqobb.bot_sentry_spigot.api.BotSentryAPI.isAntiBotEnabled()){
                blocked++;
                cps++;
                title();
                Notificator.action(BukkitMain.ACTION_BOT.replace("{NICK}", p).replace("{IP}", adress).replace("{DET}", "Protection Enabled").replace("{CPS}", String.valueOf(cps)).replace("{TPS}", String.valueOf(ExactTPS.getTPS2())));
                rem();
                return;
            }
        }
        if (BlacklistManager.checkWhitelist(adress)){
            passed(p, "Whitelist Check (Internal)");
            return;
        }
        if (BlacklistManager.check(adress)){
            blocked++;
            cps++;
            title();
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, BukkitMain.MESSAGE_KICK_BLACKLIST);
            Notificator.action(BukkitMain.ACTION_BOT.replace("{NICK}", p).replace("{IP}", adress).replace("{DET}", "Blacklist Detection").replace("{CPS}", String.valueOf(cps)).replace("{TPS}", String.valueOf(ExactTPS.getTPS2())));
            rem();
            return;
        }
        if (cps > BukkitMain.CPS_ACTIVATE){
            cps++;
            blocked++;
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, BukkitMain.MESSAGE_KICK_ATTACK);
            rem();
            return;
        }
        if (lookup(url)){
            BlacklistManager.add(adress);
            blocked++;
            cps++;
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, BukkitMain.MESSAGE_KICK_PROXY);
            title();
            Notificator.action(BukkitMain.ACTION_BOT.replace("{NICK}", p).replace("{IP}", adress).replace("{DET}", "Proxy/VPN Detection").replace("{CPS}", String.valueOf(cps)).replace("{TPS}", String.valueOf(ExactTPS.getTPS2())));
            rem();
            return;
        }
        BlacklistManager.addWhitelist(adress);
        passed(p, "Safe Player");
    }

    private static void title(){
        if (cps > BukkitMain.CPS_MIN) {
            Notificator.title(BukkitMain.ATTACK_TITLE.replace("{MAX}", String.valueOf(blocked)), BukkitMain.ATTACK_SUBTITLE.replace("{CPS}", String.valueOf(cps)));
        }
    }

    private static void passed(String a, String b){
        Notificator.action(BukkitMain.PASSED_ACTION.replace("{NICK}", a).replace("{DET}", b));
    }

    private static void rem(){
        new BukkitRunnable() {
            @Override
            public void run() {
                cps--;
            }
        }.runTaskLater(BukkitMain.getPlugin(BukkitMain.class), 20);
    }

    private static boolean lookup(String url) {
        try {
            final Scanner s = new Scanner(new URL(url).openStream());
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (s.next().contains(BukkitMain.ANTIBOT_QUERY_CONTAINS)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}

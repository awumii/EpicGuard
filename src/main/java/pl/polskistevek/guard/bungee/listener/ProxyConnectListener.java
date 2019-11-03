package pl.polskistevek.guard.bungee.listener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.polskistevek.guard.bungee.BungeeMain;
import pl.polskistevek.guard.bungee.util.ConnectionCloser;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ProxyConnectListener implements Listener {
    public static int cps = 0;
    private static int blocked = 0;
    private static boolean attack = false;

    @EventHandler
    public void onJoin(PreLoginEvent e){
        String adress = e.getConnection().getAddress().getHostName();
        String url = BungeeMain.ANTIBOT_QUERY.replace("{IP}", adress);
        String p = e.getConnection().getName();
        if (BungeeMain.API){
            if (dev.jaqobb.bot_sentry_bungee.api.BotSentryAPI.isWhitelisted(adress)){
                return;
            }
            if (dev.jaqobb.bot_sentry_bungee.api.BotSentryAPI.isAntiBotEnabled()){
                blocked++;
                cps++;
                rem();
                ConnectionCloser.closeAttack(e.getConnection());
                return;
            }
        }
        if (cps > BungeeMain.CPS_ACTIVATE){
            cps++;
            blocked++;
            ConnectionCloser.closeAttack(e.getConnection());
            rem();
            return;
        }
        if (checkUrl(url)){
            blocked++;
            cps++;
            ConnectionCloser.close(e.getConnection());
            rem();
        }
    }

    private static void rem(){
        ProxyServer.getInstance().getScheduler().schedule(BungeeMain.plugin, new Runnable() {
            public void run() {
                cps--;
            }
        }, 1, TimeUnit.SECONDS);
    }

    private static boolean checkUrl(String url) {
        try {
            final Scanner s = new Scanner(new URL(url).openStream());
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (s.next().contains(BungeeMain.ANTIBOT_QUERY_CONTAINS)) {
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

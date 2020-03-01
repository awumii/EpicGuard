package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.Logger;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.AttackSpeed;
import me.ishift.epicguard.universal.check.*;
import me.ishift.epicguard.universal.types.AttackType;
import me.ishift.epicguard.universal.types.Reason;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class ProxyPreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        final PendingConnection connection = event.getConnection();
        final String address = connection.getAddress().getAddress().getHostAddress();
        final String name = connection.getName();

        AttackSpeed.increase(AttackType.CONNECT);
        ProxyServer.getInstance().getScheduler().schedule(GuardBungee.getInstance(), () -> AttackSpeed.decrease(AttackType.CONNECT), 1, TimeUnit.SECONDS);

        if (StorageManager.isWhitelisted(address)) {
            return;
        }

        if (BlacklistCheck.perform(address)) {
            handleDetection(address, connection, Reason.BLACKLIST, false);
            return;
        }

        if (NameContainsCheck.perform(name)) {
            handleDetection(address, connection, Reason.NAMECONTAINS, true);
            return;
        }

        if (GeoCheck.perform(address)) {
            handleDetection(address, connection, Reason.GEO, true);
            return;
        }

        if (ServerListCheck.perform(address)) {
            handleDetection(address, connection, Reason.SERVERLIST, false);
            return;
        }

        if (VerifyCheck.perform(name)) {
            handleDetection(address, connection, Reason.VERIFY, false);
            return;
        }

        if (ProxyCheck.perform(address)) {
            handleDetection(address, connection, Reason.PROXY, true);
        }
    }

    public static void handleDetection(String address, PendingConnection connection, Reason reason, boolean blacklist) {
        connection.disconnect(new TextComponent(reason.getReason()));
        if (GuardBungee.log) {
            Logger.info("Closing: " + address + "(" + connection.getName() + "), (" + reason + ")]");
        }

        if (blacklist) StorageManager.blacklist(address);
        AttackSpeed.setTotalBots(AttackSpeed.getTotalBots() + 1);
    }
}

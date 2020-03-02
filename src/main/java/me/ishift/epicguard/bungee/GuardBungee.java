package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.command.GuardCommand;
import me.ishift.epicguard.bungee.listener.ProxyPingListener;
import me.ishift.epicguard.bungee.listener.ProxyPreLoginListener;
import me.ishift.epicguard.bungee.util.BungeeMetrics;
import me.ishift.epicguard.universal.*;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class GuardBungee extends Plugin {
    public static boolean log = false;
    public static boolean status = false;
    private static GuardBungee instance;

    public static GuardBungee getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        new File("plugins/EpicGuard").mkdir();
        final File file = new File(getDataFolder(), "config_bungee.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config_bungee.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Config.loadBungee();
        StorageManager.load();
        Messages.load();
        Logger.init();

        GeoAPI.init();

        this.getProxy().getPluginManager().registerListener(this, new ProxyPreLoginListener());
        this.getProxy().getPluginManager().registerListener(this, new ProxyPingListener());

        this.getProxy().getScheduler().schedule(this, new AttackClearTask(), 1L, 20L, TimeUnit.SECONDS);
        this.getProxy().getScheduler().schedule(this, new NotificationTask(NotificationTask.Server.BUNGEE), 1L, 300L, TimeUnit.MILLISECONDS);

        this.getProxy().getPluginManager().registerCommand(this, new GuardCommand("guard"));

        final BungeeMetrics metrics = new BungeeMetrics(this, 5956);
        metrics.addCustomChart(new BungeeMetrics.SingleLineChart("stoppedBots", StorageManager::getBlockedBots));
        metrics.addCustomChart(new BungeeMetrics.SingleLineChart("checkedConnections", StorageManager::getCheckedConnections));
    }

    @Override
    public void onDisable() {
        StorageManager.save();
    }
}

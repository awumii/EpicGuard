package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.command.GuardCommand;
import me.ishift.epicguard.bungee.listener.ProxyPingListener;
import me.ishift.epicguard.bungee.listener.ProxyPreLoginListener;
import me.ishift.epicguard.bungee.task.AttackClearTask;
import me.ishift.epicguard.bungee.task.CloudTask;
import me.ishift.epicguard.bungee.task.DisplayTask;
import me.ishift.epicguard.bungee.util.Metrics;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.CheckManager;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.Logger;
import net.md_5.bungee.api.plugin.Plugin;

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
        Config.loadBungee();
        StorageManager.load();
        Messages.load();
        Logger.init();

        GeoAPI.create();
        CheckManager.init();
        new Metrics(this, 5956);

        this.getProxy().getPluginManager().registerListener(this, new ProxyPreLoginListener());
        this.getProxy().getPluginManager().registerListener(this, new ProxyPingListener());

        this.getProxy().getScheduler().schedule(this, new AttackClearTask(), 1L, 30L, TimeUnit.SECONDS);
        this.getProxy().getScheduler().schedule(this, new DisplayTask(), 1L, 300L, TimeUnit.MILLISECONDS);
        this.getProxy().getScheduler().schedule(this, new CloudTask(), 1L, Config.cloudTime, TimeUnit.SECONDS);

        this.getProxy().getPluginManager().registerCommand(this, new GuardCommand("guard"));
    }

    @Override
    public void onDisable() {
        StorageManager.save();
    }
}

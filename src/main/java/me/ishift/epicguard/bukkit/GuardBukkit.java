package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.command.GuardCommand;
import me.ishift.epicguard.bukkit.command.GuardTabCompleter;
import me.ishift.epicguard.bukkit.listener.*;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.bukkit.task.ActionBarTask;
import me.ishift.epicguard.bukkit.task.AttackTask;
import me.ishift.epicguard.bukkit.task.UpdaterTask;
import me.ishift.epicguard.bukkit.task.RefreshTask;
import me.ishift.epicguard.bukkit.util.misc.Metrics;
import me.ishift.epicguard.bukkit.util.server.LogFilter;
import me.ishift.epicguard.bukkit.util.server.Reflection;
import me.ishift.epicguard.bukkit.util.server.Updater;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.Logger;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.GeoAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GuardBukkit extends JavaPlugin {
    public static final String PERMISSION = "epicguard.admin";

    public static GuardBukkit getInstance() {
        return JavaPlugin.getPlugin(GuardBukkit.class);
    }

    @Override
    public void onEnable() {
        final long ms = System.currentTimeMillis();
        this.saveDefaultConfig();
        StorageManager.load();
        Config.loadBukkit();
        Logger.init();
        GeoAPI.init();

        Reflection.init();
        Messages.load();

        this.registerListeners();
        this.registerTasks();

        this.getCommand("epicguard").setExecutor(new GuardCommand());
        this.getCommand("epicguard").setTabCompleter(new GuardTabCompleter());

        new LogFilter().registerFilter();

        Updater.checkForUpdates();
        Bukkit.getOnlinePlayers().forEach(UserManager::addUser);

        final Metrics metrics = new Metrics(this, 5845);
        metrics.addCustomChart(new Metrics.SingleLineChart("stoppedBots", StorageManager::getBlockedBots));
        metrics.addCustomChart(new Metrics.SingleLineChart("checkedConnections", StorageManager::getCheckedConnections));

        Logger.info("Succesfully loaded! Took: " + (System.currentTimeMillis() - ms) + "ms");
    }

    @Override
    public void onDisable() {
        Logger.info("Saving data and disabling plugin.");
        StorageManager.save();
    }

    private void registerListeners() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerInventoryClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);

        if (pm.isPluginEnabled("ProtocolLib")) {
            new PlayerTabCompletePacket(this);
        }
    }

    private void registerTasks() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new RefreshTask(), 1L, 20L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTask(), 1L, 400L);

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new UpdaterTask(), 40L, 1800L);
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new ActionBarTask(), 20L, 5L);
    }
}

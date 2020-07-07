package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.command.EpicGuardCommand;
import me.ishift.epicguard.bukkit.listener.CommandListener;
import me.ishift.epicguard.bukkit.listener.PlayerJoinListener;
import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.listener.PlayerQuitListener;
import me.ishift.epicguard.bukkit.module.ModuleManager;
import me.ishift.epicguard.bukkit.module.ModuleTask;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.task.AttackResetTask;
import me.ishift.epicguard.core.task.CounterTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class EpicGuardBukkit extends JavaPlugin {
    private EpicGuard epicGuard;
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        this.epicGuard = new EpicGuard(this.getLogger(), new BukkitNotificator());
        this.moduleManager = new ModuleManager(this.epicGuard);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(epicGuard), this);
        pm.registerEvents(new PlayerQuitListener(epicGuard), this);
        pm.registerEvents(new PlayerJoinListener(this, epicGuard), this);
        pm.registerEvents(new CommandListener(this), this);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(this, new CounterTask(this.epicGuard), 20L, 20L);
        scheduler.runTaskTimerAsynchronously(this, new AttackResetTask(this.epicGuard), 20L * 20L, 20L);
        scheduler.runTaskTimer(this, new ModuleTask(this), 20L * 5L, 20L);

        this.getCommand("epicguard").setExecutor(new EpicGuardCommand(this.epicGuard));
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
}

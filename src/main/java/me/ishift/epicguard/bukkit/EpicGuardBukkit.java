package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.command.EpicGuardCommand;
import me.ishift.epicguard.bukkit.listener.CommandListener;
import me.ishift.epicguard.bukkit.listener.PlayerJoinListener;
import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.listener.PlayerQuitListener;
import me.ishift.epicguard.bukkit.module.ModuleManager;
import me.ishift.epicguard.bukkit.module.ModuleTask;
import me.ishift.epicguard.bukkit.util.Metrics;
import me.ishift.epicguard.core.EpicGuard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicGuardBukkit extends JavaPlugin {
    private EpicGuard epicGuard;
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        this.epicGuard = new EpicGuard(new BukkitMethods(this));
        this.moduleManager = new ModuleManager(this.epicGuard);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(this.epicGuard), this);
        pm.registerEvents(new PlayerQuitListener(this.epicGuard), this);
        pm.registerEvents(new PlayerJoinListener(this.epicGuard), this);
        pm.registerEvents(new CommandListener(this), this);

        Bukkit.getScheduler().runTaskTimer(this, new ModuleTask(this), 20L * 5L, 20L);
        this.getCommand("epicguard").setExecutor(new EpicGuardCommand(this.epicGuard));
        new Metrics(this, 5845);
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
}

package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.command.EpicGuardCommand;
import me.ishift.epicguard.bukkit.listener.PlayerJoinListener;
import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.listener.PlayerQuitListener;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.task.AttackResetTask;
import me.ishift.epicguard.core.task.CounterTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class EpicGuardBukkit extends JavaPlugin {
    private EpicGuard epicGuard;

    @Override
    public void onEnable() {
        this.epicGuard = new EpicGuard(this.getLogger(), new BukkitNotificator());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(epicGuard), this);
        pm.registerEvents(new PlayerJoinListener(epicGuard), this);
        pm.registerEvents(new PlayerQuitListener(epicGuard), this);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(this, new CounterTask(this.epicGuard), 20L, 20L);
        scheduler.runTaskTimerAsynchronously(this, new AttackResetTask(this.epicGuard), 20L * 30L, 20L);

        this.getCommand("epicguard").setExecutor(new EpicGuardCommand(this.epicGuard));
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }
}

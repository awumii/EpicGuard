package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.task.AttackResetTask;
import me.ishift.epicguard.core.task.CounterTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicGuardBukkit extends JavaPlugin {
    private EpicGuard epicGuard;

    @Override
    public void onEnable() {
        this.epicGuard = new EpicGuard(this.getLogger());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(epicGuard), this);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new CounterTask(this.epicGuard), 20L, 20L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AttackResetTask(this.epicGuard), 20L * 30L, 20L);
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }
}

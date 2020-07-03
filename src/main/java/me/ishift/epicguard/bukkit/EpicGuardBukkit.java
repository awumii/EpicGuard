package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.core.EpicGuard;
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
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }
}

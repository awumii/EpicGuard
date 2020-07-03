package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.core.EpicGuard;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicGuardBukkit extends JavaPlugin {
    private EpicGuard epicGuard;

    @Override
    public void onEnable() {
        this.epicGuard = new EpicGuard();
    }
}

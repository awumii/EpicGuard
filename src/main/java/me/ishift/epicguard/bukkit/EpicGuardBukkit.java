package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.common.Configuration;
import me.ishift.epicguard.common.Messages;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.util.DependencyLoader;
import me.ishift.epicguard.common.util.GeoApi;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicGuardBukkit extends JavaPlugin {
    private static EpicGuardBukkit epicGuardBukkit;

    @Override
    public void onEnable() {
        epicGuardBukkit = this;
        AttackManager.init();
    }

    public static EpicGuardBukkit getInstance() {
        return epicGuardBukkit;
    }
}

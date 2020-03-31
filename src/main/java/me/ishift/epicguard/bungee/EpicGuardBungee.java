package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.common.Configuration;
import me.ishift.epicguard.common.Messages;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.util.DependencyLoader;
import me.ishift.epicguard.common.util.GeoApi;
import net.md_5.bungee.api.plugin.Plugin;

public class EpicGuardBungee extends Plugin {
    private static EpicGuardBungee epicGuardBungee;

    @Override
    public void onEnable() {
        epicGuardBungee = this;
        AttackManager.init();
    }

    public static EpicGuardBungee getInstance() {
        return epicGuardBungee;
    }
}

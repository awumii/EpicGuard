package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.util.Reflections;
import me.ishift.epicguard.core.util.MethodInterface;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Logger;

public class BukkitMethods implements MethodInterface {
    private final JavaPlugin plugin;

    public BukkitMethods(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void sendActionBar(String message, UUID target) {
        Reflections.sendActionBar(Bukkit.getPlayer(target), message + "  &8░  &7TPS: &6" + Reflections.getTPS());
    }

    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public void scheduleSyncTask(Runnable task, long seconds) {
        Bukkit.getScheduler().runTaskTimer(this.plugin, task, 20L, seconds * 20L);
    }

    @Override
    public void scheduleAsyncTask(Runnable task, long seconds) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, task, 20L, seconds * 20L);
    }
}

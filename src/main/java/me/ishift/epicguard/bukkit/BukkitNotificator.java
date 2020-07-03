package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.util.Reflections;
import me.ishift.epicguard.core.util.Notificator;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BukkitNotificator implements Notificator {

    @Override
    public void sendActionBar(String message, UUID target) {
        Reflections.sendActionBar(Bukkit.getPlayer(target), message + "  &8░  &7TPS: &6" + Reflections.getTPS());
    }
}

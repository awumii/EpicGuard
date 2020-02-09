package me.ishift.epicguard.bukkit.listener.server;

import me.ishift.epicguard.bukkit.manager.user.User;
import me.ishift.epicguard.bukkit.manager.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

public class PluginMessagesListener implements PluginMessageListener {
    public static void addChannel(Player p, String channel) {
        try {
            p.getClass().getMethod("addChannel", String.class).invoke(p, channel);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        String brand = new String(bytes, StandardCharsets.UTF_8).substring(1);
        User u = UserManager.getUser(player);
        u.setBrand(brand);
    }
}

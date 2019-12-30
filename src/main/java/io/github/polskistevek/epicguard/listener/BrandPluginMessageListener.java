package io.github.polskistevek.epicguard.listener;

import io.github.polskistevek.epicguard.manager.UserManager;
import io.github.polskistevek.epicguard.object.User;
import io.github.polskistevek.epicguard.util.Logger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;

public class BrandPluginMessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        try {
            String brand = new String(bytes, StandardCharsets.UTF_8).substring(1);
            User u = UserManager.getUser(player);
            u.setBrand(brand);
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    public static void addChannel(Player p, String channel) {
        try {
            p.getClass().getMethod("addChannel", String.class).invoke(p, channel);
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}

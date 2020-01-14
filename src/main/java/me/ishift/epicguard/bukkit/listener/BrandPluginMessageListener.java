package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.manager.UserManager;
import me.ishift.epicguard.bukkit.object.User;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;

public class BrandPluginMessageListener implements PluginMessageListener {
    public static void addChannel(Player p, String channel) {
        try {
            p.getClass().getMethod("addChannel", String.class).invoke(p, channel);
        } catch (Exception e) {
            Logger.info("DISABLE 'channel-verification' in brand.yml fil! Exception in class: " + e.getClass().getName());
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        try {
            String brand = new String(bytes, StandardCharsets.UTF_8).substring(1);
            User u = UserManager.getUser(player);
            u.setBrand(brand);
        } catch (Exception e) {
            Logger.info("DISABLE 'channel-verification' in brand.yml fil! Exception in class: " + e.getClass().getName());
        }
    }
}

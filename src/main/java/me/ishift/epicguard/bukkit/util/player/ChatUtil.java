package me.ishift.epicguard.bukkit.util.player;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String fix(String text) {
        return ChatColor.translateAlternateColorCodes('&', text.replace(">>", "»"));
    }
}

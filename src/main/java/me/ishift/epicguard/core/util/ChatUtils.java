package me.ishift.epicguard.core.util;

import net.md_5.bungee.api.ChatColor;

public final class ChatUtils {
    public static String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private ChatUtils() {
    }
}

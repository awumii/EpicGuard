package io.github.polskistevek.epicguard.utils;

import net.md_5.bungee.api.ChatColor;

public class ChatUtil {
    public static String fix(String text) {
        return ChatColor.translateAlternateColorCodes('&', text.replace(">>", "»"));
    }
}

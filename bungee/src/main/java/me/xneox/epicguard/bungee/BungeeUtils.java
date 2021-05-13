package me.xneox.epicguard.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public final class BungeeUtils {
    public static TextComponent createComponent(String text) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', text));
    }

    private BungeeUtils() {}
}

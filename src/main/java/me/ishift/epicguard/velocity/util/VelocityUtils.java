package me.ishift.epicguard.velocity.util;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.text.TextComponent;

public class VelocityUtils {
    public static String color(String message) {
        // Is there something similar to Bukkit & Bungee - ChatColor.translateAlternateColorCodes() ?
        return message.replace("&", "§");
    }

    public static TextComponent getTextComponent(String string) {
        return TextComponent.of(color(string));
    }

    public static void sendMessage(CommandSource source, String message) {
        source.sendMessage(getTextComponent(message));
    }
}

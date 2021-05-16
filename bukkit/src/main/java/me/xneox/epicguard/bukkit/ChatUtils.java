package me.xneox.epicguard.bukkit;

import me.xneox.epicguard.core.logging.GuardLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public final class ChatUtils {
    public static String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    // hehe
    public static void unsupportedVersionWarn(GuardLogger logger) {
        if (Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].startsWith("v1_8")) {
            logger.warning("You are running on an ancient version of Minecraft.");
            logger.warning("1.8 has several security issues already fixed in newer versions.");
            logger.warning("No support will be provided whatsoever.");
        }
    }

    private ChatUtils() {}
}

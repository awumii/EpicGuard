package me.xneox.epicguard.bukkit;

import me.xneox.epicguard.core.logging.GuardLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public final class ChatUtils {
    public static String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void checkUnsupportedVersion(GuardLogger logger) {
        if (Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].startsWith("v1_8")) {
            logger.error("*******************************************************");
            logger.error(" WARNING - YOU ARE RUNNING ON AN OUTDATED");
            logger.error(" AND UNSUPPORTED VERSION OF MINECRAFT (1.8).");
            logger.error(" Please update to the latest server software.");
            logger.error(" No support will be provided for any issues.");
            logger.error("*******************************************************");
        }
    }

    private ChatUtils() {}
}

package me.ishift.epicguard.bukkit.util.server;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.URLUtil;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Updater {
    private static final String CURRENT_VERSION = GuardBukkit.getInstance().getDescription().getVersion();
    private static final String UPDATE_URL = "https://raw.githubusercontent.com/PolskiStevek/EpicGuard/master/files/version.info";
    private static String latestVersion;
    private static boolean updateAvailable = false;

    public static String getCurrentVersion() {
        return CURRENT_VERSION;
    }

    public static void checkForUpdates() {
        if (Config.updater) {
            try {
                latestVersion = URLUtil.readString(UPDATE_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateAvailable = !latestVersion.equals(CURRENT_VERSION);
        }
    }

    public static void notify(Player p) {
        if (Config.updater && p.hasPermission(GuardBukkit.PERMISSION) && updateAvailable) {
            p.sendMessage(ChatUtil.fix(Messages.prefix + "&cOutdated version &8(&6" + CURRENT_VERSION + "&8)&c! New version is available &8(&6" + Updater.latestVersion + "&8)"));
            p.sendMessage(ChatUtil.fix(Messages.prefix + "&cPlease download update here: &6https://www.spigotmc.org/resources/72369"));
        }
    }
}

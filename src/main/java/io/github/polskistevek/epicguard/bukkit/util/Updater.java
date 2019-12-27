package io.github.polskistevek.epicguard.bukkit.util;

import org.bukkit.entity.Player;
import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.utils.ChatUtil;
import io.github.polskistevek.epicguard.utils.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Updater {
    public static final String currentVersion = GuardBukkit.getPlugin(GuardBukkit.class).getDescription().getVersion();
    public static String lastestVersion;
    public static boolean updateAvaible = false;

    public static void checkForUpdates() {
        if (GuardBukkit.UPDATER) {
            lastestVersion = lookup();
            updateAvaible = !lastestVersion.equals(currentVersion);
            if (updateAvaible) {
                Logger.info("[IMPORTANT - UPDATE AVAILABLE!] Your version is outdated (" + currentVersion + " -> " + lastestVersion + ") - download new version here: https://www.spigotmc.org/resources/72369");
            }
        }
    }

    public static void notify(Player p) {
        if (GuardBukkit.UPDATER) {
            if (p.hasPermission(GuardBukkit.PERMISSION)) {
                if (updateAvaible) {
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Your version &8(&c" + currentVersion + "&8) &7is outdated! New version is avaible on SpigotMC &8(&6" + Updater.lastestVersion + "&8)&7."));
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Download latest version, to enjoy new features:"));
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&6https://www.spigotmc.org/resources/72369"));
                }
            }
        }
    }

    private static String lookup() {
        String line = null;
        try {
            final Scanner scanner = new Scanner(new URL("https://api.spigotmc.org/legacy/update.php?resource=72369").openStream());
            line = scanner.next();
        } catch (Exception e) {
            Logger.throwException(e);
        }
        return line;
    }
}

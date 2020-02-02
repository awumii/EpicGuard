package me.ishift.epicguard.bukkit.util;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.ChatUtil;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Updater {
    private static final String currentVersion = GuardBukkit.getInstance().getDescription().getVersion();
    private static String lastestVersion;
    private static boolean updateAvaible = false;

    public static String getCurrentVersion() {
        return currentVersion;
    }

    public static void checkForUpdates() {
        if (Config.updater) {
            lookup();
            updateAvaible = !lastestVersion.equals(currentVersion);
        }
    }

    public static void notify(Player p) {
        if (Config.updater && p.hasPermission(GuardBukkit.PERMISSION) && updateAvaible) {
            p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Your EpicGuard version &8(&c" + currentVersion + "&8) &7is outdated! New version is available on SpigotMC &8(&6" + Updater.lastestVersion + "&8)&7."));
            p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Download latest version, to enjoy new features: &6https://www.spigotmc.org/resources/72369"));
        }
    }

    private static void lookup() {
        try {
            final Scanner scanner = new Scanner(new URL("https://raw.githubusercontent.com/PolskiStevek/EpicGuard/master/files/version.info").openStream());
            lastestVersion = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

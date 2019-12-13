package pl.polskistevek.guard.bukkit.util;

import org.bukkit.entity.Player;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.ChatUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Updater {
    public static final String currentVersion = BukkitMain.getPlugin(BukkitMain.class).getDescription().getVersion();
    public static String lastestVersion;
    public static boolean updateAvaible = false;

    public static void checkForUpdates() {
        if (BukkitMain.UPDATER) {
            lastestVersion = lookup();
            updateAvaible = !lastestVersion.equals(currentVersion);
        }
    }

    public static void notify(Player p) {
        if (p.hasPermission(BukkitMain.PERMISSION)) {
            if (Updater.updateAvaible) {
                p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Your version &8(&c" + Updater.currentVersion + "&8) &7is outdated! New version is avaible on SpigotMC &8(&a" + Updater.lastestVersion + "&8)&7."));
                p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Download latest version, to enjoy new features!"));
                p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&6https://www.spigotmc.org/resources/epicguard-antiproxy-vpn-country-filter-server-protection.72369/"));
            }
        }
    }

    private static String lookup() {
        String line = null;
        try {
            final Scanner scanner = new Scanner(new URL("https://api.spigotmc.org/legacy/update.php?resource=72369").openStream());
            line = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}

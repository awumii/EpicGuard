package pl.polskistevek.guard.utils;

import org.bukkit.entity.Player;
import pl.polskistevek.guard.bukkit.BukkitMain;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Updater {
    public static final String currentVersion = BukkitMain.getPlugin(BukkitMain.class).getDescription().getVersion();
    public static String lastestVersion;
    public static boolean updateAvaible = false;
    public static final String resource = "https://www.spigotmc.org/resources/%E2%9C%A9-epicguard-1-8-1-14-antibot-staff-protection-tools-%E2%9C%A9.72369/";

    public static void checkForUpdates() {
        lastestVersion = lookup();
        updateAvaible = !lastestVersion.equals(currentVersion);
    }

    public static void notify(Player p){
        if (p.hasPermission(BukkitMain.PERMISSION)){
            if (Updater.updateAvaible){
                p.sendMessage(ChatUtil.fix(BukkitMain.PREFIX + "&7Your version &8(&c" + Updater.currentVersion + "&8) &7is outdated! Latest version: &a" + Updater.lastestVersion));
                p.sendMessage(ChatUtil.fix(BukkitMain.PREFIX + "&7Download new version here: &6" + Updater.resource));
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

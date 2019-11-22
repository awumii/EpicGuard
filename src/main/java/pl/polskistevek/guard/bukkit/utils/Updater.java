package pl.polskistevek.guard.bukkit.utils;

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
    public static final String resource = "https://www.spigotmc.org/resources/%E2%9C%A9-epicguard-1-8-1-14-antibot-staff-protection-tools-%E2%9C%A9.72369/";

    public static void checkForUpdates() {
        if (BukkitMain.UPDATER) {
            lastestVersion = lookup();
            updateAvaible = !lastestVersion.equals(currentVersion);
        }
    }

    public static void notify(Player p){
        if (p.hasPermission(BukkitMain.PERMISSION)){
            if (Updater.updateAvaible){
                p.sendMessage(ChatUtil.fix(BukkitMain.PREFIX + "&7Your version &8(&c" + Updater.currentVersion + "&8) &7is outdated! New version is avaible on SpigotMC &8(&a" + Updater.lastestVersion + "&8)&7. Please update it when you can."));
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

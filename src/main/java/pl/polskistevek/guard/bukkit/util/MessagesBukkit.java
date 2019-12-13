package pl.polskistevek.guard.bukkit.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.Downloader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MessagesBukkit {
    public static List<String> MESSAGE_KICK_PROXY;
    public static List<String> MESSAGE_KICK_COUNTRY;
    public static List<String> MESSAGE_KICK_ATTACK;
    public static List<String> MESSAGE_KICK_BLACKLIST;
    public static String ACTIONBAR_ATTACK;
    public static String ACTIONBAR_NO_ATTACK;
    public static String ATTACK_TITLE;
    public static String ATTACK_SUBTITLE;
    public static String HISTORY_NEW;
    public static String NO_PERMISSION;
    public static String PREFIX;

    public static void load() {
        String file = BukkitMain.getPlugin(BukkitMain.class).getDataFolder() + "/messages.yml";
        File configFile = new File(file);
        if (!configFile.exists()) {
            try {
                Downloader.download("http://epicmc.cba.pl/cloud/uploads/messages.yml", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
        MESSAGE_KICK_PROXY = cfg.getStringList("kick-messages.proxy");
        MESSAGE_KICK_COUNTRY = cfg.getStringList("kick-messages.country");
        MESSAGE_KICK_ATTACK = cfg.getStringList("kick-messages.attack");
        MESSAGE_KICK_BLACKLIST = cfg.getStringList("kick-messages.blacklist");
        ACTIONBAR_ATTACK = cfg.getString("actionbar.attack");
        ACTIONBAR_NO_ATTACK = cfg.getString("actionbar.no-attack");
        ATTACK_TITLE = cfg.getString("attack-title.title");
        ATTACK_SUBTITLE = cfg.getString("attack-title.subtitle");
        HISTORY_NEW = cfg.getString("other.history-new");
        NO_PERMISSION = cfg.getString("other.no-permission");
        PREFIX = cfg.getString("prefix");
    }
}

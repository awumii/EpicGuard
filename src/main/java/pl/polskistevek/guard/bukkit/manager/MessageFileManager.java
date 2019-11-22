package pl.polskistevek.guard.bukkit.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.Downloader;
import java.io.File;
import java.io.IOException;

public class MessageFileManager {
    private static final String file = BukkitMain.getPlugin(BukkitMain.class).getDataFolder() + "/messages.yml";

    public static String MESSAGE_KICK_PROXY;
    public static String MESSAGE_KICK_COUNTRY;
    public static String MESSAGE_KICK_ATTACK;
    public static String MESSAGE_KICK_BLACKLIST;
    public static String ACTIONBAR_ATTACK;
    public static String ACTIONBAR_NO_ATTACK;
    public static String ATTACK_TITLE;
    public static String ATTACK_SUBTITLE;
    public static String HISTORY_NEW;
    public static String NO_PERMISSION;
    public static String PREFIX;

    public static void load(){
        File configFile = new File(file);
        if (!configFile.exists()){
            try {
                Downloader.download("http://epicmc.cba.pl/cloud/uploads/messages.yml", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
        MESSAGE_KICK_PROXY = cfg.getString("kick-messages.proxy");
        MESSAGE_KICK_COUNTRY = cfg.getString("kick-messages.country");
        MESSAGE_KICK_ATTACK = cfg.getString("kick-messages.attack");
        MESSAGE_KICK_BLACKLIST = cfg.getString("kick-messages.blacklist");
        ACTIONBAR_ATTACK = cfg.getString("actionbar.attack");
        ACTIONBAR_NO_ATTACK = cfg.getString("actionbar.no-attack");
        ATTACK_TITLE = cfg.getString("attack-title.title");
        ATTACK_SUBTITLE = cfg.getString("attack-title.subtitle");
        HISTORY_NEW = cfg.getString("other.history-new");
        NO_PERMISSION = cfg.getString("other.no-permission");
        PREFIX = cfg.getString("prefix");
    }
}

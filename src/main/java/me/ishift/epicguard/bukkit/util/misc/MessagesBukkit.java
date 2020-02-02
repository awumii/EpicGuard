package me.ishift.epicguard.bukkit.util.misc;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.util.Downloader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MessagesBukkit {
    public static List<String> MESSAGE_KICK_PROXY;
    public static List<String> MESSAGE_KICK_COUNTRY;
    public static List<String> MESSAGE_KICK_ATTACK;
    public static List<String> MESSAGE_KICK_BLACKLIST;
    public static List<String> MESSAGE_KICK_VERIFY;
    public static List<String> MESSAGE_KICK_NAMECONTAINS;
    public static String ACTIONBAR_ATTACK;
    public static String ATTACK_TITLE;
    public static String ATTACK_SUBTITLE;
    public static String HISTORY_NEW;
    public static String NO_PERMISSION;
    public static String PREFIX;
    public static String NOT_ALLOWED_COMMAND;
    public static String BLOCKED_COMMAND;

    public static void load() {
        deprecate();
        String file = GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder() + "/messages_en_US.yml";
        File configFile = new File(file);
        if (!configFile.exists()) {
            try {
                Downloader.download(Downloader.MIRROR_MESSAGES, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);

        PREFIX = cfg.getString("prefix");
        MESSAGE_KICK_PROXY = cfg.getStringList("kick-messages.proxy");
        MESSAGE_KICK_COUNTRY = cfg.getStringList("kick-messages.country");
        MESSAGE_KICK_ATTACK = cfg.getStringList("kick-messages.attack");
        MESSAGE_KICK_BLACKLIST = cfg.getStringList("kick-messages.blacklist");
        MESSAGE_KICK_VERIFY = cfg.getStringList("kick-messages.rejoin");
        MESSAGE_KICK_NAMECONTAINS = cfg.getStringList("kick-messages.rejoin");
        ACTIONBAR_ATTACK = cfg.getString("actionbar.attack");
        ATTACK_TITLE = cfg.getString("attack-title.title");
        ATTACK_SUBTITLE = cfg.getString("attack-title.subtitle");
        HISTORY_NEW = cfg.getString("other.history-new");
        NO_PERMISSION = cfg.getString("other.no-permission");
        NOT_ALLOWED_COMMAND = cfg.getString("other.not-allowed-command");
        BLOCKED_COMMAND = cfg.getString("other.blocked-command");
    }

    private static void deprecate() {
        File file = new File(GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder() + "/messages.yml");
        if (file.exists()) {
            file.renameTo(new File(GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder() + "/deprecated/messages.yml"));
        }
        File file1 = new File(GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder() + "/data.yml");
        if (file1.exists()) {
            file1.renameTo(new File(GuardBukkit.getPlugin(GuardBukkit.class).getDataFolder() + "/deprecated/data.yml"));
        }
    }
}

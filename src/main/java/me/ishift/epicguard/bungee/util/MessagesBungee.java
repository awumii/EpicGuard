package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.cloud.Downloader;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MessagesBungee {
    public static List<String> messageKickProxy;
    public static List<String> messageKickCountry;
    public static List<String> messageKickAttack;
    public static List<String> messageKickBlacklist;
    public static List<String> messageKickNamecontains;
    public static String attackActionBar;
    public static String attackTitle;
    public static String attackSubtitle;
    public static String historyNew;
    public static String noPermission;
    public static String prefix;

    public static void load() throws IOException {
        String file = GuardBungee.getInstance().getDataFolder() + "/messages.yml";
        File configFile = new File(file);
        if (!configFile.exists()) {
            try {
                Downloader.download(Downloader.MIRROR_MESSAGES, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(file));
        messageKickProxy = cfg.getStringList("kick-messages.proxy");
        messageKickCountry = cfg.getStringList("kick-messages.country");
        messageKickAttack = cfg.getStringList("kick-messages.attack");
        messageKickBlacklist = cfg.getStringList("kick-messages.blacklist");
        messageKickNamecontains = cfg.getStringList("kick-messages.namecontains");
        attackActionBar = cfg.getString("actionbar.attack");
        attackTitle = cfg.getString("attack-title.title");
        attackSubtitle = cfg.getString("attack-title.subtitle");
        historyNew = cfg.getString("other.history-new");
        noPermission = cfg.getString("other.no-permission");
        prefix = cfg.getString("prefix");
    }
}

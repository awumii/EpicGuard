package me.ishift.epicguard.common.data.config;

import de.leonhard.storage.Yaml;

import java.util.Arrays;
import java.util.List;

public class BungeeSettings {
    public static boolean tabCompleteBlock;

    public static boolean blockedCommandsEnable;
    public static boolean blockedCommandsBypass;
    public static List<String> blockedCommands;

    public static boolean customTabComplete;
    public static boolean customTabCompleteBypass;
    public static List<String> customTabCompleteList;

    public static void load() {
        final Yaml config = new Yaml("bungee.yml", "plugins/EpicGuard");
        config.setHeader("Welcome to the BungeeCord-specific configuration of EpicGuard.", "These modules are still experimental", "Report the bugs in the GitHub issues.");

        tabCompleteBlock = config.getOrSetDefault("fully-block-tab-complete", false);

        blockedCommandsEnable = config.getOrSetDefault("blocked-commands.enabled", false);
        blockedCommandsBypass = config.getOrSetDefault("blocked-commands.bypass-permission", false);
        blockedCommands = config.getOrSetDefault("blocked-commands.list", Arrays.asList("/example", "/example2"));

        customTabComplete = config.getOrSetDefault("custom-tab-complete.enabled", false);
        customTabCompleteList = config.getOrSetDefault("custom-tab-complete.list", Arrays.asList("/example", "/example2"));
        customTabCompleteBypass = config.getOrSetDefault("custom-tab-complete.bypass-permission", true);
    }
}

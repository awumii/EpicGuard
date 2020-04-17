package me.ishift.epicguard.common.data.config;

import de.leonhard.storage.Yaml;

import java.util.Arrays;
import java.util.List;

public class SpigotSettings {
    public static boolean tabCompleteBlock;

    public static List<String> blockedCommands;
    public static List<String> allowedCommands;

    public static List<String> opProtectionList;
    public static String opProtectionCommand;

    public static boolean allowedCommandsBypass;
    public static boolean blockedCommandsEnable;
    public static boolean allowedCommandsEnable;

    public static boolean opProtectionEnable;

    public static boolean customTabComplete;
    public static List<String> customTabCompleteList;
    public static boolean customTabCompleteBypass;

    public static boolean disableOperatorMechanics;
    public static boolean disableOperatorMechanicsConsole;
    public static boolean blockNamespacedCommands;

    public static void load() {
        final Yaml config = new Yaml("spigot.yml", "plugins/EpicGuard");

        tabCompleteBlock = config.getOrSetDefault("fully-block-tab-complete", false);

        allowedCommands = config.getOrSetDefault("allowed-commands.list", Arrays.asList("/example", "/example2"));
        allowedCommandsBypass = config.getOrSetDefault("allowed-commands.bypass-permission", true);
        allowedCommandsEnable = config.getOrSetDefault("allowed-commands.enabled", false);

        opProtectionEnable = config.getOrSetDefault("op-protection.enabled", false);
        opProtectionList = config.getOrSetDefault("op-protection.list", Arrays.asList("YourName", "YourAdmin2"));
        opProtectionCommand = config.getOrSetDefault("op-protection.punish", "ban {PLAYER} You are not allowed to have OP!");

        blockedCommandsEnable = config.getOrSetDefault("blocked-commands.enabled", false);
        blockedCommands = config.getOrSetDefault("blocked-commands.list", Arrays.asList("/example", "/example2"));

        customTabComplete = config.getOrSetDefault("custom-tab-complete.enabled", false);
        customTabCompleteList = config.getOrSetDefault("custom-tab-complete.list", Arrays.asList("/example", "/example2"));
        customTabCompleteBypass = config.getOrSetDefault("custom-tab-complete.bypass-permission", true);

        disableOperatorMechanics = config.getOrSetDefault("disable-vanilla-operator-mechanics", false);
        disableOperatorMechanics = config.getOrSetDefault("disable-vanilla-operator-mechanics-in-console", false);
        blockNamespacedCommands = config.getOrSetDefault("block-namespaced-commands", false);
    }
}

/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.ishift.epicguard.common.data.config;

import de.leonhard.storage.Yaml;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpigotSettings {
    public static boolean tabCompleteBlock;

    public static boolean opProtectionEnable;
    public static String opProtectionCommand;
    public static List<String> opProtectionList;

    public static boolean blockedCommandsEnable;
    public static boolean blockedCommandsBypass;
    public static List<String> blockedCommands;

    public static boolean allowedCommandsEnable;
    public static boolean allowedCommandsBypass;
    public static List<String> allowedCommands;

    public static boolean customTabComplete;
    public static boolean customTabCompleteBypass;
    public static List<String> customTabCompleteList;

    public static boolean disableOperatorMechanics;
    public static boolean disableOperatorMechanicsConsole;
    public static boolean deopOnEnable;

    public static boolean blockNamespacedCommands;
    public static boolean blockNamespacedCommandsWhitelistEnabled;
    public static List<String> blockNamespacedCommandsWhitelist;
    public static boolean blockNamespacedCommandsBypass;

    public static boolean superAdminEnabled;
    public static List<String> superAdminList;

    public static void load() {
        final Yaml config = new Yaml("spigot.yml", "plugins/EpicGuard");
        config.setHeader("Welcome to the Spigot-specific configuration of EpicGuard.", "These modules are still experimental", "Report the bugs in the GitHub issues.");

        tabCompleteBlock = config.getOrSetDefault("fully-block-tab-complete", false);

        allowedCommands = config.getOrSetDefault("allowed-commands.list", Arrays.asList("/example", "/example2"));
        allowedCommandsBypass = config.getOrSetDefault("allowed-commands.bypass-permission", true);
        allowedCommandsEnable = config.getOrSetDefault("allowed-commands.enabled", false);

        opProtectionEnable = config.getOrSetDefault("op-protection.enabled", false);
        opProtectionList = config.getOrSetDefault("op-protection.list", Arrays.asList("YourName", "YourAdmin2"));
        opProtectionCommand = config.getOrSetDefault("op-protection.punish", "ban {PLAYER} You are not allowed to have OP!");

        blockedCommandsEnable = config.getOrSetDefault("blocked-commands.enabled", false);
        blockedCommandsBypass = config.getOrSetDefault("blocked-commands.bypass-permission", false);
        blockedCommands = config.getOrSetDefault("blocked-commands.list", Arrays.asList("/example", "/example2"));

        customTabComplete = config.getOrSetDefault("custom-tab-complete.enabled", false);
        customTabCompleteList = config.getOrSetDefault("custom-tab-complete.list", Arrays.asList("/example", "/example2"));
        customTabCompleteBypass = config.getOrSetDefault("custom-tab-complete.bypass-permission", true);

        disableOperatorMechanics = config.getOrSetDefault("disable-vanilla-operator-mechanics", false);
        disableOperatorMechanicsConsole = config.getOrSetDefault("disable-vanilla-operator-mechanics-in-console", false);
        deopOnEnable = config.getOrSetDefault("deop-all-operators-on-startup", false);

        blockNamespacedCommands = config.getOrSetDefault("block-namespaced-commands.enabled", false);
        blockNamespacedCommandsWhitelistEnabled = config.getOrSetDefault("block-namespaced-commands.whitelist.enabled", false);
        blockNamespacedCommandsWhitelist = config.getOrSetDefault("block-namespaced-commands.whitelist.commands", Arrays.asList("/plugin:command1", "/plugin:command2"));
        blockNamespacedCommandsBypass = config.getOrSetDefault("block-namespaced-commands.bypass-permission", false);

        superAdminEnabled = config.getOrSetDefault("super-admin.enabled", false);
        superAdminList = config.getOrSetDefault("super-admin.list", Collections.singletonList("Owner"));
    }
}

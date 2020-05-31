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

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

package me.ishift.epicguard.common;

import de.leonhard.storage.Config;

import java.util.Collections;
import java.util.List;

public class Messages {
    public static List<String> KICK_PROXY;
    public static List<String> KICK_GEO;
    public static List<String> KICK_PING;
    public static List<String> KICK_BLACKLIST;
    public static List<String> KICK_VERIFY;
    public static List<String> KICK_NICKNAME;

    public static String PREFIX;

    public static void load() {
        final Config config = new Config("messages", "plugins/EpicGuard");
        PREFIX = config.getOrSetDefault("prefix", "&8[&6&lEpicGuard&8] &7");

        KICK_PROXY = config.getOrSetDefault("kick-messages.proxy", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6Proxy/VPN."));
        KICK_GEO = config.getOrSetDefault("kick-messages.country", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6Country GeoDetection."));
        KICK_PING = config.getOrSetDefault("kick-messages.server-list", Collections.singletonList("&8[&6EpicGuard&8] &cAdd our server to your &6server list&c, and then join again."));
        KICK_BLACKLIST = config.getOrSetDefault("kick-messages.blacklist", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6IP Blacklist."));
        KICK_VERIFY = config.getOrSetDefault("kick-messages.rejoin", Collections.singletonList("&8[&6EpicGuard&8] &cPlease join our server &6again &cto verify that you are not a bot."));
        KICK_NICKNAME = config.getOrSetDefault("kick-messages.namecontains", Collections.singletonList("&8[&6EpicGuard&8] &cYou have been detected for: &6NameContains (Change nickname or contact server admin)"));

    }
}

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

package me.ishift.epicguard.universal;

import de.leonhard.storage.Json;
import me.ishift.epicguard.universal.util.RuntimeExecutor;

import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private static final Json FILE = new Json("storage", "plugins/EpicGuard/data");
    private static List<String> blacklist = new ArrayList<>();
    private static List<String> whitelist = new ArrayList<>();
    private static int blockedBots = 0;
    private static int checkedConnections = 0;

    public static void load() {
        blacklist = FILE.getOrSetDefault("addresses.blacklist", new ArrayList<>());
        whitelist = FILE.getOrSetDefault("addresses.whitelist", new ArrayList<>());
        blockedBots = FILE.getOrSetDefault("stats.blocked-bots", 0);
        checkedConnections = FILE.getOrSetDefault("stats.checked-connections", 0);
    }

    public static void save() {
        FILE.set("addresses.blacklist", blacklist);
        FILE.set("addresses.whitelist", whitelist);
        FILE.set("stats.blocked-bots", blockedBots);
        FILE.set("stats.checked-connections", checkedConnections);
    }

    public static Json getFile() {
        return FILE;
    }

    public static boolean isBlacklisted(String address) {
        return blacklist.contains(address);
    }

    public static boolean isWhitelisted(String address) {
        return whitelist.contains(address);
    }

    public static List<String> getBlacklist() {
        return blacklist;
    }

    public static List<String> getWhitelist() {
        return whitelist;
    }

    public static void blacklist(String adress) {
        if (blacklist.contains(adress)) return;
        blacklist.add(adress);
        RuntimeExecutor.blacklist(adress);
    }

    public static void whitelist(String adress) {
        if (whitelist.contains(adress)) return;
        whitelist.add(adress);
        blacklist.remove(adress);
        RuntimeExecutor.blacklist(adress);
    }

    public static int getBlockedBots() {
        return blockedBots;
    }

    public static int getCheckedConnections() {
        return checkedConnections;
    }

    public static void increaseBlockedBots() {
        blockedBots++;
    }

    public static void increaseCheckedConnections() {
        checkedConnections++;
    }
}

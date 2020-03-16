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

import de.leonhard.storage.Json;
import me.ishift.epicguard.common.util.RuntimeExecutor;

import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private static Json storage;
    private static List<String> blacklist = new ArrayList<>();
    private static List<String> whitelist = new ArrayList<>();
    private static int blockedBots = 0;
    private static int checkedConnections = 0;

    public static void load() {
        storage = new Json("storage", "plugins/EpicGuard/data");
        blacklist = storage.getOrSetDefault("addresses.blacklist", new ArrayList<>());
        whitelist = storage.getOrSetDefault("addresses.whitelist", new ArrayList<>());
        blockedBots = storage.getOrSetDefault("stats.blocked-bots", 0);
        checkedConnections = storage.getOrSetDefault("stats.checked-connections", 0);
    }

    public static void save() {
        storage.set("addresses.blacklist", blacklist);
        storage.set("addresses.whitelist", whitelist);
        storage.set("stats.blocked-bots", blockedBots);
        storage.set("stats.checked-connections", checkedConnections);
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

    public static void blacklist(String address) {
        if (blacklist.contains(address)) {
            return;
        }
        blacklist.add(address);
        RuntimeExecutor.blacklist(address);
    }

    public static void whitelist(String address) {
        if (whitelist.contains(address)) {
            return;
        }
        whitelist.add(address);
        blacklist.remove(address);
        RuntimeExecutor.blacklist(address);
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

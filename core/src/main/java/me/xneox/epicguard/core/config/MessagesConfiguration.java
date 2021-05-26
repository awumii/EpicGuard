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

package me.xneox.epicguard.core.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Arrays;
import java.util.List;

@ConfigSerializable
public class MessagesConfiguration {

    // Config sections.
    public Command command = new Command();
    public Disconnect disconnect = new Disconnect();

    public String actionbarMonitor = "&cEpicGuard &8» &6%cps% &7connections/s &8| %status%";

    public String actionbarNoAttack = "&7No attack...";

    public String actionbarAttack = "&cAttack detected!";

    public String updateAvailable = "A new update is available: {NEWVER} (You are still on {OLDVER})";

    @ConfigSerializable
    public static final class Command {
        public String prefix = " &cEpicGuard &8▸ &7";

        public String usage = "&cCorrect usage: &6{USAGE}";

        public String whitelistAdd = "&7The user &a{USER} &7has been added to the whitelist.";

        public String whitelistRemove = "The user &6{USER} &7has been removed from the whitelist";

        public String blacklistAdd = "&7The user &c{USER} &7has been added to the blacklist.";

        public String blacklistRemove = "&7The user &6{USER} &7has been removed from the blacklist.";

        public String alreadyWhitelisted = "&cThe user &6{USER} &cis already whitelisted!";

        public String alreadyBlacklisted = "&cThe user &6{USER} &cis already blacklisted!";

        public String notWhitelisted = "&cThe user &6{USER} &cis not in whitelist!";

        public String notBlacklisted = "&cThe user &6{USER} &cis not in the blacklist!";

        public String reload = "&7Succesfully reloaded config and messages!";

        public String toggleStatus = "&7You have toggled your attack status!";

        public String analysisFailed = "&cCould not find address for this nickname, or provided address is invalid.";

        public List<String> mainCommand = Arrays.asList(
                "",
                " &6EpicGuard Protection System &8- &7Running version &f{VERSION}",
                "",
                " &8▸ &7Under attack: {ATTACK}",
                " &8▸ &7Connections: &e{CPS}/s",
                " &8▸ &7Blacklist: &e{BLACKLISTED-IPS} &7IPs, &e{BLACKLISTED-NAMES} &7nicks",
                " &8▸ &7Whitelisted IPs: &e{WHITELISTED-IPS} &7IPs, &e{WHITELISTED-NAMES} &7nicks",
                "",
                " &8/&fguard status &8- &7Toggle attack status on actionbar.",
                " &8/&fguard reload &8- &7Reload config and messages.",
                " &8/&fguard analyze <nick/address> &8- &7Perform detailed analysis on specified user.",
                " &8/&fguard whitelist <add/remove> <nick/address> &8- &7whitelist/unwhitelist an address or nickname.",
                "");

        public List<String> analyzeCommand = Arrays.asList(
                "",
                " &6EpicGuard Analysis System &8- &7Results for &f{ADDRESS}",
                "",
                " &eGeographic Data:",
                "  &8▸ &7Country: &f{COUNTRY}",
                "  &8▸ &7City: &f{CITY}",
                "",
                " &eKnown Accounts&6 ({ACCOUNT-AMOUNT}):",
                "  &8▸ &f{NICKNAMES}",
                "",
                " &eOther Data:",
                "  &8▸ &7Whitelisted: {WHITELISTED}",
                "  &8▸ &7Blacklisted: {BLACKLISTED}",
                "");
    }

    @ConfigSerializable
    public static final class Disconnect {
        public List<String> geographical = Arrays.asList(
                "&8» &7You have been kicked by &bAntiBot Protection&7:",
                "&8» &cYour country/city is not allowed on this server.");

        public List<String> blacklisted = Arrays.asList(
                "&8» &7You have been kicked by &bAntiBot Protection&7:",
                "&8» &cYou have been blacklisted on this server.");

        public List<String> attackLockdown = Arrays.asList(
                "&8» &7You have been kicked by &bAntiBot Protection&7:",
                "&8» &cServer is under attack, please wait some seconds before joining.");

        public List<String> proxy = Arrays.asList(
                "&8» &7You have been kicked by &bAntiBot Protection&7:",
                "&8» &cYou are using VPN or Proxy.");

        public List<String> reconnect = Arrays.asList(
                "&8» &7You have been kicked by &bAntiBot Protection&7:",
                "&8» &cJoin the server again.");

        public List<String> nickname = Arrays.asList(
                "&8» &7You have been kicked by &bAntiBot Protection&7:",
                "&8» &cYou nickname is not allowed on this server.");

        public List<String> accountLimit = Arrays.asList(
                "&8» &7You have been kicked by &bAntiBot Protection&7:",
                "&8» &cYou have too many accounts on your IP address.");

        public List<String> serverListPing = Arrays.asList(
                "&8» &7You have been kicked by &bAntiBot Protection&7:",
                "&8» &cYou must add our server to your servers list to verify yourself.");

        public List<String> settingsPacket = Arrays.asList(
                "&8» &7You have been kicked by &bAntiBot Protection&7:",
                "&8» &cBot-like behaviour detected, please join the server again.");
    }
}

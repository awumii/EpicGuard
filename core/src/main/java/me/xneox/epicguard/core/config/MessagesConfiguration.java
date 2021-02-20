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

import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgCollectionStyle;
import org.diorite.cfg.annotations.CfgName;
import org.diorite.cfg.annotations.CfgStringStyle;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;

import java.util.Arrays;
import java.util.List;

@CfgClass(name = "MessagesConfiguration")
@CfgDelegateDefault("{new}")
public class MessagesConfiguration {

    @CfgName("prefix")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_QUOTED)
    public String prefix = "&cEpicGuard &8» &7";

    @CfgName("no-permission")
    public String noPermission = "&cYou don't have permission for this command!";

    public String usage = "&cCorrect usage: &6{USAGE}";

    @CfgName("command-whitelist-add")
    public String whitelisted = "&7The user &a{USER} &7has been added to the whitelist.";

    @CfgName("command-whitelist-remove")
    public String unWhitelisted = "The user &6{USER} &7has been removed from the whitelist";

    @CfgName("command-blacklist-add")
    public String blacklisted = "&7The user &c{USER} &7has been added to the blacklist.";

    @CfgName("command-blacklist-remove")
    public String unBlacklisted = "&7The user &6{USER} &7has been removed from the whitelist.";

    @CfgName("command-whitelist-already")
    public String alreadyWhitelisted = "&cThe user &6{USER} &cis already whitelisted!";

    @CfgName("command-blacklist-already")
    public String alreadyBlacklisted = "&cThe user &6{USER} &cis already blacklisted!";

    @CfgName("command-whitelist-not")
    public String notWhitelisted = "&cThe user &6{USER} &cis not in whitelist!";

    @CfgName("command-blacklist-not")
    public String notBlacklisted = "&cThe user &6{USER} &cis not in the blacklist!";

    public String reload = "&7Succesfully reloaded config and messages!";

    public String notifications = "&7You have toggled your notifications!";

    public String unknown = "&cCommand not found. Use /guard for help.";

    @CfgName("command-not-in-allowed")
    public String notAllowedCommand = "&fUnknown command. Type '/help' for help.";

    @CfgName("blocked-command")
    public String blockedCommand = "&cThis command has been blocked.";

    @CfgName("operator-disabled")
    public String operatorDisabled = "&cOperator mechanics has been disabled on this server.";

    @CfgName("analysis-failed")
    public String analysisFailed = "&cCould not find address for this nickname, or provided address is invalid.";

    @CfgName("actionbar-monitor")
    public String monitor = "&cEpicGuard &8» &6%cps% &7connections/s &8| %status%";

    @CfgName("actionbar-no-attack")
    public String noAttack = "&7No attack...";

    @CfgName("actionbar-attack")
    public String attack = "&cAttack detected!";

    @CfgName("kick-message-geo")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageGeo = Arrays.asList(
            "&8» &7You have been kicked by &bAntiBot Protection&7:",
            "&8» &cYour country/city is not allowed on this server.");

    @CfgName("kick-message-blacklist")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageBlacklist = Arrays.asList(
            "&8» &7You have been kicked by &bAntiBot Protection&7:",
            "&8» &cYour IP address is blacklisted on this server.");

    @CfgName("kick-message-attack")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageAttack = Arrays.asList(
            "&8» &7You have been kicked by &bAntiBot Protection&7:",
            "&8» &cServer is under attack, please wait some seconds before joining.");

    @CfgName("kick-message-proxy")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageProxy = Arrays.asList(
            "&8» &7You have been kicked by &bAntiBot Protection&7:",
            "&8» &cYou are using VPN or Proxy.");

    @CfgName("kick-message-reconnect")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageReconnect = Arrays.asList(
            "&8» &7You have been kicked by &bAntiBot Protection&7:",
            "&8» &cJoin the server again.");

    @CfgName("kick-message-account-limit")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageAccountLimit = Arrays.asList(
            "&8» &7You have been kicked by &bAntiBot Protection&7:",
            "&8» &cYou have too many accounts on your IP address.");

    @CfgName("kick-message-server-list")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageServerList = Arrays.asList(
            "&8» &7You have been kicked by &bAntiBot Protection&7:",
            "&8» &cYou must add our server to your servers list to verify yourself.");

    @CfgName("kick-message-rate-limit")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageRateLimit = Arrays.asList(
            "&8» &7You have been kicked by &bAntiBot Protection&7:",
            "&8» &cYou must wait some seconds between joining again.");
}

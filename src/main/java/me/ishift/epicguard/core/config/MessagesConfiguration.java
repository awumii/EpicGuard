package me.ishift.epicguard.core.config;

import org.diorite.cfg.annotations.*;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;

import java.util.Arrays;
import java.util.List;

@CfgClass(name = "MessagesConfiguration")
@CfgDelegateDefault("{new}")
@CfgComment("███████╗██████╗ ██╗ ██████╗ ██████╗ ██╗   ██╗ █████╗ ██████╗ ██████╗")
@CfgComment("██╔════╝██╔══██╗██║██╔════╝██╔════╝ ██║   ██║██╔══██╗██╔══██╗██╔══██╗")
@CfgComment("█████╗  ██████╔╝██║██║     ██║  ███╗██║   ██║███████║██████╔╝██║  ██║")
@CfgComment("██╔══╝  ██╔═══╝ ██║██║     ██║   ██║██║   ██║██╔══██║██╔══██╗██║  ██║")
@CfgComment("███████╗██║     ██║╚██████╗╚██████╔╝╚██████╔╝██║  ██║██║  ██║██████╔╝")
@CfgComment("╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝")
@CfgComment("You are running EpicGuard V5-NEON")
@CfgComment("Created by iShift (Discord: iShift#0524)")
@CfgComment("SpigotMC: https://www.spigotmc.org/resources/72369/")
@CfgComment("Support Discord: https://discord.gg/VkfhFCv")
public class MessagesConfiguration {

    @CfgName("prefix")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_QUOTED)
    public String prefix = "&8[&2EpicGuard&8] &7";

    @CfgName("no-permission")
    public String noPermission = "&cYou don't have permission for this command!";

    public String usage = "&cCorrect usage: &6{USAGE}";

    public String whitelisted = "&7Succesfully whitelisted address &a{IP}!";

    public String blacklisted = "&7Succesfully blacklisted address &a{IP}!";

    public String reload = "&7Succesfully reloaded config and messages!";

    public String notifications = "&7You have toggled your notifications!";

    @CfgName("command-not-in-allowed")
    public String notAllowedCommand = "&fUnknown command. Type '/help' for help.";

    @CfgName("blocked-command")
    public String blockedCommand = "&cThis command has been blocked.";

    @CfgName("operator-disabled")
    public String operatorDisabled = "&cOperator mechanics has been disabled on this server.";

    @CfgName("kick-message-geo")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageGeo = Arrays.asList(
            "&8[&2EpicGuard&8]",
            "&7You have been kicked by our &aantibot protection&7.",
            "&7Details: &cYour country/city is not allowed on this server.");

    @CfgName("kick-message-blacklist")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageBlacklist = Arrays.asList(
            "&8[&2EpicGuard&8]",
            "&7You have been kicked by our &aantibot protection&7.",
            "&7Details: &cYour IP address is blacklisted on this server.");

    @CfgName("kick-message-attack")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageAttack = Arrays.asList(
            "&8[&2EpicGuard&8]",
            "&7You have been kicked by our &aantibot protection&7.",
            "&7Details: &cServer is under attack, please wait some seconds before joining.");

    @CfgName("kick-message-proxy")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageProxy = Arrays.asList(
            "&8[&2EpicGuard&8]",
            "&7You have been kicked by our &aantibot protection&7.",
            "&7Details: &cYou are using VPN or Proxy.");

    @CfgName("kick-message-reconnect")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageReconnect = Arrays.asList(
            "&8[&2EpicGuard&8]",
            "&7You have been kicked by our &aantibot protection&7.",
            "&7Details: &cPlease join the server again.");

    @CfgName("kick-message-account-limit")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageAccountLimit = Arrays.asList(
            "&8[&2EpicGuard&8]",
            "&7You have been kicked by our &aantibot protection&7.",
            "&7Details: &cYou have too many accounts on your IP address.");

    @CfgName("kick-message-server-list")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageServerList = Arrays.asList(
            "&8[&2EpicGuard&8]",
            "&7You have been kicked by our &aantibot protection&7.",
            "&7Details: &cYou must add our server to your servers list to verify yourself.");
}

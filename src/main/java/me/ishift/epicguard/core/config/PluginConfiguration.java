package me.ishift.epicguard.core.config;

import org.diorite.cfg.annotations.*;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CfgClass(name = "PluginConfiguration")
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
public class PluginConfiguration {

    @CfgComment(" ")
    @CfgComment("╔════════════════════════════════════════════╗")
    @CfgComment("║           Geographical Checks              ║")
    @CfgComment("╚════════════════════════════════════════════╝")
    @CfgComment(" ")
    @CfgComment("Country check will filter countries your players can connect from.")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player.")
    @CfgComment("ATTACK - check will perform only during bot attack.")
    @CfgComment("Default: NEVER (You SHOULD change this!)")
    @CfgName("country-check-mode")
    public String countryCheck = "NEVER";

    @CfgComment("This will define if country-check should be blacklist or whitelist.")
    @CfgComment("BLACKLIST - countries below are blocked")
    @CfgComment("WHITELIST - only countries below are allowed")
    @CfgName("country-check-type")
    public String countryCheckType = "BLACKLIST";

    @CfgComment("List of country codes: https://dev.maxmind.com/geoip/legacy/codes/iso3166/")
    @CfgName("country-check-countries")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> countryCheckValues = Collections.singletonList("US");

    @CfgComment("If some player's city is listed here, he will be blacklisted.")
    @CfgName("city-blacklist")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> cityBlacklist = Collections.singletonList("ExampleCity");

    @CfgComment(" ")
    @CfgComment("╔════════════════════════════════════════════╗")
    @CfgComment("║           Other AntiBot Checks             ║")
    @CfgComment("╚════════════════════════════════════════════╝")
    @CfgComment(" ")

    @CfgComment("Proxy check will define if user is connecting from Proxy/VPN.")
    @CfgComment("Change this option to save performance or increase accuracy")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player.")
    @CfgComment("ATTACK - check will perform only during bot attack.")
    @CfgComment("Default: ALWAYS")
    @CfgName("country-check")
    public String proxyCheck = "ALWAYS";

    @CfgComment("Recommended option!")
    @CfgComment("Register an account here: https://proxycheck.io/dashboard")
    @CfgComment("And get your FREE api key.")
    @CfgComment("It will give you more queries/24h")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_QUOTED)
    @CfgName("proxy-check-key")
    public String proxyCheckKey = "put_your_key_here";

    @CfgComment("Reconnect check will force users to join the server again.")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player.")
    @CfgComment("ATTACK - check will perform only during bot attack.")
    @CfgComment("Default: ATTACK")
    @CfgName("reconnect-check")
    public String reconnectCheck = "ATTACK";

    @CfgComment("Should every user (except if he is whitelisted)")
    @CfgComment("be disconnected when there is an bot attack?")
    @CfgComment("Enable for better protection, disable to allow NEW players during attack.")
    @CfgComment("DEFAULT: true")
    @CfgName("attack-deny-join")
    public boolean denyJoin = true;

    @CfgComment("Time in seconds the player must be online")
    @CfgComment("to be considered as legitimate player.")
    @CfgComment("Set to -1 to disable this option")
    @CfgComment("DEFAULT: 240 (4 minutes)")
    @CfgName("auto-whitelist")
    public int autoWhitelist = 280;

    @CfgComment("How many connections per second must be made,")
    @CfgComment("to activate attack mode temporally?")
    @CfgName("max-cps")
    public int maxCps = 6;

    @CfgComment(" ")
    @CfgComment("╔════════════════════════════════════════════╗")
    @CfgComment("║              Other Settings                ║")
    @CfgComment("╚════════════════════════════════════════════╝")
    @CfgComment(" ")
    @CfgComment("")

    @CfgComment("If log message contains one of these words, it will")
    @CfgComment("be hidden. This can save a lot of CPU on big attacks.")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("console-filter")
    public List<String> consoleFilter = Arrays.asList(
            "GameProfile",
            "Disconnected",
            "UUID of player",
            "logged in",
            "lost connection",
            "InitialHandler");
}

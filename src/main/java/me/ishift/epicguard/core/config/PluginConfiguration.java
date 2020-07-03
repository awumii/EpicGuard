package me.ishift.epicguard.core.config;

import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgCollectionStyle;
import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.CfgName;
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
    @CfgComment("Check mode will define, when this check will work.")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player.")
    @CfgComment("ATTACK - check will perform only during bot attack.")
    @CfgName("country-check-mode")
    public String countryCheckMode = "NEVER";

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
    @CfgComment("║              Other Settings                ║")
    @CfgComment("╚════════════════════════════════════════════╝")
    @CfgComment(" ")
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

    @CfgComment("How many connections per second must be made,")
    @CfgComment("to activate attack mode temporally?")
    @CfgName("max-cps")
    public int maxCps = 6;
}

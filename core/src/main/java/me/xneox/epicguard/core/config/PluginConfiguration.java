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

import org.diorite.cfg.annotations.*;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.diorite.cfg.annotations.CfgCollectionStyle.CollectionStyle;
import static org.diorite.cfg.annotations.CfgStringStyle.StringStyle;

@CfgClass(name = "PluginConfiguration")
@CfgDelegateDefault("{new}")
@CfgComment("███████╗██████╗ ██╗ ██████╗ ██████╗ ██╗   ██╗ █████╗ ██████╗ ██████╗")
@CfgComment("██╔════╝██╔══██╗██║██╔════╝██╔════╝ ██║   ██║██╔══██╗██╔══██╗██╔══██╗")
@CfgComment("█████╗  ██████╔╝██║██║     ██║  ███╗██║   ██║███████║██████╔╝██║  ██║")
@CfgComment("██╔══╝  ██╔═══╝ ██║██║     ██║   ██║██║   ██║██╔══██║██╔══██╗██║  ██║")
@CfgComment("███████╗██║     ██║╚██████╗╚██████╔╝╚██████╔╝██║  ██║██║  ██║██████╔╝")
@CfgComment("╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝")
@CfgComment("Created by xNeox (Discord: xNeox#0524)")
@CfgComment("SpigotMC: https://www.spigotmc.org/resources/72369/")
@CfgComment("Support Discord: https://discord.gg/VkfhFCv")
public class PluginConfiguration {
    @CfgComment("")
    @CfgComment("╔════════════════════════════════════════════╗")
    @CfgComment("║           Geographical Checks              ║")
    @CfgComment("╚════════════════════════════════════════════╝")
    @CfgComment("")
    @CfgComment("Country check will filter countries your players can connect from.")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player always.")
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
    @CfgCollectionStyle(CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> countryCheckValues = Collections.singletonList("US");

    @CfgComment("If some player's city is listed here, he will be blacklisted.")
    @CfgName("city-blacklist")
    @CfgCollectionStyle(CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> cityBlacklist = Collections.singletonList("ExampleCity");

    @CfgComment("")
    @CfgComment("╔════════════════════════════════════════════╗")
    @CfgComment("║           Other AntiBot Checks             ║")
    @CfgComment("╚════════════════════════════════════════════╝")
    @CfgComment("")

    @CfgComment("Proxy check will define if user is connecting from Proxy/VPN.")
    @CfgComment("Change this option to save performance or increase accuracy")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player always.")
    @CfgComment("ATTACK - check will perform only during bot attack.")
    @CfgComment("Default: ALWAYS")
    @CfgName("proxy-check")
    public String proxyCheck = "ALWAYS";

    @CfgComment("Should users detected for VPN/Proxy")
    @CfgComment("have their IP address blacklisted?")
    @CfgComment("Disable if you want to allow users who")
    @CfgComment("joined with VPN to join again after disabling it.")
    @CfgComment("Enable to improve performance.")
    @CfgName("blacklist-vpn-proxy-users")
    public boolean proxyCheckBlacklist = true;

    @CfgComment("Recommended option!")
    @CfgComment("Register an account here: https://proxycheck.io/dashboard")
    @CfgComment("And get your FREE api key.")
    @CfgComment("It will give you more queries/24h")
    @CfgStringStyle(StringStyle.ALWAYS_QUOTED)
    @CfgName("proxy-check-key")
    public String proxyCheckKey = "put_your_key_here";

    @CfgComment("How many accounts can be used")
    @CfgComment("per one ip address?")
    @CfgName("max-accounts-per-ip")
    public int accountLimit = 3;

    @CfgComment("Reconnect check will force users to join the server again.")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player always.")
    @CfgComment("ATTACK - check will perform only during bot attack.")
    @CfgComment("Default: ATTACK")
    @CfgName("reconnect-check")
    public String reconnectCheck = "ATTACK";

    @CfgComment("Server-list check will force users to add your server.")
    @CfgComment("to their server list (and pinging it) before joining.")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player always.")
    @CfgComment("ATTACK - check will perform only during bot attack.")
    @CfgComment("Default: ATTACK")
    @CfgName("server-list-check")
    public String serverListCheck = "ATTACK";

    @CfgComment("Should every user (except if he is whitelisted)")
    @CfgComment("be disconnected when there is an bot attack?")
    @CfgComment("Enable for better protection, disable to allow NEW players during attack.")
    @CfgComment("DEFAULT: true")
    @CfgName("attack-deny-join")
    public boolean denyJoin = true;

    @CfgComment("Time in seconds the player must be online")
    @CfgComment("to be considered as legitimate player.")
    @CfgComment("Set to a huge number to disable this option")
    @CfgComment("DEFAULT: 240 (4 minutes)")
    @CfgName("auto-whitelist-time")
    public int autoWhitelistTime = 240;

    @CfgComment("Rate-limit check will force users to wait some seconds.")
    @CfgComment("before joining the server again (configurable below).")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player always.")
    @CfgComment("ATTACK (default) - check will perform only during bot attack.")
    @CfgName("rate-limit-check")
    public String rateLimitCheck = "ALWAYS";

    @CfgComment("How many seconds users will need to wait")
    @CfgComment("between joining the server again? (See check above).")
    @CfgName("rate-limit-seconds")
    public int rateLimit = 10;

    @CfgComment("How many connections per second must be made,")
    @CfgComment("to activate attack mode temporally?")
    @CfgName("max-cps")
    public int maxCps = 6;

    @CfgComment("")
    @CfgComment("╔════════════════════════════════════════════╗")
    @CfgComment("║              Other Settings                ║")
    @CfgComment("╚════════════════════════════════════════════╝")
    @CfgComment("")

    @CfgComment("If you want to use other proxy/vpn checker")
    @CfgComment("than default (proxycheck.io), you can set it here.")
    @CfgComment("Available placeholders: %ip%")
    @CfgStringStyle(StringStyle.ALWAYS_QUOTED)
    @CfgName("custom-proxy-check-url")
    public String customProxyCheck = "disabled";

    @CfgComment("Change when the console-filter should be active.")
    @CfgComment("ALWAYS (default) - always filter console messages")
    @CfgComment("ATTACK - only filter console messages when there is an active attack")
    @CfgComment("NEVER - completely disable the console-filter feature.")
    @CfgName("console-filter-mode")
    public String consoleFilterMode = "ALWAYS";

    @CfgComment("If log message contains one of these words, it will")
    @CfgComment("be hidden. This can save a lot of CPU on big attacks.")
    @CfgCollectionStyle(CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("console-filter")
    public List<String> consoleFilter = Arrays.asList(
            "GameProfile",
            "Disconnected",
            "UUID of player",
            "logged in",
            "lost connection",
            "InitialHandler");

    @CfgComment("Set to false to disable update checker.")
    @CfgName("update-checker")
    public boolean updateChecker = true;

    @CfgComment("Time in minutes before auto-saving data.")
    @CfgName("autosave-interval")
    public long autoSaveInterval = 10L;
}

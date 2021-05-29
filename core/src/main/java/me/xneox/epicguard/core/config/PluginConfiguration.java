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

import me.xneox.epicguard.core.proxy.ProxyService;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ConfigSerializable
public class PluginConfiguration {

    // Config sections
    private final Geographical geographical = new Geographical();
    private final ProxyCheck proxyCheck = new ProxyCheck();
    private final AccountLimitCheck accountLimitCheck = new AccountLimitCheck();
    private final SettingsCheck settingsCheck = new SettingsCheck();
    private final NicknameCheck nicknameCheck = new NicknameCheck();
    private final NameSimilarityCheck nameSimilarityCheck = new NameSimilarityCheck();

    private final ConsoleFilter consoleFilter = new ConsoleFilter();
    private final AutoWhitelist autoWhitelist = new AutoWhitelist();
    private final Misc misc = new Misc();

    @ConfigSerializable
    public static final class Geographical {
        @Comment("Country check will filter countries/cities your players can connect from.\n" +
                "NEVER - check is disabled.\n" +
                "ATTACK - check will be performed only during bot-attack.\n" +
                "ALWAYS - check will be always performed.")
        private final String checkMode = "NEVER";

        @Comment("This will define if the 'countries' list should be a blacklist or a whitelist.\n" +
                "BLACKLIST - countries below are blocked\n" +
                "WHITELIST - only countries below are allowed")
        private final String checkType = "BLACKLIST";

        @Comment("List of country codes: https://dev.maxmind.com/geoip/legacy/codes/iso3166/")
        private final List<String> countries = Arrays.asList("US", "DE");

        @Comment("If a player tries to connect from city listed here, he will be blocked.")
        private final List<String> cityBlacklist = Arrays.asList("ExampleCity", "AnotherCity");

        public String checkMode() {
            return this.checkMode;
        }

        public String checkType() {
            return this.checkType;
        }

        public List<String> countries() {
            return this.countries;
        }

        public List<String> cityBlacklist() {
            return this.cityBlacklist;
        }
    }

    @ConfigSerializable
    public static final class ProxyCheck {
        @Comment("Detect users who are connecting using proxies or VPNs.\n" +
                "NEVER - check is disabled.\n" +
                "ATTACK - check will be performed only during bot-attack.\n" +
                "ALWAYS - check will be always performed.")
        private final String checkMode = "ALWAYS";

        @Comment("You can set as many proxy checking services as you want here.\n" +
                "If you're not familiar with regex, see https://regexr.com/\n" +
                "For example, (yes|proxy) will check if the response contains either 'yes' or 'proxy'")
        private final List<ProxyService> services = Collections.singletonList(
                new ProxyService("https://proxycheck.io/v2/{IP}?key=YOUR_KEY&risk=1&vpn=1", "(yes|proxy)"));

        @Comment("How long in SECONDS responses from proxy check should be cached?\n" +
                "Higher value increases performance, but keep in mind that if user\n" +
                "disables their VPN but the cache hasn't expired yet, he will still be detected.")
        private final int cacheDuration = 120;

        public String checkMode() {
            return this.checkMode;
        }

        public List<ProxyService> services() {
            return this.services;
        }

        public int cacheDuration() {
            return this.cacheDuration;
        }
    }

    @ConfigSerializable
    public static final class AccountLimitCheck {
        @Comment("This check will limit how many accounts can be registered from single IP address\n" +
                "NEVER - check is disabled.\n" +
                "ATTACK - check will be performed only during bot-attack.\n" +
                "ALWAYS - check will be always performed.")
        private final String checkMode = "ALWAYS";

        @Comment("Limit of accounts per one IP address.")
        private final int limit = 3;

        public String checkMode() {
            return this.checkMode;
        }

        public int accountLimit() {
            return this.limit;
        }
    }

    @ConfigSerializable
    public static final class SettingsCheck {
        @Comment("Every vanilla client sends the Settings packet shortly after joining.\n" +
                "Some bots doesn't do this, and this check will try to detect that.")
        private final boolean enabled = true;

        @Comment("Delay in seconds after which we check if the player has already sent this packet.\n" +
                "Increase for faster detection, decrease if detecting players with bad internet connection")
        private final int delay = 5;

        public boolean enabled() {
            return this.enabled;
        }

        public int delay() {
            return this.delay;
        }
    }

    @ConfigSerializable
    public static final class NicknameCheck {
        @Comment("Nickname-check will block players if their nickname matches\n"
                + "the regex expression set below.\n" +
                "NEVER - check is disabled.\n" +
                "ATTACK - check will be performed only during bot-attack.\n" +
                "ALWAYS - check will be always performed.")
        private final String checkMode = "ALWAYS";

        @Comment("Default value will check if the nickname contains 'bot' or 'mcspam'.\n" +
                "You can use https://regex101.com/ for making and testing your own expression.")
        private final String expression = "(?i).*(bot|mcspam).*";

        public String checkMode() {
            return this.checkMode;
        }

        public String expression() {
            return this.expression;
        }
    }

    @ConfigSerializable
    public static final class NameSimilarityCheck {
        @Comment("NameSimilarityCheck will detect similar nicknames\n"
                + "of the connecting users.\n" +
                "NEVER - check is disabled.\n" +
                "ATTACK - check will be performed only during bot-attack.\n" +
                "ALWAYS - check will be always performed.")
        private final String checkMode = "ATTACK";

        @Comment("How many nicknames should be keep in the history?\n" +
                "When an user is connecting to the server, his nickname will be added to the history.\n" +
                "Then the nickname will be compared with other nicknames stored in the history.\n" +
                "(!) Requires restart to apply.")
        private final int historySize = 10;

        @Comment("The lower the distance, the similar the names.\n" +
                "If the distance detected is lower or the same as\n" +
                "configured below, it will be considered as a positive detection.")
        private final int distance = 1;

        public String checkMode() {
            return this.checkMode;
        }

        public int historySize() {
            return this.historySize;
        }

        public int distance() {
            return this.distance;
        }
    }

    @ConfigSerializable
    public static final class ConsoleFilter {
        @Comment("Change when the console-filter should be active.\n" +
                "NEVER - feature is disabled.\n" +
                "ATTACK - feature will work only during bot-attack.\n" +
                "ALWAYS - feature will always work.")
        private final String filterMode = "ATTACK";

        @Comment("If log message contains one of these words, it will\n" +
                "be hidden. This can save a lot of CPU on big attacks.")
        private final List<String> filterMessages = Arrays.asList(
                "GameProfile",
                "Disconnected",
                "UUID of player",
                "logged in",
                "lost connection",
                "InitialHandler");

        public String filterMode() {
            return this.filterMode;
        }

        public List<String> filterMessages() {
            return this.filterMessages;
        }
    }

    @ConfigSerializable
    public static final class AutoWhitelist {
        @Comment("If a player is online for long enough (see option below)\n" +
                "He will be added to the whitelist, and be exempt from every future detections" +
                "ADDRESS - Only whitelist the user's IP address.\n" +
                "NICKNAME - Only whitelist the user's nickname.\n" +
                "MIXED - Whitelist the user's IP address AND nickname.\n" +
                "DISABLED - Disable this feature")
        private final String mode = "MIXED";

        @Comment("Time in seconds the player must be online\n" +
                "to be added to the EpicGuard's whitelist.")
        private final int timeOnline = 240;

        public String mode() {
            return this.mode;
        }

        public int timeOnline() {
            return this.timeOnline;
        }
    }

    @ConfigSerializable
    public static final class Misc {
        @Comment("ReconnectCheck will force new users to join the server again.\n" +
                "NEVER - check is disabled.\n" +
                "ATTACK - check will be performed only during bot-attack.\n" +
                "ALWAYS - check will be always performed.")
        private final String reconnectCheckMode = "ATTACK";

        @Comment("Server-list check will force users to add your server\n" +
                "to their server list (send a ping) before joining\n" +
                "NEVER - check is disabled.\n" +
                "ATTACK - check will be performed only during bot-attack.\n" +
                "ALWAYS - check will be always performed.")
        private final String serverListCheckMode = "ATTACK";

        @Comment("Should every user (except if he is whitelisted)\n" + "" +
                "be disconnected when there is an bot attack?\n" +
                "true - Better protection and HUGE performance boost\n" +
                "false - Allow NEW players connecting during attack.")
        private final boolean lockdownOnAttack = true;

        @Comment("How many connections per second must be made,\n" +
                "to activate the attack mode temporally?")
        private final int attackConnectionThreshold = 6;

        @Comment("How often (in seconds) the plugin should check if amount of connections\n" +
                "is slower than 'attack-connection-treshold' and disable attack mode?\n" +
                "(!) Requires restart to apply.")
        private final long attackResetInterval = 80L;

        @Comment("Set to false to disable update checker.")
        private final boolean updateChecker = true;

        @Comment("Time in minutes before auto-saving data.\n" +
                "(!) Requires restart to apply.")
        private final long autoSaveInterval = 10L;

        @Comment("Enabling this will log positive bot detections in the console.")
        private boolean debug;

        public String reconnectCheckMode() {
            return this.reconnectCheckMode;
        }

        public String serverListCheckMode() {
            return this.serverListCheckMode;
        }

        public boolean lockdownOnAttack() {
            return this.lockdownOnAttack;
        }

        public int attackConnectionThreshold() {
            return this.attackConnectionThreshold;
        }

        public long attackResetInterval() {
            return this.attackResetInterval;
        }

        public boolean updateChecker() {
            return this.updateChecker;
        }

        public long autoSaveInterval() {
            return this.autoSaveInterval;
        }

        public boolean debug() {
            return this.debug;
        }
    }

    public Geographical geographical() {
        return this.geographical;
    }

    public ProxyCheck proxyCheck() {
        return this.proxyCheck;
    }

    public AccountLimitCheck accountLimitCheck() {
        return this.accountLimitCheck;
    }

    public SettingsCheck settingsCheck() {
        return this.settingsCheck;
    }

    public NicknameCheck nicknameCheck() {
        return this.nicknameCheck;
    }

    public NameSimilarityCheck nameSimilarityCheck() {
        return this.nameSimilarityCheck;
    }

    public ConsoleFilter consoleFilter() {
        return this.consoleFilter;
    }

    public AutoWhitelist autoWhitelist() {
        return this.autoWhitelist;
    }

    public Misc misc() {
        return this.misc;
    }
}

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
    private final ConsoleFilter consoleFilter = new ConsoleFilter();
    private final AutoWhitelist autoWhitelist = new AutoWhitelist();

    @ConfigSerializable
    public static final class Geographical {
        private final String checkMode = "NEVER";
        private final String checkType = "BLACKLIST";
        private final List<String> countries = Collections.singletonList("US");
        private final List<String> cityBlacklist = Collections.singletonList("ExampleCity");

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
        private final String checkMode = "ALWAYS";
        private final String proxyCheckKey = "put_your_key_here";
        private final String customProxyCheckUrl = "disabled";
        private final List<String> responseContains = Arrays.asList("yes", "proxy", "vpn", "1");

        @Comment("How long in SECONDS responses from proxy check should be cached?")
        private final int cacheDuration = 120;

        public String checkMode() {
            return this.checkMode;
        }

        public String proxyCheckKey() {
            return this.proxyCheckKey;
        }

        public String customProxyCheckUrl() {
            return this.customProxyCheckUrl;
        }

        public List<String> responseContains() {
            return this.responseContains;
        }

        public int cacheDuration() {
            return this.cacheDuration;
        }
    }

    @ConfigSerializable
    public static final class AccountLimitCheck {
        private final String checkMode = "ALWAYS";
        private final int accountLimit = 3;

        public String checkMode() {
            return this.checkMode;
        }

        public int accountLimit() {
            return this.accountLimit;
        }
    }

    @ConfigSerializable
    public static final class SettingsCheck {
        private final boolean enabled = true;
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
        private final String checkMode = "ALWAYS";
        private final String expression = "(?i).*(bot|mcspam).*";

        public String checkMode() {
            return this.checkMode;
        }

        public String expression() {
            return this.expression;
        }
    }

    @ConfigSerializable
    public static final class ConsoleFilter {
        private final String filterMode = "ALWAYS";

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
        private final String mode = "MIXED";
        private final int timeOnline = 240;

        public String mode() {
            return this.mode;
        }

        public int timeOnline() {
            return this.timeOnline;
        }
    }

    private final String reconnectCheckMode = "ATTACK";

    private final String serverListCheckMode = "ATTACK";

    private final boolean lockdownOnAttack = true;

    private final int attackConnectionThreshold = 6;

    private final long attackResetInterval = 80L;

    @Comment("Set to false to disable update checker.")
    private final boolean updateChecker = true;

    @Comment("Time in minutes before auto-saving data.\n" +
            "(!) Requires restart to apply.")
    private final long autoSaveInterval = 10L;

    @Comment("Enabling this will log positive bot detections in the console.")
    private boolean debug;

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

    public ConsoleFilter consoleFilter() {
        return this.consoleFilter;
    }

    public AutoWhitelist autoWhitelist() {
        return this.autoWhitelist;
    }

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

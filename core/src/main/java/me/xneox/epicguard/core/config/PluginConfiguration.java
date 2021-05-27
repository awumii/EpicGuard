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
    public Geographical geographical = new Geographical();
    public ProxyCheck proxyCheck = new ProxyCheck();
    public AccountLimitCheck accountLimitCheck = new AccountLimitCheck();
    public SettingsCheck settingsCheck = new SettingsCheck();
    public NicknameCheck nicknameCheck = new NicknameCheck();
    public ConsoleFilter consoleFilter = new ConsoleFilter();
    public AutoWhitelist autoWhitelist = new AutoWhitelist();

    @ConfigSerializable
    public static final class Geographical {
        public String checkMode = "NEVER";
        public String checkType = "BLACKLIST";
        public List<String> countries = Collections.singletonList("US");
        public List<String> cityBlacklist = Collections.singletonList("ExampleCity");
    }

    @ConfigSerializable
    public static final class ProxyCheck {
        public String checkMode = "ALWAYS";
        public String proxyCheckKey = "put_your_key_here";
        public String customProxyCheckUrl = "disabled";
        public List<String> responseContains = Arrays.asList("yes", "proxy", "vpn", "1");

        @Comment("How long in SECONDS responses from proxy check should be cached?")
        public int cacheDuration = 120;
    }

    @ConfigSerializable
    public static final class AccountLimitCheck {
        public String checkMode = "ALWAYS";
        public int accountLimit = 3;
    }

    @ConfigSerializable
    public static final class SettingsCheck {
        public boolean enabled = true;
        public int checkDelay = 5;
    }

    @ConfigSerializable
    public static final class NicknameCheck {
        public String checkMode = "ALWAYS";
        public String expression = "(?i).*(bot|mcspam).*";
    }

    @ConfigSerializable
    public static final class ConsoleFilter {
        public String filterMode = "ALWAYS";

        @Comment("If log message contains one of these words, it will\n" +
                "be hidden. This can save a lot of CPU on big attacks.")
        public List<String> filterMessages = Arrays.asList(
                "GameProfile",
                "Disconnected",
                "UUID of player",
                "logged in",
                "lost connection",
                "InitialHandler");
    }

    @ConfigSerializable
    public static final class AutoWhitelist {
        public String mode = "MIXED";
        public int timeOnline = 240;
    }

    public String reconnectCheckMode = "ATTACK";

    public String serverListCheckMode = "ATTACK";

    public boolean lockdownOnAttack = true;

    public int attackConnectionThreshold = 6;

    public long attackResetInterval = 80L;

    @Comment("Set to false to disable update checker.")
    public boolean updateChecker = true;

    @Comment("Time in minutes before auto-saving data.\n" +
            "(!) Requires restart to apply.")
    public long autoSaveInterval = 10L;

    @Comment("Enabling this will log positive bot detections in the console.")
    public boolean debug;

    @Comment("Currently only JSON.")
    public String storageMethod = "JSON";
}

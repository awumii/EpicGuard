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

package me.xneox.epicguard.core.handler;

import de.leonhard.storage.util.Valid;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.impl.*;
import me.xneox.epicguard.core.user.PendingUser;
import me.xneox.epicguard.core.util.StringUtils;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Handler for PreLogin listeners.
 * Most important handler, should be called as soon as possible, and asynchronously.
 *
 * It performs every antibot check (except SettingsCheck).
 */
public class DetectionHandler {
    private final Collection<Check> checks = new HashSet<>();
    private final EpicGuard epicGuard;

    public DetectionHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;

        checks.add(new LockdownCheck(epicGuard));
        checks.add(new BlacklistCheck(epicGuard));
        checks.add(new GeographicalCheck(epicGuard));
        checks.add(new ServerListCheck(epicGuard));
        checks.add(new ReconnectCheck(epicGuard));
        checks.add(new AccountLimitCheck(epicGuard));
        checks.add(new NicknameCheck(epicGuard));
        checks.add(new ProxyCheck(epicGuard));
    }

    /**
     * Handling the incoming connection, and returning an optional disconnect message.
     *
     * @param address Address of the connecting user.
     * @param nickname Nickname of the connecting user.
     * @return Disconnect message, or an empty Optional if undetected.
     */
    @Nonnull
    public Optional<String> handle(@Nonnull String address, @Nonnull String nickname) {
        Valid.notNull(address, "Address cannot be null!");
        Valid.notNull(nickname, "Nickname cannot be null!");

        // Increment the connections per second and check if it's bigger than max-cps in config.
        if (this.epicGuard.attackManager().incrementConnectionCounter() >= this.epicGuard.config().attackConnectionThreshold()) {
            this.epicGuard.attackManager().attack(true); // If yes, then activate the attack mode.
        }

        // Check if the user is whitelisted, if yes, return empty result (undetected).
        if (this.epicGuard.storageManager().isWhitelisted(address)
                || this.epicGuard.storageManager().isWhitelisted(nickname)) {
            return Optional.empty();
        }

        // Performing all checks, we are using PendingUser
        PendingUser user = new PendingUser(address, nickname);
        for (Check check : this.checks) {
            if (check.handle(user)) {
                if (this.epicGuard.config().debug()) {
                    this.epicGuard.logger().info("(Debug) " + nickname + "/" + address + " detected by " +
                            check.getClass().getSimpleName());
                }

                // Positive detection, kicking the player!
                return Optional.of(StringUtils.buildMultilineString(check.kickMessage()));
            }
        }

        // Checks finished with no detection, the player is considered legitimate and is allowed to join the server
        // Also we update his account nickname history.
        this.epicGuard.storageManager().updateAccounts(user);
        return Optional.empty();
    }
}

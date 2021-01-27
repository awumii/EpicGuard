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

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.impl.*;
import me.xneox.epicguard.core.user.BotUser;
import me.xneox.epicguard.core.util.ChatUtils;
import org.diorite.libs.org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handler for PreLogin listeners.
 * Most important handler, should be called as soon as possible, and asynchronously.
 */
public class DetectionHandler {
    private final List<Check> checks = new ArrayList<>();
    private final EpicGuard epicGuard;

    public DetectionHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;

        checks.add(new AttackCheck(epicGuard));
        checks.add(new BlacklistCheck(epicGuard));
        checks.add(new GeographicalCheck(epicGuard));
        checks.add(new ServerListCheck(epicGuard));
        checks.add(new ReconnectCheck(epicGuard));
        checks.add(new AccountLimitCheck(epicGuard));
        checks.add(new RateLimitCheck(epicGuard));
        checks.add(new ProxyCheck(epicGuard));
    }

    @Nonnull
    public Optional<String> handle(@Nonnull String address, @Nonnull String nickname) {
        Validate.notNull(address, "Address cannot be null!");
        Validate.notNull(nickname, "Nickname cannot be null!");

        this.epicGuard.getAttackManager().incrementCPS();
        if (this.epicGuard.getAttackManager().getCPS() > this.epicGuard.getConfig().maxCps) {
            this.epicGuard.getAttackManager().setAttack(true);
        }

        if (this.epicGuard.getStorageManager().isWhitelisted(address)) {
            return Optional.empty();
        }

        BotUser user = new BotUser(address, nickname);
        for (Check check : this.checks) {
            if (check.handle(user)) {
                if (this.epicGuard.getConfig().debug) {
                    this.epicGuard.getLogger().log("(Debug) " + nickname + "/" + address + " detected by " +
                            check.getClass().getSimpleName());
                }
                return Optional.of(ChatUtils.buildString(check.getKickMessage()));
            }
        }

        this.epicGuard.getStorageManager().updateAccounts(user);
        return Optional.empty();
    }
}

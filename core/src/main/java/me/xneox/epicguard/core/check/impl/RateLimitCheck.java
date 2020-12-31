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

package me.xneox.epicguard.core.check.impl;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.CheckMode;
import me.xneox.epicguard.core.user.BotUser;
import me.xneox.epicguard.core.util.Cooldown;

import javax.annotation.Nonnull;
import java.util.List;

public class RateLimitCheck extends Check {
    public RateLimitCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@Nonnull BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().rateLimitCheck);
        return this.assertCheck(mode, this.hasCooldown(user));
    }

    private boolean hasCooldown(BotUser user) {
        Cooldown cooldown = new Cooldown(user.getAddress(), this.getConfig().rateLimit);
        if (this.getEpicGuard().getCooldownManager().hasCooldown(user.getAddress())) {
            return true;
        }

        this.getEpicGuard().getCooldownManager().add(cooldown);
        return false;
    }

    @Override
    public @Nonnull List<String> getKickMessage() {
        return this.getMessages().kickMessageRateLimit;
    }
}

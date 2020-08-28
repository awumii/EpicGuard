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

package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckMode;
import me.ishift.epicguard.core.user.BotUser;
import me.ishift.epicguard.core.util.Cooldown;

import java.util.List;

public class RateLimitCheck extends Check {
    public RateLimitCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().rateLimitCheck);
        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return this.rateLimitCheck(user);
            case ATTACK:
                if (this.isAttack()) {
                    return this.rateLimitCheck(user);
                }
        }
        return false;
    }

    private boolean rateLimitCheck(BotUser user) {
        Cooldown cooldown = new Cooldown(user.getAddress(), 10);
        if (this.getEpicGuard().getCooldownManager().hasCooldown(user.getAddress())) {
            return true;
        }

        this.getEpicGuard().getCooldownManager().add(cooldown);
        return false;
    }

    @Override
    public List<String> reason() {
        return this.getMessages().kickMessageRateLimit;
    }

    @Override
    public boolean blacklist() {
        return false;
    }
}

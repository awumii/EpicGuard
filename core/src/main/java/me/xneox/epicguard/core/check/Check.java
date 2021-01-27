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

package me.xneox.epicguard.core.check;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.config.PluginConfiguration;
import me.xneox.epicguard.core.manager.StorageManager;
import me.xneox.epicguard.core.user.BotUser;
import org.diorite.libs.org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class Check {
    protected final EpicGuard epicGuard;

    public Check(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public boolean assertCheck(CheckMode mode, boolean expression) {
        if (mode == CheckMode.ALWAYS || mode == CheckMode.ATTACK && this.epicGuard.getAttackManager().isAttack()) {
            return expression;
        }
        return false;
    }

    @Nonnull
    public abstract List<String> getKickMessage();

    /**
     * @return true if detection is positive (detected as bot).
     * @param user An {@link BotUser} object with the information about the user.
     */
    public abstract boolean handle(@Nonnull BotUser user);
}

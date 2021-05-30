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
import me.xneox.epicguard.core.user.PendingUser;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class Check {
    protected final EpicGuard epicGuard;

    public Check(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    /**
     * It will check the {@link CheckMode} configuration of the current check,
     * and assert the following behaviour:
     *
     * If the mode is ALWAYS, it will return the value of the specified expression.
     * If the mode is ATTACK, it will return the value of the expression ONLY if there's an attack.
     * If the mode is NEVER, it will return false.
     *
     * @param mode The configured mode of this check.
     * @param expression The expression
     * @return The return value is based on the check's behaviour. True means positive detection, false means negative.
     */
    public boolean evaluate(CheckMode mode, boolean expression) {
        if (mode == CheckMode.ALWAYS || mode == CheckMode.ATTACK && this.epicGuard.attackManager().isAttack()) {
            return expression;
        }
        return false;
    }

    @Nonnull
    public abstract List<String> kickMessage();

    /**
     * This method contains the entire behaviour on the check.
     * When returning a value, use the assertCheck() method found above.
     *
     * @return true if detection is positive (detected as bot).
     * @param user An {@link PendingUser} object with the information about the user.
     */
    public abstract boolean handle(@Nonnull PendingUser user);
}

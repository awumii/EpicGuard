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

import java.util.List;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.user.ConnectingUser;
import me.xneox.epicguard.core.util.TextUtils;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;

public abstract class Check implements Comparable<Check> {
  protected final EpicGuard epicGuard;

  private final int priority;
  private final TextComponent detectionMessage;

  public Check(@NotNull EpicGuard epicGuard, @NotNull List<String> detectionMessage, int priority) {
    this.epicGuard = epicGuard;
    this.detectionMessage = TextUtils.multilineComponent(detectionMessage);
    this.priority = priority;
  }

  /**
   * It will check the {@link CheckMode} configuration of the current check, and assert the
   * following behaviour:
   *
   * If the mode is ALWAYS, it will return the value of the specified expression. If the mode is
   * ATTACK, it will return the value of the expression ONLY if there's an attack. If the mode is
   * NEVER, it will return false.
   *
   * @param mode The configured mode of this check.
   * @param expression The expression
   * @return The return value is based on the check's behaviour. True means positive detection,
   *     false means negative.
   */
  public boolean evaluate(CheckMode mode, boolean expression) {
    if (mode == CheckMode.ALWAYS || mode == CheckMode.ATTACK && this.epicGuard.attackManager().isUnderAttack()) {
      return expression;
    }
    return false;
  }

  /**
   * This method contains the entire behaviour on the check. When returning a value, use the
   * assertCheck() method found above.
   *
   * @return true if detection is positive (detected as bot).
   * @param user An {@link ConnectingUser} object with the information about the user.
   */
  public abstract boolean isDetected(@NotNull ConnectingUser user);

  @NotNull
  public TextComponent detectionMessage() {
    return this.detectionMessage;
  }

  @Override
  public int compareTo(@NotNull Check other) {
    return Integer.compare(other.priority, this.priority);
  }
}

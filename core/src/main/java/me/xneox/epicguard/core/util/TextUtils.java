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

package me.xneox.epicguard.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import me.xneox.epicguard.core.EpicGuardAPI;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TextUtils {
  private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder()
      .character('&')
      .hexCharacter('#')
      .hexColors()
      .build();

  /**
   * Constructs a {@link TextComponent} from legacy string.
   * Supports legacy color-codes by "&" and hex colors by "&#"
   *
   * @param message the original string
   * @return a component created from the string
   */
  @NotNull
  public static TextComponent component(String message) {
    return SERIALIZER.deserialize(message);
  }

  /**
   * Builds a multiline {@link TextComponent} from list of strings.
   */
  @NotNull
  public static TextComponent multilineComponent(@NotNull List<String> list) {
    Validate.notNull(list, "Kick message cannot be null!");

    StringBuilder builder = new StringBuilder();
    for (String line : list) {
      builder.append(line).append("\n");
    }
    return component(builder.toString());
  }

  /**
   * Tries to parse an {@link InetAddress} from the provided string.
   * For more information, see {@link InetAddress#getByName(String)}
   *
   * @param address String representation of a hostname
   * @return the parsed InetAddress
   */
  @Nullable
  public static InetAddress parseAddress(@NotNull String address) {
    try {
      return InetAddress.getByName(address);
    } catch (UnknownHostException ex) {
      EpicGuardAPI.INSTANCE.instance().logger().warn("Couldn't resolve the InetAddress for the host " + address + ": " + ex.getMessage());
    }
    return null;
  }
}

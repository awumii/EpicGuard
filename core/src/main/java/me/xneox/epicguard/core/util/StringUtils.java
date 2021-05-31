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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public final class StringUtils {
    /**
     * Builds a multiline string for disconnect messages.
     */
    @Nonnull
    public static String buildMultilineString(@Nonnull List<String> list) {
        Validate.notNull(list, "Kick message cannot be null!");

        StringBuilder builder = new StringBuilder();
        list.forEach(line -> builder.append(line).append("\n"));
        return builder.toString();
    }

    private StringUtils() {}
}

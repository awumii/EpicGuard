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

package me.xneox.epicguard.core.util.logging.impl;

import java.util.logging.Logger;
import java.util.logging.Level;
import me.xneox.epicguard.core.util.logging.LogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaLoggerWrapper implements LogWrapper {
  private final Logger logger;

  public JavaLoggerWrapper(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void info(@NotNull String message) {
    this.logger.info(message);
  }

  @Override
  public void warn(@NotNull String message) {
    this.logger.warning(message);
  }

  @Override
  public void error(@NotNull String message, @Nullable Throwable throwable) {
    this.logger.log(Level.SEVERE, message, throwable);
  }
}

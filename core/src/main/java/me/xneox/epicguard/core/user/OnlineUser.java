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

package me.xneox.epicguard.core.user;

import com.google.common.base.Objects;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a player who is currently connected to the server.
 */
public class OnlineUser {
  private final UUID uuid;
  private boolean notifications;
  private boolean settingsChanged;

  public OnlineUser(@NotNull UUID uuid) {
    this.uuid = uuid;
  }

  /**
   * @return the user's UUID.
   */
  @NotNull
  public UUID uuid() {
    return this.uuid;
  }

  /**
   * @return whenever the user has enabled the status notifications.
   */
  public boolean notifications() {
    return this.notifications;
  }

  public void notifications(boolean notifications) {
    this.notifications = notifications;
  }

  /**
   * @return whenever the user has sent the Settings packet at least once.
   */
  public boolean settingsChanged() {
    return this.settingsChanged;
  }

  public void settingsChanged(boolean settingsChanged) {
    this.settingsChanged = settingsChanged;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OnlineUser that = (OnlineUser) o;
    return Objects.equal(uuid, that.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(uuid);
  }
}

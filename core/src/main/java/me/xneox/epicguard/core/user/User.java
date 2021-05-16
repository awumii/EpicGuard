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

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * Represents an player who is currently connected to the server.
 */
public class User {
    private final UUID uuid;
    private boolean notifications;
    private boolean settingsChanged;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * @return The user's UUID.
     */
    @Nonnull
    public UUID getUUID() {
        return uuid;
    }

    /**
     * @return Whenever the user has enabled the status notifications.
     */
    public boolean hasNotifications() {
        return notifications;
    }

    /**
     * @return Whenever the user has sent the Settings packet at least once.
     */
    public boolean hasChangedSettings() {
        return settingsChanged;
    }

    public void setSettingsChanged(boolean settingsChanged) {
        this.settingsChanged = settingsChanged;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return hasNotifications() == user.hasNotifications() &&
                Objects.equal(uuid, user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, hasNotifications());
    }
}

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

/**
 * Represents a user who is currently connecting to the server.
 * It is also cached by some checks.
 *
 * TODO: Convert to record when gradle shadow supports it.
 */
public class ConnectingUser {
  private final String address;
  private final String nickname;

  public ConnectingUser(String address, String nickname) {
    this.address = address;
    this.nickname = nickname;
  }

  public String address() {
    return this.address;
  }

  public String nickname() {
    return this.nickname;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectingUser that = (ConnectingUser) o;
    return Objects.equal(address, that.address)
        && Objects.equal(nickname, that.nickname);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(address, nickname);
  }
}

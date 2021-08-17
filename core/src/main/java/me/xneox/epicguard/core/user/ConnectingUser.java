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

/**
 * This class holds address and nickname of the user, who is currently being checked (or cached) by
 * the antibot checks.
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
}

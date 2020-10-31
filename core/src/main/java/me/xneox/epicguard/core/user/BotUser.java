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

public class BotUser {
    private final String address;
    private final String nickname;

    public BotUser(String address, String nickname) {
        this.address = address;
        this.nickname = nickname;
    }

    public String getAddress() {
        return this.address;
    }

    public String getNickname() {
        return this.nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotUser botUser = (BotUser) o;
        return Objects.equal(getAddress(), botUser.getAddress()) &&
                Objects.equal(getNickname(), botUser.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAddress(), getNickname());
    }
}

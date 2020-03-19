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

package me.ishift.epicguard.common.types;

import me.ishift.epicguard.common.Messages;

import java.util.List;
import java.util.stream.Collectors;

public enum Reason {
    GEO(Messages.messageKickCountry),
    PROXY(Messages.messageKickProxy),
    SERVER_LIST(Messages.messagesKickServerList),
    BLACKLIST(Messages.messageKickBlacklist),
    VERIFY(Messages.messageKickVerify),
    NAME_CONTAINS(Messages.messageKickNamecontains);

    private List<String> reason;

    Reason(List<String> reason) {
        this.reason = reason;
    }

    public String getReason() {
        return this.reason.stream().map(s -> s.replace("&", "§") + "\n").collect(Collectors.joining());
    }
}

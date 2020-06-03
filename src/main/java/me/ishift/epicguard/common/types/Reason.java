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

import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.util.MessageHelper;

import java.util.List;
import java.util.stream.Collectors;

public enum Reason {
    GEO(Messages.messageKickCountry, true),
    PROXY(Messages.messageKickProxy, true),
    SERVER_LIST(Messages.messagesKickServerList, false),
    BLACKLIST(Messages.messageKickBlacklist, false),
    REJOIN(Messages.messageKickVerify, false),
    NAME_CONTAINS(Messages.messageKickNamecontains, true),
    BOT_BEHAVIOUR(Messages.messageKickBotBehaviour, false),
    UNSAFE(Messages.messageKickUnsafe, true),
    ATTACK(Messages.messageKickBotBehaviour, false);

    private final List<String> message;
    private final boolean blacklist;

    Reason(List<String> message, boolean blacklist) {
        this.message = message;
        this.blacklist = blacklist;
    }

    public boolean isBlacklist() {
        return this.blacklist;
    }

    public String getMessage() {
        return this.message.stream().map(line -> MessageHelper.color(line) + "\n").collect(Collectors.joining());
    }
}

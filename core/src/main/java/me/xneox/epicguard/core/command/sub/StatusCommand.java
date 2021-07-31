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

package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.OnlineUser;
import me.xneox.epicguard.core.util.MessageUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class StatusCommand implements SubCommand {
    @Override
    public void execute(@NotNull Audience audience, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        MessagesConfiguration.Command config = epicGuard.messages().command();

        //TODO: Fix this because for some reason UUID is always null (on Paper)
        // not sure if it works on other platforms? even Player doesn't contain the UUID pointer here...
        Optional<UUID> optional = audience.get(Identity.UUID);
        optional.ifPresentOrElse(uuid -> {
            OnlineUser onlineUser = epicGuard.userManager().getOrCreate(uuid);
            onlineUser.notifications(!onlineUser.notifications());

            audience.sendMessage(MessageUtils.component(config.prefix() + config.toggleStatus()));
        }, () -> audience.sendMessage(Component.text("This command is unavailable in the current environment.")
                .color(TextColor.fromHexString("#ff6600"))));
    }
}

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.storage.AddressMeta;
import me.xneox.epicguard.core.util.TextUtils;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public class WhitelistCommand implements SubCommand {
  @Override
  public void execute(@NotNull Audience audience, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
    var config = epicGuard.messages().command();

    if (args.length != 3) {
      audience.sendMessage(TextUtils.component(config.prefix() + config.usage()
          .replace("{USAGE}", "/guard whitelist <add/remove> <nickname/address>")));
      return;
    }

    var meta = epicGuard.storageManager().resolveAddressMeta(args[2]);
    if (meta == null) {
      audience.sendMessage(TextUtils.component(config.prefix() + config.invalidArgument()));
      return;
    }

    if (args[1].equalsIgnoreCase("add")) {
      if (meta.whitelisted()) {
        audience.sendMessage(TextUtils.component(config.prefix() + config.alreadyWhitelisted().replace("{USER}", args[2])));
        return;
      }

      meta.whitelisted(true);
      audience.sendMessage(TextUtils.component(config.prefix() + config.whitelistAdd().replace("{USER}", args[2])));
    } else if (args[1].equalsIgnoreCase("remove")) {
      if (!meta.whitelisted()) {
        audience.sendMessage(TextUtils.component(config.prefix() + config.notWhitelisted().replace("{USER}", args[2])));
        return;
      }

      meta.whitelisted(false);
      audience.sendMessage(TextUtils.component(config.prefix() + config.whitelistRemove().replace("{USER}", args[2])));
    }
  }

  @Override
  public @NotNull Collection<String> suggest(@NotNull String[] args, @NotNull EpicGuard epicGuard) {
    if (args.length == 2) {
      return Arrays.asList("add", "remove");
    }

    if (args[1].equalsIgnoreCase("remove")) {
      return epicGuard.storageManager().viewAddresses(AddressMeta::whitelisted);
    }
    return new ArrayList<>();
  }
}

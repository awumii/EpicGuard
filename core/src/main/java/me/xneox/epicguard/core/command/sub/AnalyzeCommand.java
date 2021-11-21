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

import com.google.common.net.InetAddresses;
import java.util.Collection;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.storage.AddressMeta;
import me.xneox.epicguard.core.util.TextUtils;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public class AnalyzeCommand implements SubCommand {

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public void execute(@NotNull Audience audience, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
    var config = epicGuard.messages().command();

    if (args.length != 2) {
      audience.sendMessage(TextUtils.component(config.prefix() +
          config.usage().replace("{USAGE}", "/guard analyze <nickname/address>")));
      return;
    }

    var meta = epicGuard.storageManager().resolveAddressMeta(args[1]);
    if (meta == null) {
      audience.sendMessage(TextUtils.component(config.prefix() + config.invalidArgument()));
      return;
    }

    // Assume that executor provided an address as the argument.
    String address = args[1];

    // If executor provided nickname as the argument instead, we have to find their IP address.
    if (!InetAddresses.isInetAddress(args[1])) {
      for (var entry : epicGuard.storageManager().addresses().entrySet()) {
        if (entry.getValue().equals(meta)) {
          address = entry.getKey();
          break;
        }
      }
    }

    for (String line : config.analyzeCommand()) {
      audience.sendMessage(TextUtils.component(line
          .replace("{ADDRESS}", address)
          .replace("{COUNTRY}", epicGuard.geoManager().countryCode(address))
          .replace("{CITY}", epicGuard.geoManager().city(address))
          .replace("{WHITELISTED}", meta.whitelisted() ? "&a✔" : "&c✖")
          .replace("{BLACKLISTED}", meta.blacklisted() ? "&a✔" : "&c✖")
          .replace("{ACCOUNT-AMOUNT}", String.valueOf(meta.nicknames().size()))
          .replace("{NICKNAMES}", String.join(", ", meta.nicknames()))));
    }
  }

  @Override
  public @NotNull Collection<String> suggest(@NotNull String[] args, @NotNull EpicGuard epicGuard) {
    return epicGuard.storageManager().addresses().keySet();
  }
}

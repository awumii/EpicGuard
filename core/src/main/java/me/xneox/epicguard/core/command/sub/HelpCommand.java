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
import me.xneox.epicguard.core.storage.AddressMeta;
import me.xneox.epicguard.core.util.MessageUtils;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements SubCommand {
  @Override
  public void execute(@NotNull Audience audience, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
    for (String line : epicGuard.messages().command().mainCommand()) {
      audience.sendMessage(MessageUtils.component(line
          .replace("{VERSION}", epicGuard.platform().version())
          .replace("{BLACKLISTED-IPS}", String.valueOf(epicGuard.storageManager().viewAddresses(AddressMeta::blacklisted).size()))
          .replace("{WHITELISTED-IPS}", String.valueOf(epicGuard.storageManager().viewAddresses(AddressMeta::whitelisted).size()))
          .replace("{CPS}", String.valueOf(epicGuard.attackManager().connectionCounter()))
          .replace("{ATTACK}", epicGuard.attackManager().isAttack() ? "&a✔" : "&c✖")));
    }
  }
}

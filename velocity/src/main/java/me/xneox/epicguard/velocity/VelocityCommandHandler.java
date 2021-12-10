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

package me.xneox.epicguard.velocity;

import com.velocitypowered.api.command.SimpleCommand;
import java.util.ArrayList;
import java.util.List;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.CommandHandler;
import net.kyori.adventure.text.Component;

public class VelocityCommandHandler extends CommandHandler implements SimpleCommand {
  public VelocityCommandHandler(EpicGuard epicGuard) {
    super(epicGuard);
  }

  @Override
  public void execute(Invocation invocation) {
    if (!invocation.source().hasPermission("epicguard.admin"))
      invocation.source().sendMessage(Component.text("§cYou don't have permission! (epicguard.admin)"));
    handleCommand(invocation.arguments(), invocation.source());
  }

  @Override
  public List<String> suggest(Invocation invocation) {
    return new ArrayList<>(handleSuggestions(invocation.arguments()));
  }
}

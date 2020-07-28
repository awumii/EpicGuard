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

package me.ishift.epicguard.velocity;

import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.util.MessagePosition;
import me.ishift.epicguard.core.MethodInterface;
import me.ishift.epicguard.core.util.ChatUtils;
import net.kyori.text.TextComponent;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class VelocityMethods implements MethodInterface {
    private final EpicGuardVelocity plugin;

    public VelocityMethods(EpicGuardVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void sendActionBar(String message, UUID target) {
        Optional<Player> playerOptional = this.plugin.getServer().getPlayer(target);
        playerOptional.ifPresent(player -> player.sendMessage(TextComponent.of(ChatUtils.coloredLegacy(message)), MessagePosition.ACTION_BAR));
    }

    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    @Override
    public String getVersion() {
        Optional<PluginContainer> container = this.plugin.getServer().getPluginManager().getPlugin("epicguard");
        if (container.isPresent()) {
            Optional<String> version = container.get().getDescription().getVersion();
            if (version.isPresent()) {
                return version.get();
            }
        }
        return null;
    }

    @Override
    public void runTaskLater(Runnable task, long seconds) {
        this.plugin.getServer().getScheduler()
                .buildTask(this.plugin, task)
                .delay(seconds, TimeUnit.SECONDS)
                .schedule();
    }

    @Override
    public void scheduleTask(Runnable task, long seconds) {
        this.plugin.getServer().getScheduler()
                .buildTask(this.plugin, task)
                .repeat(seconds, TimeUnit.SECONDS)
                .schedule();
    }
}

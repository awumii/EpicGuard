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

package me.ishift.epicguard.bungee;

import me.ishift.epicguard.core.MethodInterface;
import me.ishift.epicguard.core.util.ChatUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BungeeMethods implements MethodInterface {
    private final Plugin plugin;

    public BungeeMethods(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void sendActionBar(String message, UUID target) {
        ProxyServer.getInstance().getPlayer(target).sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtils.colored(message)));
    }

    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public void runTaskLater(Runnable task, long seconds) {
        this.plugin.getProxy().getScheduler().schedule(this.plugin, task, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void scheduleTask(Runnable task, long seconds) {
        this.plugin.getProxy().getScheduler().schedule(this.plugin, task, seconds, seconds, TimeUnit.SECONDS);
    }
}

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

package me.xneox.epicguard.bukkit;

import me.xneox.epicguard.bukkit.command.BukkitGuardCommandExecutor;
import me.xneox.epicguard.bukkit.listener.*;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.logging.GuardLogger;
import me.xneox.epicguard.core.Platform;
import me.xneox.epicguard.core.logging.impl.JavaLogger;
import me.xneox.epicguard.core.user.User;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class EpicGuardBukkit extends JavaPlugin implements Platform {
    private EpicGuard epicGuard;
    private GuardLogger logger;

    @Override
    public void onEnable() {
        this.logger = new JavaLogger(this.getLogger());
        this.epicGuard = new EpicGuard(this);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(this.epicGuard), this);
        pm.registerEvents(new PlayerQuitListener(this.epicGuard), this);
        pm.registerEvents(new PlayerJoinListener(this.epicGuard), this);
        pm.registerEvents(new ServerPingListener(this.epicGuard), this);
        pm.registerEvents(new PlayerSettingsListener(this.epicGuard), this);

        PluginCommand command = this.getCommand("epicguard");
        if (command != null) {
            BukkitGuardCommandExecutor cmdExecutor = new BukkitGuardCommandExecutor(this.epicGuard);
            command.setExecutor(cmdExecutor);
            command.setTabCompleter(cmdExecutor);
        }

        new Metrics(this, 5845);
        ChatUtils.checkUnsupportedVersion(this.logger);
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }

    @Override
    public @NotNull GuardLogger getGuardLogger() {
        return this.logger;
    }

    @Override
    public void sendActionBar(@Nonnull String message, @Nonnull User user) {
        Player player = Bukkit.getPlayer(user.getUUID());
        if (player != null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatUtils.colored(message)));
        }
    }

    @Override
    public void disconnectUser(@Nonnull User user, @Nonnull String message) {
        Player player = Bukkit.getPlayer(user.getUUID());
        if (player != null) {
            player.kickPlayer(ChatUtils.colored(message));
        }
    }

    @Override
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public void runTaskLater(@Nonnull Runnable task, long seconds) {
        Bukkit.getScheduler().runTaskLater(this, task, seconds * 20L);
    }

    @Override
    public void scheduleTask(@Nonnull Runnable task, long seconds) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, task, 20L, seconds * 20L);
    }
}

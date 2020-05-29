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

import lombok.Getter;
import me.ishift.epicguard.bungee.command.GuardCommand;
import me.ishift.epicguard.bungee.listener.*;
import me.ishift.epicguard.bungee.util.BungeeMetrics;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.BungeeSettings;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.task.AttackToggleTask;
import me.ishift.epicguard.common.task.CounterTask;
import me.ishift.epicguard.common.task.MonitorTask;
import me.ishift.epicguard.common.types.Platform;
import me.ishift.epicguard.common.util.FileUtil;
import me.ishift.epicguard.common.util.LibraryLoader;
import me.ishift.epicguard.common.util.Log4jFilter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
public class EpicGuardBungee extends Plugin {
    private static EpicGuardBungee epicGuardBungee;

    private AttackManager manager;
    private Collection<UUID> statusPlayers;

    @Override
    public void onEnable() {
        epicGuardBungee = this;
        LibraryLoader.init();
        FileUtil.saveResource(this.getDataFolder(), "config.yml");
        BungeeSettings.load();

        this.manager = new AttackManager();
        this.statusPlayers = new HashSet<>();

        final PluginManager pm = this.getProxy().getPluginManager();
        pm.registerListener(this, new PreLoginListener(this.manager));
        pm.registerListener(this, new PostLoginListener());
        pm.registerListener(this, new PingListener());
        pm.registerListener(this, new PlayerChatListener());
        pm.registerListener(this, new PlayerTabListener());

        pm.registerCommand(this, new GuardCommand("guard"));

        this.getProxy().getScheduler().schedule(this, new AttackToggleTask(this.manager), 1L, Configuration.checkConditionsDelay, TimeUnit.SECONDS);
        this.getProxy().getScheduler().schedule(this, new CounterTask(this.manager), 1L, 1L, TimeUnit.SECONDS);
        this.getProxy().getScheduler().schedule(this, new MonitorTask(this.manager, Platform.BUNGEE), 1L, 200L, TimeUnit.MILLISECONDS);

        new BungeeMetrics(this, 5956);

        if (Configuration.filterEnabled) {
            try {
                Class.forName("org.apache.logging.log4j.core.filter.AbstractFilter");
            } catch (ClassNotFoundException e) {
                this.getLogger().warning("Looks like you are running regular BungeeCord! LogFilter has been disabled. If you want to enable LogFilter, you need to install Waterfall/Travertine.");
                return;
            }
            final Log4jFilter filter = new Log4jFilter();
            filter.setFilteredMessages(Configuration.filterValues);
            filter.registerFilter();
        }
    }

    @Override
    public void onDisable() {
        StorageManager.save();
    }

    public static EpicGuardBungee getInstance() {
        return epicGuardBungee;
    }
}

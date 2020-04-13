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

import me.ishift.epicguard.bungee.command.GuardCommand;
import me.ishift.epicguard.bungee.listener.PingListener;
import me.ishift.epicguard.bungee.listener.PostLoginListener;
import me.ishift.epicguard.bungee.listener.PreLoginListener;
import me.ishift.epicguard.bungee.util.BungeeMetrics;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.task.AttackToggleTask;
import me.ishift.epicguard.common.task.CounterResetTask;
import me.ishift.epicguard.common.util.FileUtil;
import me.ishift.epicguard.common.util.Log4jFilter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EpicGuardBungee extends Plugin {
    private static EpicGuardBungee epicGuardBungee;
    private List<UUID> statusPlayers;

    @Override
    public void onEnable() {
        epicGuardBungee = this;
        this.statusPlayers = new ArrayList<>();
        FileUtil.saveResource(this.getDataFolder(), "config.yml");
        AttackManager.init();

        final PluginManager pm = this.getProxy().getPluginManager();
        pm.registerListener(this, new PreLoginListener());
        pm.registerListener(this, new PostLoginListener());
        pm.registerListener(this, new PingListener());
        pm.registerCommand(this, new GuardCommand("guard"));

        this.getProxy().getScheduler().schedule(this, new AttackToggleTask(), 1L, Configuration.checkConditionsDelay, TimeUnit.SECONDS);
        this.getProxy().getScheduler().schedule(this, new CounterResetTask(), 1L, 1L, TimeUnit.SECONDS);

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

    public static EpicGuardBungee getInstance() {
        return epicGuardBungee;
    }

    public List<UUID> getStatusPlayers() {
        return statusPlayers;
    }
}

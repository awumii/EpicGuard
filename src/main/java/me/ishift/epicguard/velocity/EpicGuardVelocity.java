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

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.task.AttackToggleTask;
import me.ishift.epicguard.common.task.CounterTask;
import me.ishift.epicguard.common.util.FileUtil;
import me.ishift.epicguard.common.util.LibraryLoader;
import me.ishift.epicguard.velocity.command.GuardCommand;
import me.ishift.epicguard.velocity.listener.PreLoginListener;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Plugin(id = "epicguard", name = "EpicGuard", version = "4.1.0",
        description = "Advanced server protection.", authors = {"iShift", "ruzekh"})
public class EpicGuardVelocity {
    private AttackManager manager;
    private final ProxyServer server;

    @Inject
    public EpicGuardVelocity(ProxyServer server) {
        this.server = server;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        LibraryLoader.init();
        FileUtil.saveResource(new File("plugins/EpicGuard"), "config.yml");
        this.manager = new AttackManager();

        server.getScheduler().buildTask(this, new AttackToggleTask(this.manager)).repeat(Configuration.checkConditionsDelay, TimeUnit.SECONDS).schedule();
        server.getScheduler().buildTask(this, new CounterTask(this.manager)).repeat(1, TimeUnit.SECONDS).schedule();

        server.getEventManager().register(this, new PreLoginListener(this.manager));
        server.getCommandManager().register(new GuardCommand(this.manager), "guard", "epicguard", "ab", "antibot");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        this.manager.getStorageManager().save();
    }

    public AttackManager getAttackManager() {
        return this.manager;
    }
}

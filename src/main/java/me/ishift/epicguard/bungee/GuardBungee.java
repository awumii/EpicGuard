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

import io.sentry.Sentry;
import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.api.GeoAPI;
import me.ishift.epicguard.bungee.command.GuardCommand;
import me.ishift.epicguard.bungee.listener.PingListener;
import me.ishift.epicguard.bungee.listener.PreLoginListener;
import me.ishift.epicguard.bungee.util.BungeeMetrics;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.Messages;
import me.ishift.epicguard.common.task.CounterTask;
import me.ishift.epicguard.common.task.NotificationTask;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.api.GuardLogger;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class GuardBungee extends Plugin {
    public static boolean log = false;
    public static boolean status = false;
    private static GuardBungee instance;

    /**
     * @return Instance of GuardBungee (Plugin)
     */
    public static GuardBungee getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Sentry.init("https://e7ee770a0ec94517b498284594d37adf@sentry.io/1868578");

        final String path = "plugins/EpicGuard";
        instance = this;

        final File file = new File(path, "config_bungee.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config_bungee.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                Sentry.capture(e);
            }
        }

        Config.loadBungee();
        StorageManager.getStorage().load();
        Messages.load();

        this.getProxy().getPluginManager().registerListener(this, new PreLoginListener());
        this.getProxy().getPluginManager().registerListener(this, new PingListener());

        this.getProxy().getScheduler().schedule(this, new CounterTask(), 1L, 1L, TimeUnit.SECONDS);
        this.getProxy().getScheduler().schedule(this, new NotificationTask(NotificationTask.Server.BUNGEE), 1L, 100L, TimeUnit.MILLISECONDS);

        this.getProxy().getPluginManager().registerCommand(this, new GuardCommand("guard"));

        new BungeeMetrics(this, 5956);

        EpicGuardAPI.setLogger(new GuardLogger("EpicGuard", path));
        EpicGuardAPI.setGeoApi(new GeoAPI(path, Config.countryEnabled, false));
    }

    @Override
    public void onDisable() {
        StorageManager.getStorage().save();
    }
}

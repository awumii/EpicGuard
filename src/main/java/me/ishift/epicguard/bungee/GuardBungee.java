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
import me.ishift.epicguard.common.StorageManager;
import me.ishift.epicguard.api.GuardLogger;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
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
        final String path = "plugins/EpicGuard";
        EpicGuardAPI.setLogger(new GuardLogger("EpicGuard", path));
        instance = this;

        final File file = new File(path, "config_bungee.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config_bungee.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                EpicGuardAPI.getLogger().info("[ERROR] ExceptionStackTrace");
                EpicGuardAPI.getLogger().info(" Error: " + e.toString());
                Arrays.stream(e.getStackTrace())
                        .map(stackTraceElement -> stackTraceElement.toString().split("\\("))
                        .filter(errors -> errors[0].contains("me.ishift.epicguard"))
                        .map(errors -> errors[1])
                        .map(line -> line.replace(".java", ""))
                        .map(line -> line.replace(":", " Line: "))
                        .map(line -> line.replace("\\)", ""))
                        .forEach(line -> EpicGuardAPI.getLogger().info("Klasa: " + line));
            }
        }

        Config.loadBungee();
        StorageManager.load();
        Messages.load();

        this.getProxy().getPluginManager().registerListener(this, new PreLoginListener());
        this.getProxy().getPluginManager().registerListener(this, new PingListener());

        this.getProxy().getScheduler().schedule(this, new CounterTask(), 1L, 1L, TimeUnit.SECONDS);
        this.getProxy().getScheduler().schedule(this, new NotificationTask(NotificationTask.Server.BUNGEE), 1L, 100L, TimeUnit.MILLISECONDS);

        this.getProxy().getPluginManager().registerCommand(this, new GuardCommand("guard"));

        new BungeeMetrics(this, 5956);
        EpicGuardAPI.setGeoApi(new GeoAPI(this.getDataFolder() + "/data", Config.countryEnabled, false));
    }

    @Override
    public void onDisable() {
        StorageManager.save();
    }
}

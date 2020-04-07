package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.command.GuardCommand;
import me.ishift.epicguard.bungee.listener.PingListener;
import me.ishift.epicguard.bungee.listener.PostLoginListener;
import me.ishift.epicguard.bungee.listener.PreLoginListener;
import me.ishift.epicguard.bungee.util.BungeeMetrics;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.task.AttackToggleTask;
import me.ishift.epicguard.common.task.CounterResetTask;
import me.ishift.epicguard.common.util.FileUtil;
import me.ishift.epicguard.common.util.Log4jFilter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Time;
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

        final TaskScheduler scheduler = this.getProxy().getScheduler();
        scheduler.schedule(this, new AttackToggleTask(), Configuration.checkConditionsDelay, TimeUnit.SECONDS);
        scheduler.schedule(this, new CounterResetTask(), 1, TimeUnit.SECONDS);

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

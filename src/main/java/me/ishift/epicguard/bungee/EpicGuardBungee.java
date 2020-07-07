package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.listener.PreLoginListener;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.task.CounterTask;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.concurrent.TimeUnit;

public class EpicGuardBungee extends Plugin {
    private EpicGuard epicGuard;

    @Override
    public void onEnable() {
        this.epicGuard = new EpicGuard(this.getLogger(), new BungeeNotificator());

        TaskScheduler scheduler = this.getProxy().getScheduler();
        scheduler.schedule(this, new CounterTask(this.epicGuard), 1L, TimeUnit.SECONDS);
        scheduler.schedule(this, new CounterTask(this.epicGuard), 20L, TimeUnit.SECONDS);

        this.getProxy().getPluginManager().registerListener(this, new PreLoginListener(this.epicGuard));
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }
}

package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.command.EpicGuardCommand;
import me.ishift.epicguard.bungee.listener.DisconnectListener;
import me.ishift.epicguard.bungee.listener.PostLoginListener;
import me.ishift.epicguard.bungee.listener.PreLoginListener;
import me.ishift.epicguard.bungee.listener.ServerPingListener;
import me.ishift.epicguard.bungee.util.Metrics;
import me.ishift.epicguard.core.EpicGuard;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class EpicGuardBungee extends Plugin {
    private EpicGuard epicGuard;

    @Override
    public void onEnable() {
        this.epicGuard = new EpicGuard(new BungeeMethods(this));

        PluginManager pm = this.getProxy().getPluginManager();
        pm.registerListener(this, new PreLoginListener(this.epicGuard));
        pm.registerListener(this, new DisconnectListener(this.epicGuard));
        pm.registerListener(this, new PostLoginListener(this.epicGuard));
        pm.registerListener(this, new ServerPingListener(this.epicGuard));

        pm.registerCommand(this, new EpicGuardCommand(this.epicGuard));

        new Metrics(this, 5956);
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }
}

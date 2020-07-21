package me.ishift.epicguard.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.velocity.command.EpicGuardCommand;
import me.ishift.epicguard.velocity.listener.DisconnectListener;
import me.ishift.epicguard.velocity.listener.PostLoginListener;
import me.ishift.epicguard.velocity.listener.PreLoginListener;

import java.util.logging.Logger;

@Plugin(id = "epicguard", name = "EpicGuard", version = "5.1.0")
public class EpicGuardVelocity {
    private final ProxyServer server;
    private final Logger logger;

    private EpicGuard epicGuard;

    @Inject
    public EpicGuardVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onEnable(ProxyInitializeEvent e) {
        this.epicGuard = new EpicGuard(new VelocityMethods(this));
        this.server.getCommandManager().register(new EpicGuardCommand(this.epicGuard), "epicguard", "guard");

        EventManager manager = this.getServer().getEventManager();
        manager.register(this, new PostLoginListener(this, epicGuard));
        manager.register(this, new PreLoginListener(epicGuard));
        manager.register(this, new DisconnectListener(epicGuard));
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent e) {
        this.epicGuard.shutdown();
    }

    public Logger getLogger() {
        return this.logger;
    }

    public ProxyServer getServer() {
        return this.server;
    }
}

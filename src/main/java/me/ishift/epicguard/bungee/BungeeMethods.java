package me.ishift.epicguard.bungee;

import me.ishift.epicguard.core.util.ChatUtils;
import me.ishift.epicguard.core.util.MethodInterface;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BungeeMethods implements MethodInterface {
    private final Plugin plugin;

    public BungeeMethods(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void sendActionBar(String message, UUID target) {
        ProxyServer.getInstance().getPlayer(target).sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtils.colored(message)));
    }

    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public void scheduleSyncTask(Runnable task, long seconds) {
        this.plugin.getProxy().getScheduler().schedule(this.plugin, task, seconds, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void scheduleAsyncTask(Runnable task, long seconds) {
        // There are no async repeating tasks in BungeeCord
        this.scheduleSyncTask(task, seconds);
    }
}

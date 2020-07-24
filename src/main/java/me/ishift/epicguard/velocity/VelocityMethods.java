package me.ishift.epicguard.velocity;

import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.util.MessagePosition;
import me.ishift.epicguard.core.MethodInterface;
import me.ishift.epicguard.velocity.util.VelocityUtils;
import net.kyori.text.TextComponent;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class VelocityMethods implements MethodInterface {
    private final EpicGuardVelocity plugin;

    public VelocityMethods(EpicGuardVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void sendActionBar(String message, UUID target) {
        Optional<Player> playerOptional = this.plugin.getServer().getPlayer(target);
        playerOptional.ifPresent(player -> player.sendMessage(TextComponent.of(VelocityUtils.color(message)), MessagePosition.ACTION_BAR));
    }

    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    @Override
    public String getVersion() {
        Optional<PluginContainer> container = this.plugin.getServer().getPluginManager().getPlugin("epicguard");
        if (container.isPresent()) {
            Optional<String> version = container.get().getDescription().getVersion();
            if (version.isPresent()) {
                return version.get();
            }
        }
        return null;
    }

    @Override
    public void runTaskLater(Runnable task, long seconds) {
        this.plugin.getServer().getScheduler()
                .buildTask(this.plugin, task)
                .delay(seconds, TimeUnit.SECONDS)
                .schedule();
    }

    @Override
    public void scheduleTask(Runnable task, long seconds) {
        this.plugin.getServer().getScheduler()
                .buildTask(this.plugin, task)
                .repeat(seconds, TimeUnit.SECONDS)
                .schedule();
    }
}

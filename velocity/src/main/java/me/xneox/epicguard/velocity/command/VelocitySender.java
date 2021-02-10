package me.xneox.epicguard.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import me.xneox.epicguard.core.command.Sender;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public class VelocitySender extends Sender<CommandSource> {
    protected VelocitySender(CommandSource sender) {
        super(sender);
    }

    @Override
    public void sendMessage(String message) {
        this.sender.sendMessage(Component.text(message));
    }

    @Override
    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    @Override
    public UUID getUUID() {
        return this.isPlayer() ? ((Player) this.sender).getUniqueId() : null;
    }
}

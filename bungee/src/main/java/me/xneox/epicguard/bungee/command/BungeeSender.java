package me.xneox.epicguard.bungee.command;

import me.xneox.epicguard.bungee.BungeeUtils;
import me.xneox.epicguard.core.command.Sender;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BungeeSender extends Sender<CommandSender> {
    protected BungeeSender(CommandSender sender) {
        super(sender);
    }

    @Override
    public void sendMessage(@NotNull String message) {
        this.sender.sendMessage(BungeeUtils.createComponent(message));
    }

    @Override
    public boolean isPlayer() {
        return this.sender instanceof ProxiedPlayer;
    }

    @Override
    public UUID getUUID() {
        return this.isPlayer() ? ((ProxiedPlayer) this.sender).getUniqueId() : null;
    }
}

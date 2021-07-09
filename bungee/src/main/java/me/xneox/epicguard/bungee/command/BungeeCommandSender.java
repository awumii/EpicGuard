package me.xneox.epicguard.bungee.command;

import me.xneox.epicguard.bungee.BungeeUtils;
import me.xneox.epicguard.core.command.Sender;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BungeeCommandSender extends Sender<CommandSender> {
    protected BungeeCommandSender(CommandSender commandSender) {
        super(commandSender);
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
    public UUID uuid() {
        return this.isPlayer() ? ((ProxiedPlayer) this.sender).getUniqueId() : null;
    }
}

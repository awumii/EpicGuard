package me.xneox.epicguard.bukkit.command;

import me.xneox.epicguard.bukkit.ChatUtils;
import me.xneox.epicguard.core.command.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BukkitCommandSender extends Sender<CommandSender> {
    protected BukkitCommandSender(CommandSender commandSender) {
        super(commandSender);
    }

    @Override
    public void sendMessage(@NotNull String message) {
        this.sender.sendMessage(ChatUtils.colored(message));
    }

    @Override
    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    @Override
    public UUID uuid() {
        return this.isPlayer() ? ((Player) this.sender).getUniqueId() : null;
    }
}

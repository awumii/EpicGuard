package me.xneox.epicguard.bukkit.command;

import me.xneox.epicguard.bukkit.ChatUtils;
import me.xneox.epicguard.core.command.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitSender extends Sender<CommandSender> {
    protected BukkitSender(CommandSender sender) {
        super(sender);
    }

    @Override
    public void sendMessage(String message) {
        this.sender.sendMessage(ChatUtils.colored(message));
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

package me.ishift.epicguard.bukkit.module.modules;

import me.ishift.epicguard.bukkit.module.Module;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import me.ishift.epicguard.common.util.MessageHelper;
import org.bukkit.entity.Player;

public class AllowedCommands implements Module {
    @Override
    public boolean execute(Player player, String command, String[] args) {
        if (SpigotSettings.allowedCommandsEnable && !SpigotSettings.allowedCommands.contains(args[0])) {
            if (SpigotSettings.allowedCommandsBypass && player.hasPermission("epicguard.bypass.allowed-commands")) {
                return false;
            }
            player.sendMessage(MessageHelper.color(Messages.notAllowedCommand));
            return true;
        }
        return false;
    }
}

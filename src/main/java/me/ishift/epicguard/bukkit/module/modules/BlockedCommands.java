package me.ishift.epicguard.bukkit.module.modules;

import me.ishift.epicguard.bukkit.module.Module;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import me.ishift.epicguard.common.util.MessageHelper;
import org.bukkit.entity.Player;

public class BlockedCommands implements Module {
    @Override
    public boolean execute(Player player, String command, String[] args) {
        if (SpigotSettings.blockedCommandsEnable && SpigotSettings.blockedCommands.contains(args[0])) {
            player.sendMessage(MessageHelper.color(Messages.prefix + Messages.blockedCommand));
            return true;
        }
        return false;
    }
}

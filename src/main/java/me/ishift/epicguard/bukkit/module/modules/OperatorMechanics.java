package me.ishift.epicguard.bukkit.module.modules;

import me.ishift.epicguard.bukkit.module.Module;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import me.ishift.epicguard.common.util.MessageHelper;
import org.bukkit.entity.Player;

public class OperatorMechanics implements Module {
    @Override
    public boolean execute(Player player, String command, String[] args) {
        if (SpigotSettings.disableOperatorMechanics)
            if (command.startsWith("/op") || command.startsWith("/deop") || command.startsWith("/minecraft:op") || command.startsWith("/minecraft:deop")) {
                player.sendMessage(MessageHelper.color(Messages.operatorDisabled));
                return true;
            }
        return false;
    }
}

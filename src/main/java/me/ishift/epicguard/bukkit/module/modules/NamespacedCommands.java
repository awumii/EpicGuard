package me.ishift.epicguard.bukkit.module.modules;

import me.ishift.epicguard.bukkit.module.Module;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import me.ishift.epicguard.common.util.MessageHelper;
import org.bukkit.entity.Player;

public class NamespacedCommands implements Module {
    @Override
    public boolean execute(Player player, String command, String[] args) {
        if (SpigotSettings.blockNamespacedCommands && command.contains(":")) {
            player.sendMessage(MessageHelper.color(Messages.namespacedDisabled));
            return true;
        }
        return false;
    }
}

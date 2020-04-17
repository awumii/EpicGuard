package me.ishift.epicguard.bukkit.module.modules;

import me.ishift.epicguard.bukkit.module.Module;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OperatorProtection implements Module {
    @Override
    public boolean execute(Player player, String command, String[] args) {
        if (SpigotSettings.opProtectionEnable && !SpigotSettings.opProtectionList.contains(player.getName()) && player.isOp()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), SpigotSettings.opProtectionCommand.replace("{PLAYER}", player.getName()));
            return true;
        }
        return false;
    }
}

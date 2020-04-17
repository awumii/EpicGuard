package me.ishift.epicguard.bukkit.module;

import org.bukkit.entity.Player;

public interface Module {
    /**
     * @param player Player executing the command.
     * @param command Full command which is executed, as String.
     * @param args Full command which is executed, as Array.
     * @return if the event should be cancelled
     */
    boolean execute(Player player, String command, String[] args);
}

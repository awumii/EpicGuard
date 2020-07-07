package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {
    private final EpicGuardBukkit plugin;

    public CommandListener(EpicGuardBukkit plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        String command = event.getMessage();
        String[] args = command.split(" ");

        if (this.plugin.getModuleManager().check(player, command, args)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent event) {
        String cmd = event.getCommand();
        if (this.plugin.getModuleManager().disableOperatorMechanicsConsole) {
            if (cmd.startsWith("op") || cmd.startsWith("deop") || cmd.startsWith("minecraft:op") || cmd.startsWith("minecraft:deop")) {
                event.setCancelled(true);
                event.getSender().sendMessage("Operator mechanics has been disabled on this server.");
            }
        }
    }
}

package me.xneox.epicguard.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

// why bungee... why Command is an abstract class?
public class BungeeCommandExecutor extends Command implements TabExecutor {
    private final EpicGuardBungee epicGuardBungee;

    public BungeeCommandExecutor(EpicGuardBungee epicGuardBungee) {
        super("epicguard", "epicguard.admin", "guard", "eg", "ab", "antibot");
        this.epicGuardBungee = epicGuardBungee;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.epicGuardBungee.epicGuard().commandHandler().handleCommand(args, this.epicGuardBungee.adventure().sender(sender));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return this.epicGuardBungee.epicGuard().commandHandler().handleSuggestions(args);
    }
}

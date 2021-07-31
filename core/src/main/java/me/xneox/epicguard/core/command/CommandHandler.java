package me.xneox.epicguard.core.command;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.sub.*;
import me.xneox.epicguard.core.util.MessageUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class holds all registered subcommands, and handles the user command/tab suggestion input.
 */
public class CommandHandler {
    private final Map<String, SubCommand> commandMap = new HashMap<>();
    private final EpicGuard epicGuard;

    public CommandHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;

        this.commandMap.put("analyze", new AnalyzeCommand());
        this.commandMap.put("blacklist", new BlacklistCommand());
        this.commandMap.put("help", new HelpCommand());
        this.commandMap.put("reload", new ReloadCommand());
        this.commandMap.put("status", new StatusCommand());
        this.commandMap.put("whitelist", new WhitelistCommand());
        this.commandMap.put("save", new SaveCommand());
    }

    public void handleCommand(@NotNull String[] args, @NotNull Audience audience) {
        String prefix = this.epicGuard.messages().command().prefix();
        if (args.length < 1) {
            audience.sendMessage(Component.text("&7You are running &6EpicGuard v" + this.epicGuard.platform().version()
                    + "&7. Run &c/guard help &7to see available commands and statistics."));
            return;
        }

        SubCommand command = this.commandMap.get(args[0]);
        if (command == null) {
            audience.sendMessage(MessageUtils.component(prefix + this.epicGuard.messages().command().unknownCommand()));
            return;
        }

        command.execute(audience, args, this.epicGuard);
    }

    @NotNull
    public Collection<String> handleSuggestions(@NotNull String[] args) {
        // If no argument is specified, send all available subcommands.
        if (args.length == 1) {
            return this.commandMap.keySet();
        }

        SubCommand command = this.commandMap.get(args[0]);
        if (command != null) {
            return command.suggest(args, this.epicGuard);
        }
        return new ArrayList<>();
    }
}

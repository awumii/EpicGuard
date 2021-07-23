package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.Sender;
import me.xneox.epicguard.core.command.SubCommand;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class SaveCommand implements SubCommand {
    @Override
    public void execute(@NotNull Sender<?> sender, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        try {
            epicGuard.storageManager().database().saveData();
            sender.sendMessage(epicGuard.messages().command().prefix() + "&aData has been saved succesfully.");
        } catch (SQLException ex) {
            sender.sendMessage(epicGuard.messages().command().prefix() + "&cAn exception ocurred when saving data. See console for details.");
            ex.printStackTrace();
        }
    }
}

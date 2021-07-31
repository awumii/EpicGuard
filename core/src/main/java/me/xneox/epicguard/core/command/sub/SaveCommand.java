package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.util.MessageUtils;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class SaveCommand implements SubCommand {
    @Override
    public void execute(@NotNull Audience audience, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        try {
            epicGuard.storageManager().database().saveData();
            audience.sendMessage(MessageUtils.component(epicGuard.messages().command().prefix() +
                    "&aData has been saved succesfully."));
        } catch (SQLException ex) {
            audience.sendMessage(MessageUtils.component(epicGuard.messages().command().prefix() +
                    "&cAn exception ocurred when saving data. See console for details."));
            ex.printStackTrace();
        }
    }
}

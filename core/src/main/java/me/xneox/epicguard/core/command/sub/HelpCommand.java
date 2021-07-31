package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.storage.AddressMeta;
import me.xneox.epicguard.core.util.MessageUtils;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements SubCommand {
    @Override
    public void execute(@NotNull Audience audience, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        for (String line : epicGuard.messages().command().mainCommand()) {
            audience.sendMessage(MessageUtils.component(line
                    .replace("{VERSION}", epicGuard.platform().version())
                    .replace("{BLACKLISTED-IPS}", String.valueOf(epicGuard.storageManager().viewAddresses(AddressMeta::blacklisted).size()))
                    .replace("{WHITELISTED-IPS}", String.valueOf(epicGuard.storageManager().viewAddresses(AddressMeta::whitelisted).size()))
                    .replace("{CPS}", String.valueOf(epicGuard.attackManager().connectionCounter()))
                    .replace("{ATTACK}", epicGuard.attackManager().isAttack() ? "&a✔" : "&c✖")));
        }
    }
}

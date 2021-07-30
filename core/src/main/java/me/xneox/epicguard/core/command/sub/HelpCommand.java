package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.Sender;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.storage.AddressMeta;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements SubCommand {
    @Override
    public void execute(@NotNull Sender<?> sender, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        for (String line : epicGuard.messages().command().mainCommand()) {
            sender.sendMessage(line
                    .replace("{VERSION}", epicGuard.platform().version())
                    .replace("{BLACKLISTED-IPS}", String.valueOf(epicGuard.storageManager().viewAddresses(AddressMeta::blacklisted).size()))
                    .replace("{WHITELISTED-IPS}", String.valueOf(epicGuard.storageManager().viewAddresses(AddressMeta::whitelisted).size()))
                    .replace("{CPS}", String.valueOf(epicGuard.attackManager().connectionCounter()))
                    .replace("{ATTACK}", epicGuard.attackManager().isAttack() ? "&a✔" : "&c✖"));
        }
    }
}

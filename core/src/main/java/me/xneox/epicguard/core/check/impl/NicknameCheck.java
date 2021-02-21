package me.xneox.epicguard.core.check.impl;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.user.BotUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NicknameCheck extends Check {
    public NicknameCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@NotNull BotUser user) {
        return user.getNickname().matches(this.epicGuard.getConfig().nicknameCheck);
    }

    @NotNull
    @Override
    public List<String> getKickMessage() {
        return this.epicGuard.getMessages().kickMessageNickname;
    }
}

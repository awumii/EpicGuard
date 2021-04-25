package me.xneox.epicguard.core.check.impl;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.CheckMode;
import me.xneox.epicguard.core.user.BotUser;


import javax.annotation.Nonnull;
import java.util.List;

public class NicknameCheck extends Check {
    public NicknameCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@Nonnull BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.epicGuard.getConfig().nicknameCheck);
        return this.assertCheck(mode, user.getNickname().matches(this.epicGuard.getConfig().nicknameCheckExpression));
    }

    @Nonnull
    @Override
    public List<String> getKickMessage() {
        return this.epicGuard.getMessages().kickMessageNickname;
    }
}

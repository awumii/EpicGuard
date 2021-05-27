package me.xneox.epicguard.core.check.impl;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.CheckMode;
import me.xneox.epicguard.core.user.PendingUser;


import javax.annotation.Nonnull;
import java.util.List;

/**
 * This check will try to match the user's nickname with the configured expression.
 */
public class NicknameCheck extends Check {
    public NicknameCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@Nonnull PendingUser user) {
        CheckMode mode = CheckMode.valueOf(this.epicGuard.config().nicknameCheck().checkMode());
        return this.assertCheck(mode, user.nickname().matches(this.epicGuard.config().nicknameCheck().expression()));
    }

    @Nonnull
    @Override
    public List<String> kickMessage() {
        return this.epicGuard.messages().disconnect().nickname();
    }
}

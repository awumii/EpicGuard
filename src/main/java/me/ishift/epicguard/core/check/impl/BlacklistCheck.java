package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.user.BotUser;

import java.util.List;

public class BlacklistCheck extends Check {
    public BlacklistCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(BotUser user) {
        return this.getEpicGuard().getStorageManager().isBlacklisted(user.getAddress());
    }

    @Override
    public List<String> getKickMessage() {
        return this.getEpicGuard().getMessages().kickMessageBlacklist;
    }

    @Override
    public boolean blacklistUser() {
        return false;
    }
}

package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;

import java.util.List;

public class BlacklistCheck extends Check {
    public BlacklistCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(String address, String nickname) {
        return this.getEpicGuard().getStorageManager().isBlacklisted(address);
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

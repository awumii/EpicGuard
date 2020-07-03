package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;

public class BlacklistCheck extends Check {
    public BlacklistCheck(EpicGuard epicGuard) {
        super(epicGuard, false);
    }

    @Override
    public boolean check(String address, String nickname) {
        return this.getEpicGuard().getStorageManager().isBlacklisted(address);
    }
}

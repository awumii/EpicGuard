package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;

public class AttackCheck extends Check {
    public AttackCheck(EpicGuard epicGuard) {
        super(epicGuard, false, epicGuard.getMessages().kickMessageBlacklist);
    }

    @Override
    public boolean check(String address, String nickname) {
        return this.getEpicGuard().isAttack() && this.getConfig().denyJoin;
    }
}

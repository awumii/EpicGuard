package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.user.BotUser;

import java.util.List;

public class AttackCheck extends Check {
    public AttackCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(BotUser user) {
        return this.getEpicGuard().isAttack() && this.getConfig().denyJoin;
    }

    @Override
    public List<String> getKickMessage() {
        return this.getEpicGuard().getMessages().kickMessageAttack;
    }

    @Override
    public boolean blacklistUser() {
        return false;
    }
}

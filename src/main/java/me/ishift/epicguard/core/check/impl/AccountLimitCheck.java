package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.user.BotUser;

import java.util.List;

public class AccountLimitCheck extends Check {
    public AccountLimitCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(BotUser user) {
        return user.getNicknames().size() > this.getConfig().accountLimit;
    }

    @Override
    public List<String> reason() {
        return this.getEpicGuard().getMessages().kickMessageAccountLimit;
    }

    @Override
    public boolean blacklist() {
        return true;
    }
}

package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.user.Account;

public class AccountLimitCheck extends Check {
    public AccountLimitCheck(EpicGuard epicGuard) {
        super(epicGuard, true, epicGuard.getMessages().kickMessageAccountLimit);
    }

    @Override
    public boolean check(String address, String nickname) {
        Account account = new Account(this.getEpicGuard(), address, nickname);
        return account.getNicknames().size() > this.getConfig().accountLimit;
    }
}

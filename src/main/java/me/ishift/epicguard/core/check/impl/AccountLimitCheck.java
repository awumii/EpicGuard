package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.user.Account;

import java.util.List;

public class AccountLimitCheck extends Check {
    public AccountLimitCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(String address, String nickname) {
        Account account = new Account(this.getEpicGuard(), address, nickname);
        return account.getNicknames().size() > this.getConfig().accountLimit;
    }

    @Override
    public List<String> getKickMessage() {
        return this.getEpicGuard().getMessages().kickMessageAccountLimit;
    }

    @Override
    public boolean blacklistUser() {
        return true;
    }
}

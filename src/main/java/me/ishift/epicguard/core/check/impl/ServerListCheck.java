package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckMode;
import me.ishift.epicguard.core.user.BotUser;

import java.util.List;

public class ServerListCheck extends Check {
    public ServerListCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean check(BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().serverListCheck);

        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return !this.getStorage().getPingCache().contains(user.getAddress());
            case ATTACK:
                if (this.getEpicGuard().isAttack()) {
                    return !this.getStorage().getPingCache().contains(user.getAddress());
                }
        }
        return false;
    }

    @Override
    public List<String> reason() {
        return this.getMessages().kickMessageServerList;
    }

    @Override
    public boolean blacklist() {
        return false;
    }
}

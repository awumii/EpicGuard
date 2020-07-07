package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckMode;

import java.util.Collection;
import java.util.HashSet;

public class ReconnectCheck extends Check {
    private final Collection<String> addresses;

    public ReconnectCheck(EpicGuard epicGuard) {
        super(epicGuard, false, epicGuard.getMessages().kickMessageReconnect);
        this.addresses = new HashSet<>();
    }

    @Override
    public boolean check(String address, String nickname) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().reconnectCheck);

        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return this.reconnectCheck(address);
            case ATTACK:
                if (this.getEpicGuard().isAttack()) {
                    return this.reconnectCheck(address);
                }
        }
        return false;
    }

    private boolean reconnectCheck(String address) {
        if (!this.addresses.contains(address)) {
            this.addresses.add(address);
            return true;
        }
        return false;
    }
}

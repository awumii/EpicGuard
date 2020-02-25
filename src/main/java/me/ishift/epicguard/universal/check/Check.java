package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.types.Reason;

public abstract class Check {
    private Reason reason;
    private boolean blacklist;

    public Check(Reason reason, boolean blacklist) {
        this.reason = reason;
    }

    public Reason getReason() {
        return this.reason;
    }

    public String getName() {
        return this.reason.name();
    }

    public boolean shouldBlacklist() {
        return blacklist;
    }

    public abstract boolean perform(String address, String nickname);
}

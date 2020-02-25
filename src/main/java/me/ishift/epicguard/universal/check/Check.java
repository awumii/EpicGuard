package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.types.Reason;

public abstract class Check {
    private Reason reason;

    public Check(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }

    public abstract boolean perform(String address, String nickname);
}

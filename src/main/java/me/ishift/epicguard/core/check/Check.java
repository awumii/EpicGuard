package me.ishift.epicguard.core.check;

import me.ishift.epicguard.core.Configuration;
import me.ishift.epicguard.core.EpicGuard;

public abstract class Check {
    private final EpicGuard epicGuard;
    private final boolean blacklist;

    public Check(EpicGuard epicGuard, boolean blacklist) {
        this.epicGuard = epicGuard;
        this.blacklist = blacklist;
    }

    public EpicGuard getEpicGuard() {
        return this.epicGuard;
    }

    public Configuration getConfig() {
        return this.epicGuard.getConfig();
    }

    public boolean shouldBlacklist() {
        return this.blacklist;
    }

    /**
     * @return true if detection is positive (detected as bot).
     */
    public abstract boolean check(String address, String nickname);
}

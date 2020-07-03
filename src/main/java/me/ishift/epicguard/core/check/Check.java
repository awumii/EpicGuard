package me.ishift.epicguard.core.check;

import me.ishift.epicguard.core.Configuration;
import me.ishift.epicguard.core.EpicGuard;

public abstract class Check {
    private final EpicGuard epicGuard;

    public Check(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public EpicGuard getEpicGuard() {
        return this.epicGuard;
    }

    public Configuration getConfig() {
        return this.epicGuard.getConfig();
    }

    /**
     * @return true if detection is positive (detected as bot).
     */
    public abstract boolean check(String address, String nickname);
}
